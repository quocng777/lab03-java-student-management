package com.quocnguyen.studentmanagement.rest_controllers;


import com.quocnguyen.studentmanagement.entities.CollectionResponse;
import com.quocnguyen.studentmanagement.entities.CourseDTO;
import com.quocnguyen.studentmanagement.entities.DataResponse;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import com.quocnguyen.studentmanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRestController {
    private static final int NUMBER_COURSERS_PER_PAGE = 15;
    private final CourseService service;

    @GetMapping
    public ResponseEntity<CollectionResponse<CourseDTO>> getCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") String keyword
            ) {

        Sort sort = sortDir.equals("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                    page - 1,
                    NUMBER_COURSERS_PER_PAGE,
                    sort
                    );

        Page<CourseDTO> courses = service.getCourses(pageable, keyword);

        return ResponseEntity.ok(new CollectionResponse<>(courses));

    }

    @PostMapping
    public ResponseEntity<DataResponse<CourseDTO>> createNewCourse(@RequestBody @Valid CourseDTO course) {

        CourseDTO savedCourse = service.create(course);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(savedCourse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DataResponse<>(savedCourse));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable(name = "id", required = true) Integer id) {
        try {
            service.delete(id);
        } catch(ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Course id \"%s\" not found", id));
        }

        return ResponseEntity.ok("Deleted course successfully");
    }


}
