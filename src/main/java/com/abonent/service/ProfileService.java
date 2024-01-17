package com.abonent.service;


import com.abonent.dto.RandomUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Service
public class ProfileService {

    @Value("${user.generation.address}")
    private String userGenerationAddress;
    public Logger logger = LoggerFactory.getLogger("ProfileService");


    /*
     * Method generates random List of RandomUserResponse
     *
     */
    @Async
    public CompletableFuture<List<RandomUserResponse>> generateRandomUserResponseList(List<String> list) {

        List<RandomUserResponse> randomUserResponses = list.stream().filter(Objects::nonNull).map(this::getRandomUserResponse).toList();

        return CompletableFuture.completedFuture(randomUserResponses);
    }

    /*
     * Method generates random User
     *
     */
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
