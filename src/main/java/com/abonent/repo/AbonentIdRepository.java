package com.abonent.repo;

import com.abonent.model.AbonentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AbonentIdRepository extends CrudRepository<AbonentId, UUID> {
    List<AbonentId> findAbonentsByCtnIn(List<String> ctn);
}
