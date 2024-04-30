package com.quocnguyen.studentmanagement.rest_controllers;

import com.quocnguyen.studentmanagement.entities.CollectionResponse;
import com.quocnguyen.studentmanagement.entities.StudentDTO;
import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ExposesResourceFor(Student.class)
@RequestMapping("/api/students")
@RequiredArgsConstructor
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
    public ResponseEntity<String> getStudentById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok("dsfa");
    }
}
