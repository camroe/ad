package com.cmr.faa.pojo;

public class ModelToAD {
  private Integer model_id;
  private Integer ad_id;

  public ModelToAD(Integer model_id, Integer ad_id) {
    this.model_id = model_id;
    this.ad_id = ad_id;
  }

  public ModelToAD() {

  }

  public Integer getModel_id() {
    return model_id;
  }

  public void setModel_id(Integer model_id) {
    this.model_id = model_id;
  }

  public Integer getAd_id() {
    return ad_id;
  }

  public void setAd_id(Integer ad_id) {
    this.ad_id = ad_id;
  }
}
