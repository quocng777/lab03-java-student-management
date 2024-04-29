package com.quocnguyen.studentmanagement.rest_controllers;

import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentRESTController {
    private final StudentService service;
    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {




        List<Student> students = service.getStudents(page, sortBy, sortDir);

        return ResponseEntity.ok(students);
    }
}
