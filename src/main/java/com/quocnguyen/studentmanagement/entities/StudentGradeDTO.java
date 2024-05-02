package com.quocnguyen.studentmanagement.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentGradeDTO {
    private int id;
    private double grade;
    private String name;

    public StudentGradeDTO(CourseStudent courseStudent)  {
        this.id = courseStudent.getStudent().getId();
        this.grade = courseStudent.getGrade();
        this.name = courseStudent.getStudent().getName();
    }
}
