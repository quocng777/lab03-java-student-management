package com.quocnguyen.studentmanagement.rest_controllers;

import com.quocnguyen.studentmanagement.entities.CollectionResponse;
import com.quocnguyen.studentmanagement.entities.DataResponse;
import com.quocnguyen.studentmanagement.entities.StudentDTO;
import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import com.quocnguyen.studentmanagement.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@ExposesResourceFor(Student.class)
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentRESTController {
    private static final int NUMBER_STUDENT_PER_PAGE = 12;

    private final StudentService service;
    @GetMapping
    public ResponseEntity<CollectionResponse<StudentDTO>> getStudents(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        int pageNo;
        try {
            pageNo = Integer.parseInt(page);
            if(pageNo < 1)
                pageNo = 0;
            else
                pageNo -= 1;
        } catch(Exception e) {
            pageNo = 0;
        }

        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, NUMBER_STUDENT_PER_PAGE, sort);

        Page<StudentDTO> paging = service.getStudents(pageable);

        CollectionResponse<StudentDTO> response = new CollectionResponse<>(paging);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<StudentDTO>> getStudentById(@PathVariable(name = "id") String id) {

        int studentId;
        StudentDTO student;
        try {
            studentId = Integer.parseInt(id);
            // call to the service
            student = service.getStudentById(studentId);
        } catch(NumberFormatException | ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Student with id \"%s\" not found", id));
        }

        return ResponseEntity.ok(new DataResponse<>(student));
    }

    @PostMapping
    public ResponseEntity<DataResponse<StudentDTO>> createNewStudent(@Valid @RequestBody StudentDTO student) {

        //logging
        log.debug(student.toString());

        //TODO: call service to handle transaction
        final StudentDTO savedStudent =  service.create(student);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUri();

        log.debug(uri.toString());

        return ResponseEntity.created(uri).body(new DataResponse<>(savedStudent));
    }
}


