package com.product.util;

import java.io.Serializable;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.math.BigDecimal;  
import java.math.BigInteger;  
import java.util.Collection;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
  
import ognl.Ognl;  
import ognl.OgnlException;  
  
import org.apache.commons.beanutils.BeanUtils;  
import org.apache.commons.lang.StringUtils;  
import org.apache.log4j.Logger;  
  
import com.alibaba.fastjson.JSONArray;  
import com.alibaba.fastjson.JSONObject;  
import com.alibaba.fastjson.serializer.SerializerFeature;  
 
  
  
/** 
 * JSONMap类，用于描述json的{}类型 
 *  
 * 1.对象序列化为json文本， 静态方法 toJSONString 对象方法 toJSONString toString 
 *  
 * 2.从json文本解析为对象 参见各种parse方法 
 *  
 * 3.访问 参见各种get方法 
 *  
 * 4.支持Map接口 
 *  
 * @author wKF11381 
 * @version [版本号, Jul 9, 2012] 
 * @see [相关类/方法] 
 * @since [产品/模块版本] 
 */  
public class JSONMap implements Map, Cloneable, Serializable  
{  
    private static final long serialVersionUID = 5671383795555292993L;  
      
    private static Logger log = LogUtil.getLog();  
      
    private JSONObject json;  
      
    public JSONMap()  
    {  
        json = new JSONObject(16, false);  
    }  
      
    public JSONMap(Map map)  
    {  
        json = new JSONObject(map);  
    }  
      
    public JSONMap(boolean ordered)  
    {  
        json = new JSONObject(16, ordered);  
    }  
      
    public JSONMap(int initialCapacity)  
    {  
        json = new JSONObject(initialCapacity, false);  
    }  
      
    public JSONMap(int initialCapacity, boolean ordered)  
    {  
        json = new JSONObject(initialCapacity, ordered);  
    }  
      
    /** 
     * 解析文本得到JSONMap 
     *  
     * @param text 
     * @return 
     */  
    public static final JSONMap parseJSONMap(String text)  
    {  
        return from(JSONObject.parseObject(text));  
    }  
      
