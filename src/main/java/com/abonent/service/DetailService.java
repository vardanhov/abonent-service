package com.abonent.service;

import com.abonent.dto.AbonentResponse;
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
    private static List<AbonentResponse> abonentResponses;
    public Logger logger = LoggerFactory.getLogger("DetailService");

    @Autowired
    public DetailService(SessionsRepository sessionsRepository, ProfileService profileService) {
        this.sessionsRepository = sessionsRepository;
        this.profileService = profileService;
    }

    @Async
    public CompletableFuture<Map<String, Object>> getAbonentsAndProfilesByCellId(String cellId) throws ExecutionException, InterruptedException {
        logger.info(" hsdjskd ");
        List<String> ctnList = getSessionsCtnsByCellId(cellId);

        abonentResponses = ctnList.stream().map(a -> new AbonentResponse()).toList();

        CompletableFuture<List<AbonentResponse>> abonentResponseListFromDatabase = profileService.getAbonentsFromDatabase(ctnList, abonentResponses)
                .completeOnTimeout(null, 2, TimeUnit.SECONDS);

        if (abonentResponseListFromDatabase.get() != null) {
            try {
                abonentResponses = abonentResponseListFromDatabase.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        CompletableFuture<List<AbonentResponse>> listCompletableFuture1 = profileService.generateAbonentList(ctnList, abonentResponses)
                .completeOnTimeout(null, 10, TimeUnit.SECONDS);

        if (listCompletableFuture1.get() != null) {
            abonentResponses = listCompletableFuture1.get();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("total", abonentResponses.size());
        data.put("results", abonentResponses.toArray());
        return CompletableFuture.completedFuture(data);
    }

    public List<String> getSessionsCtnsByCellId(String cellId) {

        logger.info("Getting phone numbers (ctn) by cellId from sessions table");
        List<Sessions> sessions = sessionsRepository.findSessionsByCellId(cellId);
        return sessions.stream().map(x -> x.getCtn().getCtn()).toList();
    }
}

