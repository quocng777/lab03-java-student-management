package com.quocnguyen.studentmanagement.services;


import com.quocnguyen.studentmanagement.entities.Course;
import com.quocnguyen.studentmanagement.entities.CourseDTO;
import com.quocnguyen.studentmanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
}
