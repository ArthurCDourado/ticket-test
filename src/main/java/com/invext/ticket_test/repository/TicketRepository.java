package com.invext.ticket_test.repository;

import com.invext.ticket_test.entity.Ticket;
import com.invext.ticket_test.enums.TicketStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select (count(t) >= :amount) from Ticket as t where t.departmentMember.id = :departmentMemberId and t.ticketStatus = :status")
    boolean existMoreThanByUser(int amount, Long departmentMemberId, TicketStatus status);

    @Query("select t from Ticket t join Department d on (d.topic = t.topic or (d.defaultDepartment = true and not exists (select dep from Department dep where dep.topic = t.topic))) where t.id = :id and d.id = :departmentId")
    Optional<Ticket> findByIdAndDepartmentId(Long id, Long departmentId);

    @Query("select t from Ticket t join Department d on (d.topic = t.topic or (d.defaultDepartment = true and not exists (select dep from Department dep where dep.topic = t.topic))) where d.id = :departmentId")
    List<Ticket> findAllByDepartmentId(Long departmentId);

    Optional<Ticket> findByIdAndDepartmentMemberId(Long id, Long departmentMemberId);

    @Query("""
    select t.id from Ticket t join DepartmentMember d on (
        d.department.topic = t.topic or
        (d.department.defaultDepartment = true and not exists (select dep from Department dep where dep.topic = t.topic))
    )
    where d.id = :departmentMemberId and t.ticketStatus = com.invext.ticket_test.enums.TicketStatus.PENDING
    order by t.createdDate limit 1
    """)
    Optional<Long> findNextTicketIdByDepartmentMemberId(Long departmentMemberId);

    @Transactional
    @Modifying
    @Query("update Ticket set ticketStatus = :newStatus where id = :id and departmentMember.id = :departmentMemberId")
    void changeStatus(Long id, Long departmentMemberId, TicketStatus newStatus);
}
