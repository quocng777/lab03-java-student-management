package com.quocnguyen.studentmanagement.services;


import com.quocnguyen.studentmanagement.entities.Course;
import com.quocnguyen.studentmanagement.entities.CourseDTO;
import com.quocnguyen.studentmanagement.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository repository;


    public Page<CourseDTO> getCourses(Pageable pageable, String keyword) {
        Page<Course> courses;
        if(keyword.matches("^(19|20)\\d{2}$")) { // check if the keyword is year formatted
            int year = Integer.parseInt(keyword);
            courses = repository.findByYearEquals(year, pageable);
        } else {
            courses = repository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        return courses.map(CourseDTO::new);
    }

    @Transactional
    public CourseDTO create(CourseDTO course) {
        Course newCourse = Course
                .builder()
                .name(course.getName())
                .lecture(course.getLecture())
                .year(course.getYear())
                .notes(course.getNotes())
                .students(new ArrayList<>())
                .build();


        repository.save(newCourse);

        log.debug(newCourse.getStudents().toString());

        return new CourseDTO(newCourse);
    }
}
