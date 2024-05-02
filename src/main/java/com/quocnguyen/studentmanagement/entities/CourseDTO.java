package com.quocnguyen.studentmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Integer id;
    private String name;
    private String lecture;
    private int year;
    private List<StudentGradeDTO> students;


    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.lecture = course.getLecture();
        this.year = course.getYear();
        this.students = course
                .getStudents()
                .stream()
                .map(StudentGradeDTO::new)
                .toList();
    }
}
