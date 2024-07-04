package com.invext.ticket_test.controller;


import com.invext.ticket_test.dto.TicketCreateDto;
import com.invext.ticket_test.dto.TicketDetailDto;
import com.invext.ticket_test.enums.TicketStatus;
import com.invext.ticket_test.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Object> createTicket(@RequestBody @Valid TicketCreateDto ticketDto) {
        var ticket = ticketService.createTicket(ticketDto);

        return ResponseEntity.ok(new TicketDetailDto(ticket));
    }

    @GetMapping("/{departmentId:\\d+}")
    public ResponseEntity<Object> getAllByDepartment(@PathVariable Long departmentId) {
        var tickets = ticketService.getAllByDepartmentId(departmentId);

        return ResponseEntity.ok(tickets.stream().map(TicketDetailDto::new).toList());
    }

    @PatchMapping("/change-status")
    public ResponseEntity<Object> changeStatus(@RequestParam Long ticketId, @RequestParam TicketStatus status, @RequestParam Long departmentMemberId) {

        var ticket = ticketService.changeStatus(departmentMemberId, ticketId, status);
        return ResponseEntity.ok(new TicketDetailDto(ticket));
    }


    @PatchMapping("/next")
    public ResponseEntity<Object> startNextTicket(@RequestParam Long departmentMemberId) {

        var ticket = ticketService.startNextTicket(departmentMemberId);
        return ResponseEntity.ok(new TicketDetailDto(ticket));
    }
}
