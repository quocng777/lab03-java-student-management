package com.quocnguyen.studentmanagement.controllers;

import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class StudentController {
    @GetMapping("/")
    public String getIndexPage() {
        return "students";
    }

    @GetMapping("/students/{id}")
    public String getStudentDetails(@PathVariable(name = "id") String id, ModelMap modelMap) {

        log.debug("Student id" + id);

        modelMap.addAttribute("studentId", id);

        return "student";
    }

}
