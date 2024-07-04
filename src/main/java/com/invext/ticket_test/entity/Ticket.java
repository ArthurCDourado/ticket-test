package com.invext.ticket_test.entity;

import com.invext.ticket_test.enums.TicketStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private DepartmentMember departmentMember;

    @NotBlank
    private String topic;

    @NotBlank
    private String message;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.PENDING;

    private OffsetDateTime createdDate = OffsetDateTime.now();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public DepartmentMember getDepartmentMember() {
        return departmentMember;
    }

    public void setDepartmentMember(DepartmentMember departmentMember) {
        this.departmentMember = departmentMember;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
