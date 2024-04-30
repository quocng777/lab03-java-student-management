package com.quocnguyen.studentmanagement.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@NoArgsConstructor
public class StudentDTO {
    private Integer id;

    @NotNull
    @Size(max = 50, message = "Name must not be longer than 50 characters")
    private String name;

    @NotNull
    private Date birthday;

    @NotNull
    @Size(max = 512, message = "Address must not be longer than 512 characters")
    private String address;

    private String notes;

    public StudentDTO(Student student)  {
        this.id = student.getId();
        this.name = student.getName();
        this.birthday = student.getBirthday();
        this.address = student.getAddress();
        this.notes = student.getNotes();
    }
}
