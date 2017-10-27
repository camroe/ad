package com.cmr.faa.repositories;

import com.cmr.faa.DAO.makes.MakeDao;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MakeRepository extends Repository<MakeDao, Integer> {
    List<MakeDao> findAll();

//    MakeDao findByMake_id(Integer make_id);

    MakeDao save(MakeDao adToPersist);
}
