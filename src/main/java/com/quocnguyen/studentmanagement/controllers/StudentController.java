package com.quocnguyen.studentmanagement.controllers;

import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {
    @GetMapping("/abc")
    public String getIndexPage() {
        return "students";
    }

}
