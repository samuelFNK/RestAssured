package com.restassured.restassured.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PostOwnershipViolationException extends RuntimeException {

    private String callerId;
    private Long postId;

    public PostOwnershipViolationException(String callerId, Long postId) {

        super(String.format("Caller id %s does not have ownership over post id %s.", callerId, postId));

        this.callerId = callerId;
        this.postId = postId;
    }
}
