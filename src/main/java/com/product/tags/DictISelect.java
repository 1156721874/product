package com.product.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.product.bean.common.DictItem;
import com.product.util.Common;

public class DictISelect extends SimpleTagSupport {

	private String name = "";
	
	private String id = "";
	
	private String tagclass="";
	
	private Object collection;

	private String defaultKey;

	private String emptyKey;

	private String emptyValue;

	public void doTag() throws JspException {
		StringBuffer rtn = new StringBuffer("");
		int flag = 0;
		rtn.append("<select name='").append(name).append("'").append(" id='").append(id).append("'");
		if(Common.isNotEmpty(tagclass)){
			rtn.append(" class='").append(tagclass).append("'");
		}
		rtn.append(">");
		try {
			if (null != collection && collection instanceof List) {
				List collectionList = (List) collection;
				for (int i = 0; i < collectionList.size(); i++) {
					DictItem dict = (DictItem) collectionList.get(i);
					String value = dict.getDictid();
					String tagtext = dict.getDictname();
					if(Common.isNotEmpty(defaultKey)){
						if(defaultKey.equals(value)){
							rtn.append("<option value='").append(value).append("'").append(" selected ").append(">").append(tagtext).append("</option>");
							flag = 1;
						}else{
							rtn.append("<option value='").append(value).append("'").append(">").append(tagtext).append("</option>");
						}
					}
					else{
						rtn.append("<option value='").append(value).append("'").append(">").append(tagtext).append("</option>");
					}
				}
				if(flag == 0 && null !=emptyKey && null !=emptyValue){
					rtn.append("<option value='").append(emptyKey).append("'").append(" selected ").append(">").append(emptyValue).append("</option>");
				}
			} else {
				getJspContext().getOut().write("");
			}
			rtn.append("</select>");
			System.out.println(rtn.toString());
			getJspContext().getOut().write(rtn.toString()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getCollection() {
		return collection;
	}

	public void setCollection(Object collection) {
		this.collection = collection;
	}

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

	public String getEmptyKey() {
		return emptyKey;
	}

	public void setEmptyKey(String emptyKey) {
		this.emptyKey = emptyKey;
	}

	public String getEmptyValue() {
		return emptyValue;
	}

	public void setEmptyValue(String emptyValue) {
		this.emptyValue = emptyValue;
	}
	public String getTagclass() {
		return tagclass;
	}

	public void setTagclass(String tagclass) {
		this.tagclass = tagclass;
	}

}