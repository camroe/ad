package com.cmr.faa.repositories;

import com.cmr.faa.DAO.Mapping.ModelToADMappingDAO;
import org.springframework.data.repository.Repository;

public interface ModelADRepository extends Repository<ModelToADMappingDAO, Integer> {
    ModelToADMappingDAO save(ModelToADMappingDAO modelToADMappingDAO);
}
