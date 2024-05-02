package com.quocnguyen.studentmanagement.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class StudentCourseDTO {
    private int id;
    private String name;
    private double grade;
    private int year;

    public StudentCourseDTO (CourseStudent courseStudent) {
        this.id = courseStudent.getCourse().getId();
        this.name = courseStudent.getCourse().getName();
        this.grade = courseStudent.getGrade();
        this.year = courseStudent.getCourse().getYear();
    }
}
