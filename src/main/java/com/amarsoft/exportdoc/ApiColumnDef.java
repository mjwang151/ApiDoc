package com.amarsoft.exportdoc;

/**
 * 字段结构表
 * @author kwqin
 * created on 2020年12月31日 上午9:13:59
 */
public class ApiColumnDef {
	private String id ;//序号
	private String name ;//字段名
	private String desc ;//注释
	private String type ;//类型及范围
	private String need ;//必选
	private String comment ;//说明
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
