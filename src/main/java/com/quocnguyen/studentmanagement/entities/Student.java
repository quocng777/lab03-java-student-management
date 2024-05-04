package com.quocnguyen.studentmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Table(name = "student")
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Date birthday;

    @Column(length = 255)
    private String address;
    private String notes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "student")
    List<CourseStudent> courses = new ArrayList<>();


}