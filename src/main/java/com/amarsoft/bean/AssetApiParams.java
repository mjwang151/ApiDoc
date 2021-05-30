package com.amarsoft.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetApiParams implements Comparable<AssetApiParams>{

  private String paramId;
  private String parentParamId;
  private String apiId;
  private String projectId;
  private String name;
  private String chName;
  private String paramType;
  private String requestType;
  private String contentType;
  private String dataType;
  private String isRequired;
  private int sortNo;
  private int level;
  private String descr;
  private String inputUser;
  private String inputTime;
  private String updateUser;
  private String updateTime;
  private String isDelete;

  @Override
  public int compareTo(AssetApiParams o) {
    return (this.getSortNo()-o.getSortNo());
  }
}
