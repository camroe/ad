package com.cmr.faa.DAO.ad;

import com.cmr.faa.model.excel.AD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.net.*;
import java.util.Date;

@Entity
@Table(name = "ad")
public class ADDao {
    @Transient
    final static Logger log = LoggerFactory.getLogger(ADDao.class);

    @Transient
    private final AD ad;
    @Transient
    boolean urlValid = true;
    @Transient
    boolean pdfAttachement = false;

    @Transient
    private String baseUrl = ADConstants.adPdfBaseUrl;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //From AD
    private Integer ad_id;

    @Column(name = "adnumber")
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
    private String extendedAttachmentURL;

    public ADDao(AD excelAd) {
        this.ad = excelAd;
        this.ad_id = ad.getAd_id();
        this.adNumber = ad.getAdNumber();
        this.docketNumber = ad.getDocketNumber();
        this.amendmentNumber = ad.getAmendmentNumber();
        this.publishDate = ad.getPublishDate();
        this.issueDate = ad.getIssueDate();
        this.effectiveDate = ad.getEffectiveDate();
        this.supersedes = ad.getSupersedes();
        this.supersededBy = ad.getSupersededBy();
        this.subject = ad.getSubject();
        this.productType = ad.getProductType();
        this.productSubtype = ad.getProductSubtype();
        this.UNID = ad.getUNID();
        this.attachements = ad.getAttachements();
        this.extendedAttachmentURL = constructExtendedUrl(this.attachements);
    }

    public boolean checkAndLogExistence() {
        boolean returnValue = false;
        if (isUrlUnknown(extendedAttachmentURL)) {
            log.error("Base Airworthiness URL is unknown");
        } else {
            try {
                URL url = new URL(extendedAttachmentURL);
                HttpURLConnection huc = (HttpURLConnection)
                        url.openConnection();
                huc.setRequestMethod("HEAD");
                huc.connect();
                int responsecode = huc.getResponseCode();
                if (responsecode != HttpURLConnection.HTTP_OK) {
                    System.out.println();
                    log.warn("URL " + extendedAttachmentURL + " Did not return 'OK'.  AD: " + this.adNumber + " returned " + responsecode);
                } else {
                    urlValid = true;
                    returnValue = true;
                }
            } catch (MalformedURLException e) {
                log.error("Extended Attachment URL is malformed! ");
                e.printStackTrace();
                return returnValue;
            } catch (IOException e) {
                e.printStackTrace();
                return returnValue;
            }
        }
        return returnValue;
    }

    private boolean isUrlUnknown(String extendedAttachmentURL) {
        return ((extendedAttachmentURL.contains("http://UnknownURL")) || (null == extendedAttachmentURL));
    }

    private String constructExtendedUrl(String attachements) {
        StringBuilder sb = new StringBuilder();
        String pathFragment, urlString;
        if ((null == this.attachements) || (this.attachements.equals(""))) {
            //There is no pdf attachment, so set the extendedURL to the base AD page
            sb.append("/")
                    .append(this.UNID)
                    .append("/?OpenDocument");
        } else {
            sb.append("/")
                    .append(this.UNID)
                    .append("/$FILE/")
                    .append(this.attachements);
            pdfAttachement = true;
        }
        sb.trimToSize();
        pathFragment = sb.toString();
        try {
            String path = ADConstants.AD_PATH + pathFragment;
            URI uri = new URI(ADConstants.AD_BASE_SCHEME, ADConstants.AD_HOST, path);
            URL url = uri.toURL();
            urlString = url.toString();
        } catch (URISyntaxException e) {
            urlString = "";
            e.printStackTrace();
            urlValid = false;
        } catch (MalformedURLException e) {
            urlString = "";
            e.printStackTrace();
            urlValid = false;
        }
        return urlString;
    }

    public boolean isPdfAttachement() {
        return pdfAttachement;
    }

    public boolean isUrlValid() {
        return urlValid;
    }


}
