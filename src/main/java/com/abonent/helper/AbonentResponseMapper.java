package com.abonent.helper;

import com.abonent.dto.AbonentResponse;
import com.abonent.dto.RandomUserResponse;
import com.abonent.model.AbonentId;

import java.util.List;

public class AbonentResponseMapper {


    /*
     * Method maps list of RandomUserResponse to list of AbonentResponse
     *
     */
    public static List<AbonentResponse> mapRandomUserToAbonentResponse(List<RandomUserResponse> randomUserResponses, List<AbonentResponse> abonentResponses) {

        for (int i = 0; i < abonentResponses.size(); i++) {
            abonentResponses.get(i).setName(randomUserResponses.get(i).getResults()[0].getName().getFirst() + " " + randomUserResponses.get(i).getResults()[0].getName().getLast());
            abonentResponses.get(i).setEmail(randomUserResponses.get(i).getResults()[0].getEmail());
        }
        return abonentResponses;
    }


    /*
     * Method maps list of AbonentId to list of AbonentResponse
     *
     */
    public static List<AbonentResponse> mapAbonentIdToAbonentResponse(List<AbonentId> abonentIds, List<AbonentResponse> abonentResponses) {
        for (int i = 0; i < abonentResponses.size(); i++) {
            abonentResponses.get(i).setAbonentId(String.valueOf(abonentIds.get(i).getId()));
            abonentResponses.get(i).setCtn(abonentIds.get(i).getCtn());
        }
        return abonentResponses;
    }

}
