package com.quocnguyen.studentmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @GetMapping
    public String getCoursesPage() {
        return "courses";
    }

    @GetMapping("/{id}")
    public String getCourseDetailsPage(
            @PathVariable(name = "id", required = true) String id,
            ModelMap modelMap
    ) {
        modelMap.addAttribute("courseId", id);

        return "course";
    }

}
