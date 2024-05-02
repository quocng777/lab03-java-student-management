package com.quocnguyen.studentmanagement.rest_controllers;


import com.quocnguyen.studentmanagement.entities.CollectionResponse;
import com.quocnguyen.studentmanagement.entities.CourseDTO;
import com.quocnguyen.studentmanagement.services.CourseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
