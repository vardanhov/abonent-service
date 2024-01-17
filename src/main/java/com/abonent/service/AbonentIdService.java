package com.abonent.service;

import com.abonent.model.AbonentId;
import com.abonent.repo.AbonentIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AbonentIdService {

    private final AbonentIdRepository abonentIdRepository;
    public Logger logger = LoggerFactory.getLogger("AbonentIdService");

    @Autowired
    public AbonentIdService(AbonentIdRepository abonentIdRepository) {
        this.abonentIdRepository = abonentIdRepository;
    }

    /*
     * Method generate random User
     *
     */
    @Async
    public CompletableFuture<List<AbonentId>> getAbonentsFromDatabase(List<String> list) {

        logger.info("Find abonents by phone number");
        return abonentIdRepository.findAbonentsByCtnIn(list);
    }

}
