package com.abonent.service;

import com.abonent.dto.AbonentResponse;
import com.abonent.dto.RandomUserResponse;
import com.abonent.helper.AbonentResponseMapper;
import com.abonent.model.AbonentId;
import com.abonent.model.Sessions;
import com.abonent.repo.SessionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class DetailService {
    private final SessionsRepository sessionsRepository;
    private final ProfileService profileService;
    private final AbonentIdService abonentIdService;
    private static List<AbonentResponse> abonentResponses;
    public Logger logger = LoggerFactory.getLogger("DetailService");

    @Autowired
    public DetailService(SessionsRepository sessionsRepository, ProfileService profileService, AbonentIdService abonentIdService) {
        this.sessionsRepository = sessionsRepository;
        this.profileService = profileService;
        this.abonentIdService = abonentIdService;
    }

    @Async
    public CompletableFuture<Map<String, Object>> getAbonentsAndProfilesByCellId(String cellId) {

        List<String> ctnList = getSessionsCtnsByCellId(cellId);

        abonentResponses = ctnList.stream().map(a -> new AbonentResponse()).toList();

        logger.info("Getting List of AbonentId from Database");
        CompletableFuture<List<AbonentId>> abonentIdList = abonentIdService.getAbonentsFromDatabase(ctnList)
                .completeOnTimeout(null, 2, TimeUnit.SECONDS);

        try {

            if (abonentIdList.get() != null) {
                AbonentResponseMapper.mapAbonentIdToAbonentResponse(abonentIdList.get(),abonentResponses);
            }

        } catch (InterruptedException | ExecutionException e) {
            logger.error("Throw InterruptedException");
            throw new RuntimeException(e);
        }

        logger.info("Generating List of random user");
        CompletableFuture<List<RandomUserResponse>> randomUserResponseList = profileService.generateRandomUserResponseList(ctnList)
                .completeOnTimeout(null, 10, TimeUnit.SECONDS);

        try {
            if (randomUserResponseList.get() != null) {
                 AbonentResponseMapper.mapRandomUserToAbonentResponse(randomUserResponseList.get(),abonentResponses);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Throw InterruptedException");
            throw new RuntimeException(e);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total", abonentResponses.size());
        data.put("results", abonentResponses.toArray());
        return CompletableFuture.completedFuture(data);
    }


    /*
     * Method get list of sessions by ctn
     *
     */
    public List<String> getSessionsCtnsByCellId(String cellId) {

        logger.info("Getting phone numbers (ctn) by cellId from sessions table");
        List<Sessions> sessions = sessionsRepository.findSessionsByCellId(cellId);
        return sessions.stream().map(x -> x.getCtn().getCtn()).toList();
    }
}