    /** 
     * 解析文本 得到指定class的对象 
     *  
     *  
     * @param <T> 
     * @param text 
     * @param clazz 
     * @return 
     */  
    public static final <T> T parseObject(String text, Class<T> clazz)  
    {  
        return JSONObject.parseObject(text, clazz);  
    }  
    /**
     * 根据ognl表达式express解析text
     * 
     * @param text
     * @param express
     * @return
     */
    public static final <T> T parseExpress(String text, String express)
    {
        try
        {
            Object obj = Ognl.getValue(express, JSONMap.parseJSONMap(text));
            
            if (obj instanceof JSONObject)
            {
                return (T)JSONMap.from((JSONObject)obj);
            }
            
            if (obj instanceof JSONArray)
            {
                return (T)JSONList.from((JSONArray)obj);
            }
            
            return (T)obj;
        }
        catch (OgnlException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据ognl表达式express解析json对象
     * 
     * @param text
     * @param express
     * @return
     */
    public static final <T> T parseExpress(JSONMap json, String express)
    {
        try
        {
            Object obj = Ognl.getValue(express, json);
            
            if (obj instanceof JSONObject)
            {
                return (T)JSONMap.from((JSONObject)obj);
            }
            
            if (obj instanceof JSONArray)
            {
                return (T)JSONList.from((JSONArray)obj);
            }
            
            return (T)obj;
        }
        catch (OgnlException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * JSONMap 转为 纯java对象类型
     * 
     * 1.JSONMap 中必须存在 key为 className，value为要转换的javabean路径 如 {"className": "com.huawei.xx.xx"}
     * 如不存在className，则返回入参的JSONMap 如className对于的类不存在，返回入参的JSONMap
     * 
     * 2.本方法支持 JSONMap中基本包装类型、json对象类型、json数组类型三者的复合嵌套 如 {"className":"com.huawei.xx.xx", "i" : 1, "str" : "wang", "d" :
     * 101.10, "list":["1",{"aa":"bb"}] }
     * 
     * @param item
     * @author wKF11381
     * @return
     */
    public static <T> T parseComplexJSONMap(String text)
    {
        JSONMap jsonMap = parseJSONMap(text);
        return (T)parseComplexJSONMap(jsonMap);
    }
    
    private static final String CLASS_NAME = "className";
    
    /**
     * 解析复杂的JSONMap
     * 
     * 1.JSONMap 如果存在 key为 className，value为要转换的javabean路径 则将该JSONMap转为对应的bean， 如 {"className": "com.huawei.xx.xx"}
     * 如不存在className，则返回入参的JSONMap 如className对于的类不存在，返回入参的JSONMap
     * 
     * 2.本方法支持 JSONMap中包含基本包装类型、json对象类型、json数组类型三者的复合嵌套 如 {"className":"com.huawei.xx.xx", "i" : 1, "str" : "wang", "d"
     * : 101.10, "list":["1",{"aa":"bb"}] }
     * 
     * @param item
     * @author wKF11381
     * @return
     */
    public static <T> T parseComplexJSONMap(JSONMap jsonMap)
    {
        String className = (jsonMap).getString(CLASS_NAME);
        if (StringUtils.isEmpty(className))
        {
            return (T)jsonMap;
        }
        
        Class cls;
        Object obj;
        try
        {
            cls = Class.forName(className);
            
            Method[] ms = cls.getMethods();
            
            Map<String, Method> writeMethodMap = new HashMap<String, Method>();
            for (Method m : ms)
            {
                writeMethodMap.put(m.getName(), m);
            }
            
            obj = cls.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(className + ":class load error", e);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(className + ":class load error", e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(className + ":class load error", e);
        }
        
        Iterator<Map.Entry<String, Object>> itr = ((Map)jsonMap).entrySet().iterator();
        while (itr.hasNext())
        {
            Map.Entry<String, Object> entry = itr.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (CLASS_NAME.equalsIgnoreCase(key))
            {
                continue;
            }
            
            // 由于底层 使用fastjson解析，所以这里要判断fastjson的对象类型
            // 而不是 自定义的JSONMap
            if (value instanceof JSONObject)
            {
                try
                {
                    BeanUtils.setProperty(obj, key, parseComplexJSONMap(from((JSONObject)value)));
                }
                catch (IllegalAccessException e)
                {
                    log.error(e.getMessage(), e);
                }
                catch (InvocationTargetException e)
                {
                    log.error(e.getMessage(), e);
                }
                continue;
            }
            
            // 由于底层 使用fastjson解析，所以这里要判断fastjson的对象类型
            // 而不是 自定义的JSONList
            if (value instanceof JSONArray)
            {
                try
                {
                    BeanUtils.setProperty(obj, key, JSONList.parseComplexJSONList(JSONList.from((JSONArray)value)));
                }
                catch (IllegalAccessException e)
                {
                    log.error(e.getMessage(), e);
                }
                catch (InvocationTargetException e)
                {
                    log.error(e.getMessage(), e);
                }
                continue;
            }
            
            try
            {
                
                BeanUtils.setProperty(obj, key, value);
            }
            catch (IllegalAccessException e)
            {
                log.error(e.getMessage(), e);
            }
            catch (InvocationTargetException e)
            {
                log.error(e.getMessage(), e);
            }
            continue;
            
        }
        
        return (T)obj;
    }
    /**
     * 将指定的对象转为json字符串
     * 
     * 此方法 对于内部同一个对象不会转成$ref格式
     * 
     * 如果内部对象存在循环引用 请慎用此方法 否则会产生堆栈溢出
     * 
     * @param object
     * @return
     */
    public static final String toJSONString(Object object)
    {
        return JSONObject.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }
    
    /**
     * 将fastjson中的JSONObject对象转为JSONMap对象
     * 
     * 仅供JSONList和JSONMap内部使用
     * 
     * @param obj
     * @return
     */
    protected static JSONMap from(JSONObject obj)
    {
        if (null == obj)
        {
            return null;
        }
        
        JSONMap jobject = new JSONMap();
        jobject.json = obj;
        
        return jobject;
    }
    
    /**
     * 根据ognl表达式express获取对应的值
     * 
     * @param text
     * @return
     */
    public <T> T getValue(String express)
    {
        try
        {
            Object obj = Ognl.getValue(express, this);
            if (obj instanceof JSONObject)
            {
                return (T)JSONMap.from((JSONObject)obj);
            }
            
            if (obj instanceof JSONArray)
            {
                return (T)JSONList.from((JSONArray)obj);
            }
            return (T)obj;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * 将key对应的值转为JSONMap类型返回
     * 
     * @param key
     * @return JSONObject
     */
    public JSONMap getJSONMap(String key)
    {
        return from(json.getJSONObject(key));
    }
    
    /**
     * 将key对应的值转为JSONList类型返回
     * 
     * @param key
     * @return
     */
    public JSONList getJSONList(String key)
    {
        return JSONList.from(json.getJSONArray(key));
    }
    
    /**
     * 将key对应的值转为JSONMap类型返回
     * 
     * 如果对返回的对象进行操作，会同时修改老的
     * 
     * @param key
     * @return JSONObject
     */
    public JSONMap getCanModifyJSONMap(String key)
    { 
        Object obj = json.get(key);
        
        if (obj instanceof JSONObject)
        {
            return from(json.getJSONObject(key));
        }
        if(obj instanceof Map)
        {
            return JSONMap.from(new JSONObject((Map)obj));
        }
        
        throw new RuntimeException("getJSONMap获取失败，内部非JSONObject 非Map类型");
    }
    
    /**
     * 将key对应的值转为JSONList类型返回
     * 
     * 如果对返回的对象进行操作，会同时修改老的
     * 
     * @param key
     * @return
     */
    public JSONList getCanModifyJSONList(String key)
    {
        Object obj = json.get(key);
        
        if(obj instanceof JSONArray)
        {
            return JSONList.from(json.getJSONArray(key));    
        }
        
        if(obj instanceof List)
        {
            return JSONList.from(new JSONArray((List)obj));
        }
        
        throw new RuntimeException("getJSONList获取失败，内部非JSONArray 非List类型");
    }
    
    /**
     * 将key对应的值转为类型为指定class的对象返回
     * 
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz)
    {
        return json.getObject(key, clazz);
    }
    
    /**
     * 将key对应的值转为Boolean类型返回
     * 
     * @param key
     * @return
     */
    public Boolean getBoolean(String key)
    {
        return json.getBoolean(key);
    }
    
    /**
     * 将key对应的值转为byte[]类型返回
     * 
     * @param key
     * @return
     */
    public byte[] getBytes(String key)
    {
        return json.getBytes(key);
    }
    
    /**
     * 将key对应的值转为boolean类型返回
     * 
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key)
    {
        return json.getBooleanValue(key);
    }
    
    /**
     * 将key对应的值转为Byte类型返回
     * 
     * @param key
     * @return
     */
    public Byte getByte(String key)
    {
        return json.getByte(key);
    }
    
    /**
     * 将key对应的值转为byte类型返回
     * 
     * @param key
     * @return
     */
    public byte getByteValue(String key)
    {
        return json.getByteValue(key);
    }
    /**
     * 将key对应的值转为Short类型返回
     * 
     * @param key
     * @return
     */
    public Short getShort(String key)
    {
        return json.getShort(key);
    }
    
    /**
     * 将key对应的值转为short类型返回
     * 
     * @param key
     * @return
     */
    public short getShortValue(String key)
    {
        return json.getShortValue(key);
    }
    
    /**
     * 将key对应的值转为Integer类型返回
     * 
     * @param key
     * @return
     */
    public Integer getInteger(String key)
    {
        return json.getInteger(key);
    }
    
    /**
     * 将key对应的值转为int类型返回
     * 
     * @param key
     * @return
     */
    public int getIntValue(String key)
    {
        return json.getIntValue(key);
    }
    
    /**
     * 将key对应的值转为Long类型返回
     * 
     * @param key
     * @return
     */
    public Long getLong(String key)
    {
        return json.getLong(key);
    }
    
    /**
     * 将key对应的值转为long类型返回
     * 
     * @param key
     * @return
     */
    public long getLongValue(String key)
    {
        return json.getLongValue(key);
    }
    
    /**
     * 将key对应的值转为Float类型返回
     * 
     * @param key
     * @return
     */
    public Float getFloat(String key)
    {
        return json.getFloat(key);
    }
    
    /**
     * 将key对应的值转为float类型返回
     * 
     * @param key
     * @return
     */
    public float getFloatValue(String key)
    {
        return json.getFloatValue(key);
    }
    
    /**
     * 将key对应的值转为Double类型返回
     * 
     * @param key
     * @return
     */
    public Double getDouble(String key)
    {
        return json.getDouble(key);
    }
    /**
     * 将key对应的值转为double类型返回
     * 
     * @param key
     * @return
     */
    public double getDoubleValue(String key)
    {
        return json.getDoubleValue(key);
    }
    
    /**
     * 将key对应的值转为BigDecimal类型返回
     * 
     * @param key
     * @return
     */
    public BigDecimal getBigDecimal(String key)
    {
        return json.getBigDecimal(key);
    }
    
    /**
     * 将key对应的值转为BigInteger类型返回
     * 
     * @param key
     * @return
     */
    public BigInteger getBigInteger(String key)
    {
        return json.getBigInteger(key);
    }
    
    /**
     * 将key对应的值转为String类型返回
     * 
     * @param key
     * @return
     */
    public String getString(String key)
    {
        return json.getString(key);
    }
    
    /**
     * 将key对应的值转为Date类型返回
     * 
     * @param key
     * @return
     */
    public Date getDate(String key)
    {
        return json.getDate(key);
    }
    
    /**
     * 将key对应的值转为java.sql.Date类型返回
     * 
     * @param key
     * @return
     */
    public java.sql.Date getSqlDate(String key)
    {
        return json.getSqlDate(key);
    }
    
    /**
     * 将key对应的值转为java.sql.Timestamp类型返回
     * 
     * @param key
     * @return
     */
    public java.sql.Timestamp getTimestamp(String key)
    {
        return json.getTimestamp(key);
    }
    
    /**
     * 将自己转为json字符串
     * 
     * 此方法 对于内部同一个对象不会转成$ref格式
     * 
     * 如果内部对象存在循环引用 请慎用此方法 否则会产生堆栈溢出
     * 
     * @return
     */
    @Override
    public String toString()
    {
        return toJSONString(json);
    }
    
    /**
     * 
     * 将自己转为json字符串
     * 
     * 此方法 对于内部同一个对象不会转成$ref格式
     * 
     * 如果内部对象存在循环引用 请慎用此方法 否则会产生堆栈溢出
     * 
     * @return
     */
    public String toJSONString()
    {
        return toJSONString(json);
    }
    
    public void clear()
    {
        json.clear();
    }
    
    public boolean containsKey(Object key)
    {
        return json.containsKey(key);
    }
    
    public boolean containsValue(Object value)
    {
        return json.containsValue(value);
    }
    
    public Set entrySet()
    {
        return json.entrySet();
    }
    
    public Object get(Object key)
    {
        return json.get(key);
    }
    
    public boolean isEmpty()
    {
        return json.isEmpty();
    }
    
    public Set keySet()
    {
        return json.keySet();
    }
    
    public Object put(Object key, Object value)
    {
        return json.put((String)key, value);
    }
    
    public void putAll(Map m)
    {
        json.putAll(m);
    }
    
    public Object remove(Object key)
    {
        return json.remove(key);
    }
    
    public int size()
    {
        return json.size();
    }
    
    public Collection values()
    {
        return json.values();
    }
    
    public Object clone()
    {
        JSONMap jsonMap = null;
        try
        {
            jsonMap = (JSONMap)super.clone();
            jsonMap = (JSONMap)json.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        
        return jsonMap;
    }
    
    public boolean equals(Object obj)
    {
        return json.equals(obj);
    }
    
    public int hashCode()
    {
        return json.hashCode();
    }
    
    public static void main(String[] args)
    {
       Map param1 =new HashMap();
       param1.put("key1.1", "value1.1");
       param1.put("key1.2", "value1.2");
       param1.put("key1.3", "value1.3");
       Map param2 =new HashMap();
       param2.put("key2.1", "value2.1");
       param2.put("key2.2", "value2.2");
       param2.put("key2.3", "value2.3");
       param2.put("key2.4", "value2.4");
       param1.put("param2", param2);
       String s1 = JSONMap.toJSONString(param1);
       System.out.println(s1);
       System.out.println(JSONMap.parseJSONMap(s1));
    }
}

        