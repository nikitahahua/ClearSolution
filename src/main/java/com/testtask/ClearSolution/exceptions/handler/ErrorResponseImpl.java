package com.testtask.ClearSolution.exceptions.handler;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class ErrorResponseImpl implements ErrorResponse {

    private final HttpStatusCode errorCode;
    private final String errorMessage;

    public ErrorResponseImpl(HttpStatusCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return errorCode;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail details = ProblemDetail.forStatus(errorCode);
        details.setDetail(errorMessage);
        return details;
    }

}