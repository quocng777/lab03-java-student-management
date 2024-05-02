package com.quocnguyen.studentmanagement.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String lecture;

    @NotNull
    private int year;

    private String notes;

    private List<StudentGradeDTO> students;


    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.lecture = course.getLecture();
        this.year = course.getYear();
        this.notes = course.getNotes();
        this.students = course
                .getStudents()
                .stream()
                .map(StudentGradeDTO::new)
                .toList();
    }
}
