package com.quocnguyen.studentmanagement.entities;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentDTO {
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 50, message = "Name must not be longer than 50 characters")
    private String name;

    @NotNull
    private Date birthday;

    @NotBlank
    @Size(min = 1, max = 512, message = "Address must not be longer than 512 characters")
    private String address;

    private String notes;
    private List<CourseGradeDTO> courses;

    public StudentDTO(Student student)  {
        this.id = student.getId();
        this.name = student.getName();
        this.birthday = student.getBirthday();
        this.address = student.getAddress();
        this.notes = student.getNotes();
        this.courses = student.getCourses().stream().map(CourseGradeDTO::new).toList();
    }
}
