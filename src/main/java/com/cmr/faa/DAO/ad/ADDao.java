package com.cmr.faa.DAO.ad;

import com.cmr.faa.pojo.AD;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.net.*;
import java.util.Date;


@Entity
@NoArgsConstructor
@Table(name = "ad")
public class ADDao {
  @Transient
  final static Logger log = LoggerFactory.getLogger(ADDao.class);


  @Transient
  private AD ad;
  @Transient
  boolean urlValid = true;
  @Transient
  boolean pdfAttachement = false;

  @Transient
  private String baseUrl = ADConstants.AD_PDF_BASE_URL;
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

  public ADDao(AD ad) {
    this.ad = ad;
    this.ad_id = this.ad.getAd_id();
    this.adNumber = this.ad.getAdNumber();
    this.docketNumber = this.ad.getDocketNumber();
    this.amendmentNumber = this.ad.getAmendmentNumber();
    this.publishDate = this.ad.getPublishDate();
    this.issueDate = this.ad.getIssueDate();
    this.effectiveDate = this.ad.getEffectiveDate();
    this.supersedes = this.ad.getSupersedes();
    this.supersededBy = this.ad.getSupersededBy();
    this.subject = this.ad.getSubject();
    this.productType = this.ad.getProductType();
    this.productSubtype = this.ad.getProductSubtype();
    this.UNID = this.ad.getUNID();
    this.attachements = this.ad.getAttachements();
    this.extendedAttachmentURL = constructExtendedUrl(this.attachements);
  }

  public boolean validateExtendedURL() {
    boolean returnValue = false;
    URL url;
    HttpURLConnection huc;
    int responsecode;
    if (isUrlUnknown(extendedAttachmentURL)) {
      log.error("\nBase Airworthiness URL is unknown");
    } else {
      try {
        url = new URL(extendedAttachmentURL);
      } catch (MalformedURLException e) {
        System.out.println();
        log.error("Extended Attachment URL is malformed! ");
        e.printStackTrace();
        return returnValue;
      }
      try {
        huc = (HttpURLConnection)
          url.openConnection();
      } catch (IOException e) {
        log.error("\nADDao.validateExtendedURL");
        e.printStackTrace();
        return returnValue;
      }
      try {
        huc.setRequestMethod("HEAD");
      } catch (ProtocolException e) {
        log.error("\n Protocol exception setting request method.");
        e.printStackTrace();
        return returnValue;
      }
      try {
        huc.connect();
      } catch (IOException e) {
        log.error("\n IO exception connectingn to URl.");
        e.printStackTrace();
        return returnValue;
      }
      try {
        responsecode = huc.getResponseCode();
      } catch (IOException e) {
        log.error("\n IO exception getting Response code.");
        e.printStackTrace();
        return returnValue;
      }
      //Not sure why but most if not all URLS at FAA site return 302. It could have something to do with SEO does not follow a 'temporary' redirect and
      // maybe the FAA does not want SEO increase?
      if ((responsecode != HttpURLConnection.HTTP_OK) && (responsecode != HttpURLConnection.HTTP_MOVED_TEMP)) {
        log.warn("URL (" + extendedAttachmentURL + ")\n Did not return 'OK'.  AD: " + this.adNumber + " returned " + responsecode);
        urlValid = false;
      } else {
        urlValid = true;
        returnValue = true;
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
        boolean opendocument = false;
        if ((null == this.attachements) || (this.attachements.equals(""))) {
            //There is no pdf attachment, so set the extendedURL to the base AD page
            sb.append("/")
                    .append(this.UNID);
            opendocument = true;
        } else {
            //There IS a pdf attachment
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
            URI uri;
            if (opendocument)
                uri = new URI(ADConstants.AD_BASE_SCHEME, ADConstants.AD_HOST, path, "OpenDocument", null);
//                uri = new URI(ADConstants.AD_BASE_SCHEME, null, ADConstants.AD_HOST, "?OpenDocument", path);
            else

                uri = new URI(ADConstants.AD_BASE_SCHEME, ADConstants.AD_HOST, path, null);
            URL url = uri.toURL();
            urlString = url.toString();
//            log.info(urlString);
        } catch (URISyntaxException | MalformedURLException e) {
            urlString = "";
            printErrorMessage();
            e.printStackTrace();
            urlValid = false;
        }
        return urlString;
    }

    private void printErrorMessage() {
        System.out.println();
        System.out.println("ADDao.constructExtendedUrl");
        log.error("Error in constructing Extended URL for AD " + this.adNumber);
    }

    public boolean isPdfAttachement() {
        return pdfAttachement;
    }

    public boolean isUrlValid() {
        return urlValid;
    }

}
