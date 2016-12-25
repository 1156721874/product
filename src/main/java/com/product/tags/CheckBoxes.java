package com.product.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.product.bean.common.DictItem;
import com.product.util.Common;

public class CheckBoxes extends SimpleTagSupport {

	private String name = "";
	private String itemid = "";
	private Object collection = null;
	private Object paramCollection = null;
	private String paramString = "";
	private String disabled="false";

	public void doTag() throws JspException, IOException {
		StringBuffer sb = new StringBuffer("");
		if (null != collection && collection instanceof List  ) {
			List collectionList = (List) collection;
			//使用集合的情况
			if(null != paramCollection && paramCollection instanceof List){
				List paramCollectionlist = (List) paramCollection;
				for (int i = 0; i < collectionList.size(); i++) {
					DictItem item = (DictItem) collectionList.get(i);
					for (int j = 0; j < paramCollectionlist.size(); j++) {
						DictItem pitem = (DictItem) paramCollectionlist.get(j);
						if (item.getDictid().equals(pitem.getDictid())) {
							item.setStatus("1");
							break;
						}
					}
				}
				//使用字符串的情况
			}else if( Common.isNotEmpty(paramString)){
				String [] items = paramString.split(",");
				for (int i = 0; i < collectionList.size(); i++) {
					DictItem item = (DictItem) collectionList.get(i);
					for(int j=0;j<items.length;j++){
						if(item.getDictid().equals(items[j])){
							item.setStatus("1");
							break;
						}
					}
				}
			}

			for (int i = 0; i < collectionList.size(); i++) {
				if (i != 0 && i % 5 == 0) {
					sb.append("<br/>");
				}
				DictItem item = (DictItem) collectionList.get(i);
				sb.append("<input type='checkbox' name='"+name+"' id='" + itemid + i+ "' "+disabled+" value='"+item.getDictid()+"' "+(("1".equals(item.getStatus()))?"checked":"")+"/>");
				sb.append(item.getDictname());
			}
		}
		System.out.println(sb.toString());
		getJspContext().getOut().write(sb.toString()); 
	}


	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public Object getCollection() {
		return collection;
	}

	public void setCollection(Object collection) {
		this.collection = collection;
	}

	public Object getParamCollection() {
		return paramCollection;
	}

	public void setParamCollection(Object paramCollection) {
		this.paramCollection = paramCollection;
	}

	public String getParamString() {
		return paramString;
	}

	public void setParamString(String paramString) {
		this.paramString = paramString;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDisabled() {
		return disabled;
	}


	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
