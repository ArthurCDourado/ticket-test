package com.invext.ticket_test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String topic;

    private boolean defaultDepartment = false;

    @OneToMany(mappedBy = "department")
    private List<DepartmentMember> departmentMembers;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<DepartmentMember> getDepartmentMembers() {
        return departmentMembers;
    }

    public void setDepartmentMembers(List<DepartmentMember> departmentMembers) {
        this.departmentMembers = departmentMembers;
    }

    public boolean isDefaultDepartment() {
        return defaultDepartment;
    }

    public void setDefaultDepartment(boolean defaultDepartment) {
        this.defaultDepartment = defaultDepartment;
    }
}
