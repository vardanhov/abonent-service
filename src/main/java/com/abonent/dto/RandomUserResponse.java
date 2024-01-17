package com.abonent.dto;

import com.abonent.exception.ErrorResponse;

public class RandomUserResponse {
    public RandomUserResponse() {
    }

    public RandomUserResponse(String errorMessage) {
        this.error = new ErrorResponse(errorMessage);
    }

    private ErrorResponse error;

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    private User[] results;

    public User[] getResults() {
        return this.results;
    }

    public void setResults(User[] users) {
        this.results = users;
    }
}