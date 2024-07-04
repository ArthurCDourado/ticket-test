package com.invext.ticket_test.dto;

import jakarta.validation.constraints.NotBlank;

public class TicketUpdateDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String topic;

    @NotBlank
    private String message;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
