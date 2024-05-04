package com.quocnguyen.studentmanagement.rest_controllers;


import com.quocnguyen.studentmanagement.entities.*;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import com.quocnguyen.studentmanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("{id}")
    public ResponseEntity<DataResponse<CourseDTO>> getCourseById(@PathVariable("id") int courseId) {
        CourseDTO course;
        try {
            course = service.getById(courseId);

        } catch(ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Course id \"%d\" not found", courseId));
        }

        return ResponseEntity.ok(new DataResponse<>(course));
    }

    @PutMapping("{id}")
    public ResponseEntity<DataResponse<CourseDTO>> updateStudent(
            @PathVariable(name = "id") String id ,
            @RequestBody @Valid CourseDTO course) {

        int courseId;
        try {
            courseId = Integer.parseInt(id);

            //call to service to perform transaction
            course = service.update(courseId, course);

        } catch (NumberFormatException | ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Student with id \"%s\" not found", course.getId()));
        }

        return ResponseEntity.ok(new DataResponse<>(course));
    }

    @GetMapping("{id}/available-students")
    public ResponseEntity<DataResponse<List<StudentDTO>>> getAvailableStudent(
            @PathVariable("id") int courseId,
            @RequestParam(defaultValue = "") String keyword
    ) {
        List<StudentDTO> students = service.getAvailableStudent(courseId, keyword);
        log.debug(students.toString());

        return ResponseEntity.ok(new DataResponse<>(students));
    }

    @PostMapping("{id}/add-student")
    public ResponseEntity<DataResponse<CourseStudentDTO>> addStudentToCourse(
            @RequestBody @Valid CourseStudentDTO courseStudent
    ) {
        CourseStudentDTO savedObj = service.addStudentToCourse(courseStudent);

        return ResponseEntity.ok(new DataResponse<>(savedObj));
    }

    @DeleteMapping("{courseId}/students/{studentId}")
    public ResponseEntity<String> deleteStudentFromCourse(
            @PathVariable("courseId") int courseId,
            @PathVariable("studentId") int studentId
    ) {
        service.deleteStudentFromCourse(courseId, studentId);

        return ResponseEntity.ok("Deleted student from course successfully");
    }


}
