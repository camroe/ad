package com.cmr.faa.DAO.Mapping;

import com.cmr.faa.model.access.ModelToAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "model_ad")
public class ModelToADMappingDAO {

    @Transient
    final static Logger log = LoggerFactory.getLogger(ModelToADMappingDAO.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "model_id")
    private Integer model_id;
    @Column(name = "ad_id")
    private Integer ADID;

    public ModelToADMappingDAO(ModelToAD modelToAD) {
        this.ADID=modelToAD.getAd_id();
        this.model_id = modelToAD.getModel_id();
    }

    public Integer getModel_id() {
        return model_id;
    }

    public void setModel_id(Integer modelID) {
        this.model_id = modelID;
    }

    public Integer getADID() {
        return ADID;
    }

    public void setADID(Integer ADID) {
        this.ADID = ADID;
    }
}
