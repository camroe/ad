package com.cmr.faa.DAO.makes;

import com.cmr.faa.pojo.Make;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "makes")
public class MakeDao {
  @Transient
  final static Logger log = LoggerFactory.getLogger(MakeDao.class);

  @Transient
  private final Make excelMake;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "make_id")
  private Integer make_id;
  @Column(name = "make")
  private String make;

  public MakeDao(Make excelMake) {
    this.excelMake = excelMake;
    this.make_id = excelMake.getMake_id();
    this.make = excelMake.getMake();
  }
}
