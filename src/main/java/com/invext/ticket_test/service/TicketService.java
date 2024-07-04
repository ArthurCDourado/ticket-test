package com.invext.ticket_test.service;

import com.invext.ticket_test.dto.TicketCreateDto;
import com.invext.ticket_test.entity.Ticket;
import com.invext.ticket_test.enums.TicketStatus;
import com.invext.ticket_test.exceptions.ForbbidenException;
import com.invext.ticket_test.exceptions.NotFoundException;
import com.invext.ticket_test.repository.DepartmentMemberRepository;
import com.invext.ticket_test.repository.DepartmentRepository;
import com.invext.ticket_test.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class TicketService {

    private static final int MAX_TICKETS_PER_MEMBER = 3;
    private final TicketRepository ticketRepository;

    private final DepartmentMemberRepository departmentMemberRepository;
    private final Map<TicketStatus, BiFunction<Long, Long, Ticket>> statusChangeFunc = Map.of(
            TicketStatus.IN_PROGRESS, this::moveToInProgress,
            TicketStatus.CONCLUDED, this::moveToConcluded
    );

    public TicketService(TicketRepository ticketRepository,
                         DepartmentMemberRepository departmentMemberRepository) {
        this.ticketRepository = ticketRepository;
        this.departmentMemberRepository = departmentMemberRepository;
    }

    public Ticket createTicket(TicketCreateDto ticketDto) {
        Ticket ticket = new Ticket();

        ticket.setMessage(ticketDto.getMessage());
        ticket.setTopic(ticketDto.getTopic());

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllByDepartmentId(Long departmentId) {
        return ticketRepository.findAllByDepartmentId(departmentId);
    }

    public Ticket startNextTicket(Long departmentMemberId) {
        var ticketId = ticketRepository.findNextTicketIdByDepartmentMemberId(departmentMemberId)
                .orElseThrow(() -> new NotFoundException("No pending tickets found"));

        return moveToInProgress(departmentMemberId, ticketId);
    }

    public Ticket changeStatus(Long departmentMemberId, Long ticketId, TicketStatus status) {
        return statusChangeFunc.getOrDefault(status, (a, b) -> {
            throw new ForbbidenException("Cannot change status to " + status);
        }).apply(departmentMemberId, ticketId);
    }

    private Ticket moveToConcluded(Long departmentMemberId, Long ticketId) {
        ticketRepository.changeStatus(ticketId, departmentMemberId, TicketStatus.CONCLUDED);
        return ticketRepository.findByIdAndDepartmentMemberId(ticketId, departmentMemberId)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
    }

    public Ticket moveToInProgress(Long departmentMemberId, Long ticketId) {
        var userHasMaxTicketsInProgress = ticketRepository.existMoreThanByUser(
                MAX_TICKETS_PER_MEMBER, departmentMemberId, TicketStatus.IN_PROGRESS);
        if (userHasMaxTicketsInProgress) {
            throw new ForbbidenException("Status change not available");
        }

        var departmentMember = departmentMemberRepository.findById(departmentMemberId)
                .orElseThrow(() -> new NotFoundException("Department Member not found"));

        var ticket = ticketRepository.findByIdAndDepartmentId(ticketId, departmentMember.getDepartment().getId())
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        ticket.setTicketStatus(TicketStatus.IN_PROGRESS);
        ticket.setDepartmentMember(departmentMember);
        return ticketRepository.save(ticket);
    }
}
