package com.cmr.faa.DAO.Models;

import com.cmr.faa.pojo.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class ModelDao {
  @Transient
  final static Logger log = LoggerFactory.getLogger(ModelDao.class);

  @Transient
  private final Model excelModel;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "model_id")
  private Integer model_id;
  @Column(name = "make_id")
  private Integer make_id;
  @Column(name = "model")
  private String model;

  public ModelDao(Model excelModel) {
    this.excelModel = excelModel;
    this.model_id = excelModel.getModel_id();
    this.make_id = excelModel.getMake_id();
    this.model = excelModel.getModel();
  }

}
