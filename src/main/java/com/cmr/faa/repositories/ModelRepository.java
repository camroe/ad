package com.cmr.faa.repositories;

import com.cmr.faa.DAO.Models.ModelDao;
import org.springframework.data.repository.Repository;

public interface ModelRepository extends Repository<ModelDao, Integer> {

  ModelDao save(ModelDao adToPersist);

}
