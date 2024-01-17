package com.abonent.repo;

import com.abonent.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionsRepository extends JpaRepository<Sessions, Integer> {
    List<Sessions> findSessionsByCellId(String cellId);
}
