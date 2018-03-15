package com.cmr.faa.pojo;

import java.util.Date;

/**
 * This is the pojo for the   ADs
 */
public class AD {

    private Integer ad_id;
    private String adNumber;
    private String docketNumber;
    private String amendmentNumber;
    private Date publishDate;
    private Date issueDate;
    private Date effectiveDate;
    private String supersedes;
    private String supersededBy;
    private String subject;
    private String productType;
    private String productSubtype;
    private String UNID;
    private String attachements;

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }

    public String getAmendmentNumber() {
        return amendmentNumber;
    }

    public void setAmendmentNumber(String amendmentNumber) {
        this.amendmentNumber = amendmentNumber;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getSupersedes() {
        return supersedes;
    }

    public void setSupersedes(String supersedes) {
        this.supersedes = supersedes;
    }

    public String getSupersededBy() {
        return supersededBy;
    }

    public void setSupersededBy(String supersededBy) {
        this.supersededBy = supersededBy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSubtype() {
        return productSubtype;
    }

    public void setProductSubtype(String productSubtype) {
        this.productSubtype = productSubtype;
    }

    public String getUNID() {
        return UNID;
    }

    public void setUNID(String UNID) {
        this.UNID = UNID;
    }

    public String getAttachements() {
        return attachements;
    }

    public void setAttachements(String attachements) {
        this.attachements = attachements;
    }
}
