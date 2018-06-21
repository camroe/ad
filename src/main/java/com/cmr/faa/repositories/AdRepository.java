package com.cmr.faa.repositories;

import com.cmr.faa.DAO.ad.ADDao;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AdRepository extends Repository<ADDao, Integer> {

  List<ADDao> findAll();

  ADDao findByAdNumber(String adnumber);

  ADDao save(ADDao adToPersist);

}


