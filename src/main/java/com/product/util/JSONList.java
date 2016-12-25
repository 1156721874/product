package com.product.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * JSONList类 用于表述json []类型
 * 
 * 1.对象序列化为json文本， 静态方法 toJSONString 对象方法 toJSONString toString
 * 
 * 2.从json文本解析为对象 参见各种parse方法
 * 
 * 3.访问 参见各种get方法
 * 
 * 4.支持List接口
 * 
 * 
 * @author wKF11381
 * @version [1.0, Jul 9, 2012]
 */
public class JSONList implements List, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 5671383795555292993L;
    
    private JSONArray json = new JSONArray();
    
    public JSONList()
    {
        json = new JSONArray(10);
    }
    
    public JSONList(List list)
    {
        json = new JSONArray(list);
    }
    
    public JSONList(int initialCapacity)
    {
        json = new JSONArray(initialCapacity);
    }
    
    /**
     * 将text解析为JSONList类型
     * 
     * @param text
     * @return
     */
    public static final JSONList parseJSONList(String text)
    {
        return from(JSONArray.parseArray(text));
    }
    
    /**
     * 将text解析为指定class的List集合
     * 
     * @param <T>
     * @param text
     * @param clazz
     * @return
     */
    public static final <T> List<T> parseList(String text, Class<T> clazz)
    {
        return JSONArray.parseArray(text, clazz);
    }
    
    /**
     * 根据ognl表达式express从 text表示的jsonlist中解析
     * 
     * @param text
     * @param express
     * @return
     */
    public static final <T> T parseExpress(String text, String express)
    {
        try
        {
            Object obj = Ognl.getValue(express, JSONList.parseJSONList(text));
            
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
     * 根据ognl表达式express从json集合对象解析
     * 
     * @param text
     * @param express
     * @return
     */
    public static final <T> T parseExpress(JSONList json, String express)
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
     * 复杂的数组字符串 转为List类型
     * 
     * 1. 支持数组字符串中 存放不同类型的数据
     * 
     * 2. 如果存在JSON对象类型 则必须存在 key为 className，value为要转换的javabean路径 如 {"className": "com.huawei.xx.xx"}
     * 如不存在className，则返回入参的JSONMap 如className对于的类不存在，返回入参的JSONMap
     * 
     * 3.本方法支持 数组中包含基本包装类型、JSON {}对象、JSON []对象三者的复合嵌套 如 [{"className":"com.huawei.xx.xx", "i" : 1, "str" : "wang", "d" :
     * 101.10, "list":["1",{"aa":"bb"}] }, 12, "ssss"]
     * 
     * @param jsonList
     * @author wKF11381
     * @return
     */
    public static List parseComplexJSONList(String text)
    {
        JSONList jsonList = JSONList.parseJSONList(text);
        return parseComplexJSONList(jsonList);
    }
    /**
     * 复杂的JSONList 转为List类型
     * 
     * 1. 支持数组中 存放不同类型的数据
     * 
     * 2. 如果存在JSON对象类型 则必须存在 key为 className，value为要转换的javabean路径 如 {"className": "com.huawei.xx.xx"}
     * 如不存在className，则返回入参的JSONMap 如className对于的类不存在，返回入参的JSONMap
     * 
     * 3.本方法支持 数组中包含基本包装类型、JSON {}对象、JSON []对象三者的复合嵌套 如 [{"className":"com.huawei.xx.xx", "i" : 1, "str" : "wang", "d" :
     * 101.10, "list":["1",{"aa":"bb"}] }, 12, "ssss"]
     * 
     * @param jsonList
     * @author wKF11381
     * @return
     */
    public static List parseComplexJSONList(JSONList jsonList)
    {
        List result = new ArrayList();
        for (int i = 0, size = jsonList.size(); i < size; i++)
        {
            Object item = jsonList.get(i);
            
            // 由于底层 使用fastjson解析，所以这里要判断fastjson的对象类型
            // 而不是 自定义的JSONList
            if (item instanceof JSONArray)
            {
                result.add(parseComplexJSONList(from((JSONArray)item)));
                
                continue;
            }
            
            // 由于底层 使用fastjson解析，所以这里要判断fastjson的对象类型
            // 而不是 自定义的JSONMap
            if (item instanceof JSONObject)
            {
                result.add(JSONMap.parseComplexJSONMap(JSONMap.from((JSONObject)item)));
                continue;
            }
            
            result.add(item);
        }
        
        return result;
    }
    
    /**
     * 将对象转为json字符串
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
        return JSONArray.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }
    
    /**
     * 将fastjson中的JSONArray对象转为JSONList对象
     * 
     * 仅供JSONList和JSONMap内部使用
     * 
     * @param array
     * @return
     */
    protected static JSONList from(JSONArray array)
    {
        if (null == array)
        {
            return null;
        }
        
        JSONList jsonList = new JSONList();
        jsonList.json = array;
        
        return jsonList;
    }
    
    /**
     * 根据ognl表达式express获取对应的值
     * 
     * @param express
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
        catch (OgnlException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * 将列表中指定位置的值转为JSONObject类型返回
     * 
     * @param index
     * @return
     */
    public JSONMap getJSONMap(int index)
    {
        return JSONMap.from(json.getJSONObject(index));
    }
    
    /**
     * 将列表中指定位置的值转为JSONList类型返回
     * 
     * @param index
     * @return
     */
    public JSONList getJSONList(int index)
    {
        return from(json.getJSONArray(index));
    }
    
    /**
     * 将列表中指定位置的值转为JSONObject类型返回
     * 
     * @param index
     * @return
     */
    public JSONMap getCanModifyJSONMap(int index)
    { 
        Object obj = json.get(index);
        
        if (obj instanceof JSONObject)
        {
            return JSONMap.from(json.getJSONObject(index));
        }
        
        if(obj instanceof Map) 
        {
            return JSONMap.from(new JSONObject((Map)obj));
        }
        
        throw new RuntimeException("getJSONMap获取失败，内部非JSONObject 非Map类型");
    }
    
    /**
     * 将列表中指定位置的值转为JSONList类型返回
     * 
     * @param index
     * @return
     */
    public JSONList getCanModifyJSONList(int index)
    {
        Object obj = json.get(index);
        
        if(obj instanceof JSONArray)
        {
            return JSONList.from(json.getJSONArray(index));    
        }
        
        if(obj instanceof List)
        {
            return JSONList.from(new JSONArray((List)obj));
        }
        
        throw new RuntimeException("getJSONList获取失败，内部非JSONArray 非List类型");
    }
    
    /**
     * 将列表中指定位置 指定class类型的元素
     * 
     * @param <T>
     * @param index
     * @param clazz
     * @return
     */
    public <T> T getObject(int index, Class<T> clazz)
    {
        return json.getObject(index, clazz);
    }
    
    /**
     * 将列表中指定位置的值转为Boolean类型返回
     * 
     * @param index
     * @return
     */
    public Boolean getBoolean(int index)
    {
        return json.getBoolean(index);
    }
    /**
     * 将列表中指定位置的值转为boolean类型返回
     * 
     * @param index
     * @return
     */
    public boolean getBooleanValue(int index)
    {
        return json.getBooleanValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为Byte类型返回
     * 
     * @param index
     * @return
     */
    public Byte getByte(int index)
    {
        return json.getByte(index);
    }
    
    /**
     * 将列表中指定位置的值转为byte类型返回
     * 
     * @param index
     * @return
     */
    public byte getByteValue(int index)
    {
        return json.getByteValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为Short类型返回
     * 
     * @param index
     * @return
     */
    public Short getShort(int index)
    {
        return json.getShort(index);
    }
    
    /**
     * 将列表中指定位置的值转为short类型返回
     * 
     * @param index
     * @return
     */
    public short getShortValue(int index)
    {
        return json.getShortValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为Integer类型返回
     * 
     * @param index
     * @return
     */
    public Integer getInteger(int index)
    {
        return json.getInteger(index);
    }
    
    /**
     * 将列表中指定位置的值转为int类型返回
     * 
     * @param index
     * @return
     */
    public int getIntValue(int index)
    {
        return json.getIntValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为Long类型返回
     * 
     * @param index
     * @return
     */
    public Long getLong(int index)
    {
        return json.getLong(index);
    }
    
    /**
     * 将列表中指定位置的值转为long类型返回
     * 
     * @param index
     * @return
     */
    public long getLongValue(int index)
    {
        return json.getLongValue(index);
    }
    /**
     * 将列表中指定位置的值转为Float类型返回
     * 
     * @param index
     * @return
     */
    public Float getFloat(int index)
    {
        return json.getFloat(index);
    }
    
    /**
     * 将列表中指定位置的值转为float类型返回
     * 
     * @param index
     * @return
     */
    public float getFloatValue(int index)
    {
        return json.getFloatValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为Double类型返回
     * 
     * @param index
     * @return
     */
    public Double getDouble(int index)
    {
        return json.getDouble(index);
    }
    
    /**
     * 将列表中指定位置的值转为double类型返回
     * 
     * @param index
     * @return
     */
    public double getDoubleValue(int index)
    {
        return json.getDoubleValue(index);
    }
    
    /**
     * 将列表中指定位置的值转为BigDecimal类型返回
     * 
     * @param index
     * @return
     */
    public BigDecimal getBigDecimal(int index)
    {
        return json.getBigDecimal(index);
    }
    
    /**
     * 将列表中指定位置的值转为BigInteger类型返回
     * 
     * @param index
     * @return
     */
    public BigInteger getBigInteger(int index)
    {
        return json.getBigInteger(index);
    }
    
    /**
     * 将列表中指定位置的值转为String类型返回
     * 
     * @param index
     * @return
     */
    public String getString(int index)
    {
        return json.getString(index);
    }
    
    /**
     * 将列表中指定位置的值转为java.util.Date类型返回
     * 
     * @param index
     * @return
     */
    public java.util.Date getDate(int index)
    {
        return json.getDate(index);
    }
    
    /**
     * 将列表中指定位置的值转为java.sql.Date类型返回
     * 
     * @param index
     * @return
     */
    public java.sql.Date getSqlDate(int index)
    {
        return json.getSqlDate(index);
    }
    /**
     * 将列表中指定位置的值转为java.sql.Timestamp类型返回
     * 
     * @param index
     * @return
     */
    public java.sql.Timestamp getTimestamp(int index)
    {
        return json.getTimestamp(index);
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
    
    public boolean add(Object e)
    {
        return json.add(e);
    }
    
    public void add(int index, Object element)
    {
        json.add(index, element);
    }
    
    public boolean addAll(Collection c)
    {
        return json.addAll(c);
    }
    
    public boolean addAll(int index, Collection c)
    {
        return json.addAll(index, c);
    }
    
    public void clear()
    {
        json.clear();
    }
    
    public boolean contains(Object o)
    {
        return json.contains(o);
    }
    
    public boolean containsAll(Collection c)
    {
        return json.containsAll(c);
    }
    
    public Object get(int index)
    {
        return json.get(index);
    }
    
    public int indexOf(Object o)
    {
        return json.indexOf(o);
    }
    
    public boolean isEmpty()
    {
        return json.isEmpty();
    }
    
    public Iterator iterator()
    {
        return json.iterator();
    }
    
    public int lastIndexOf(Object o)
    {
        return json.lastIndexOf(o);
    }
    public ListIterator listIterator()
    {
        return json.listIterator();
    }
    
    public ListIterator listIterator(int index)
    {
        return json.listIterator(index);
    }
    
    public boolean remove(Object o)
    {
        return json.remove(o);
    }
    
    public Object remove(int index)
    {
        return json.remove(index);
    }
    
    public boolean removeAll(Collection c)
    {
        return json.removeAll(c);
    }
    
    public boolean retainAll(Collection c)
    {
        return json.retainAll(c);
    }
    
    public Object set(int index, Object element)
    {
        return json.set(index, element);
    }
    
    public int size()
    {
        return json.size();
    }
    
    public List subList(int fromIndex, int toIndex)
    {
        return json.subList(fromIndex, toIndex);
    }
    
    public Object[] toArray()
    {
        return json.toArray();
    }
    
    public Object[] toArray(Object[] a)
    {
        return json.toArray(a);
    }
    
    public JSONList clone()
    {
        JSONList jsonList = null;
        try
        {
            jsonList = (JSONList)super.clone();
            jsonList.json = (JSONArray)json.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return jsonList;
    }
    
    public boolean equals(Object obj)
    {
        return json.equals(obj);
    }
    
    public int hashCode()
    {
        return json.hashCode();
    }
    
}
