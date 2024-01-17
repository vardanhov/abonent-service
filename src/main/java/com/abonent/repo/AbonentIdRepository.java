package com.abonent.repo;

import com.abonent.model.AbonentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface AbonentIdRepository extends CrudRepository<AbonentId, UUID> {
    CompletableFuture<List<AbonentId>> findAbonentsByCtnIn(List<String> ctn);
}
