package com.placement.placementeligibilitychecker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "approved_students")
public class ApprovedStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    public ApprovedStudent() {}

    public ApprovedStudent(String email) {
        this.email = email;
    }

    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}