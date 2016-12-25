package com.product.bean.common;

public class DictItem {

	private String dictid = "";
	private String dictname = "";
	private String status = "";
	private String descript = "";
	public DictItem() {
	}

	public DictItem(String dictid, String dictname) {
		super();
		this.dictid = dictid;
		this.dictname = dictname;
	}

	public DictItem(String dictid, String dictname, String status,
			String descript) {
		super();
		this.dictid = dictid;
		this.dictname = dictname;
		this.status = status;
		this.descript = descript;
	}

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
	}

	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
}
