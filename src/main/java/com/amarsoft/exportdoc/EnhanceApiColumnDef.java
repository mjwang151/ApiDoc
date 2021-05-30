package com.amarsoft.exportdoc;

/**
 * 字段结构附表
 * @author kwqin
 * created on 2020年12月31日 上午9:13:59
 */
public class EnhanceApiColumnDef {
	private String name;//字段
	private String desc;//中文名称
	private String type;//类型
	private String need;//类型
	private String id;//类型
	private String comment;//类型

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
