package com.quocnguyen.studentmanagement.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseGradeDTO {
    private int id;
    private String name;
    private double grade;
    private int year;

    public CourseGradeDTO(CourseStudent courseStudent) {
        this.id = courseStudent.getCourse().getId();
        this.name = courseStudent.getCourse().getName();
        this.grade = courseStudent.getGrade();
        this.year = courseStudent.getCourse().getYear();
    }
}
