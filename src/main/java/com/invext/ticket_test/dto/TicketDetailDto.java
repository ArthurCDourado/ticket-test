package com.invext.ticket_test.dto;

import com.invext.ticket_test.entity.Ticket;
import com.invext.ticket_test.enums.TicketStatus;

import java.time.OffsetDateTime;

public class TicketDetailDto {

    private Long id;
    private String topic;
    private String message;
    private TicketStatus status;
    private OffsetDateTime createdDate;

    public TicketDetailDto() {}

    public TicketDetailDto(Ticket ticket) {
        this.id = ticket.getId();
        this.topic = ticket.getTopic();
        this.message = ticket.getMessage();
        this.status = ticket.getTicketStatus();
        this.createdDate = ticket.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
