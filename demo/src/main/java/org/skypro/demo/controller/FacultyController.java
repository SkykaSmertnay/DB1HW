package org.skypro.demo.controller;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getById(@PathVariable Long id) {
        return facultyService.getById(id);
    }

    @GetMapping
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable Long id,
                          @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facultyService.delete(id);
    }

    @GetMapping("/search")
    public List<Faculty> searchFaculties(@RequestParam String query) {
        return facultyService.findByNameOrColor(query);
    }

    @GetMapping("/{id}/students")
    public List<Student> getFacultyStudents(@PathVariable Long id) {
        return facultyService.getFacultyStudents(id);
    }

    @GetMapping("/faculties/longest-name")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }

}
