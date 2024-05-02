package com.quocnguyen.studentmanagement.repositories;

import com.quocnguyen.studentmanagement.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findByYearEquals(int year, Pageable pageable);

    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
