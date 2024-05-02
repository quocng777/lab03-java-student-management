package com.quocnguyen.studentmanagement.entities;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseStudentDTO {
    private int id;

    @NotNull
    private int studentId;

    @NotNull
    private int courseId;

    private Double grade;

    public CourseStudentDTO(CourseStudent courseStudent) {
        this.id = courseStudent.getId();
        this.studentId = courseStudent.getStudent().getId();
        this.courseId = courseStudent.getCourse().getId();
        this.grade = courseStudent.getGrade();
    }
}
