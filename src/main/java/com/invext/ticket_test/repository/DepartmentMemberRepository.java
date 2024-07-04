package com.invext.ticket_test.repository;

import com.invext.ticket_test.entity.DepartmentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMemberRepository extends JpaRepository<DepartmentMember, Long> {

}
