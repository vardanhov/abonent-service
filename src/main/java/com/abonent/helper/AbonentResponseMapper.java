package com.abonent.helper;

import com.abonent.dto.AbonentResponse;
import com.abonent.model.AbonentId;

import java.util.ArrayList;
import java.util.List;

public class AbonentResponseMapper {
    public static List<AbonentResponse> mapToAbonentRsponse(List<AbonentId> list) {

        List<AbonentResponse> abonentResponses = new ArrayList<>();

        for (AbonentId abonentId : list) {
            AbonentResponse abonentResponse = new AbonentResponse();
            abonentResponse.setAbonentId(String.valueOf(abonentId.getId()));
            abonentResponse.setCtn(abonentId.getCtn());
            abonentResponse.setName(abonentId.getName());
            abonentResponse.setEmail(abonentId.getEmail());
            abonentResponses.add(abonentResponse);
        }
        return abonentResponses;
    }
}
