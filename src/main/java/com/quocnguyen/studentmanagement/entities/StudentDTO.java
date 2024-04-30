package com.quocnguyen.studentmanagement.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
public class StudentDTO {
    private Integer id;
    private String name;
    private Date birthday;
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
