package com.abonent.service;


import com.abonent.dto.AbonentResponse;
import com.abonent.dto.RandomUserResponse;
import com.abonent.model.AbonentId;
import com.abonent.repo.AbonentIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class ProfileService {

    @Value("${user.generation.address}")
    private String userGenerationAddress;
    private final AbonentIdRepository abonentIdRepository;
    public Logger logger = LoggerFactory.getLogger("ProfileService");

    @Autowired
    public ProfileService(AbonentIdRepository abonentIdRepository) {
        this.abonentIdRepository = abonentIdRepository;

    }

    @Async
    public CompletableFuture<List<AbonentResponse>> getAbonentsFromDatabase(List<String> list, List<AbonentResponse> abonentResponses) {

        logger.info("Find abonents by phone number");
        List<AbonentId> abonents = abonentIdRepository.findAbonentsByCtnIn(list);

        for (int i = 0; i < abonentResponses.size(); i++) {
            abonentResponses.get(i).setAbonentId(String.valueOf(abonents.get(1).getId()));
            abonentResponses.get(i).setCtn(abonents.get(1).getCtn());
        }

        return CompletableFuture.completedFuture(abonentResponses);
    }

    @Async
    public CompletableFuture<List<AbonentResponse>> generateAbonentList(List<String> list, List<AbonentResponse> responses) {

        for (int i = 0; i < responses.size(); i++) {
            RandomUserResponse randomUserResponse = getRandomUserResponse(list.get(i));
            responses.get(i).setName(randomUserResponse.getResults()[0].getName().getFirst() + " " + randomUserResponse.getResults()[0].getName().getLast());
            responses.get(i).setEmail(randomUserResponse.getResults()[0].getEmail());
        }
        return CompletableFuture.completedFuture(responses);
    }

    public RandomUserResponse getRandomUserResponse(String ctn) {

        WebClient getWebClient = WebClient.create(userGenerationAddress);

        return getWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/")
                        .queryParam("phone", ctn)
                        .queryParam("inc", "name,email")
                        .build())
                .retrieve()
                .bodyToMono(RandomUserResponse.class)
                .doOnError(error -> this.logger.error("Something went wrong with data retrieval {}", error.getMessage()))
                .onErrorResume(error -> Mono.just(new RandomUserResponse("Something went wrong")))
                .block();
    }
}
