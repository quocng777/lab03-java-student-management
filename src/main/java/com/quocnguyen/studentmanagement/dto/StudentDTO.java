package com.quocnguyen.studentmanagement.dto;


import com.quocnguyen.studentmanagement.entities.Student;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO {
    private int id;
    private String name;
    private Date birthday;
    private String address;
    private String notes;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.birthday = student.getBirthday();
        this.address = student.getAddress();
        this.notes = student.getNotes();
    }
}
