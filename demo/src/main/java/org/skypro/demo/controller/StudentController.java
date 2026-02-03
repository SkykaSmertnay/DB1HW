package org.skypro.demo.controller;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    // READ by id
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    // READ all
    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id,
                          @RequestBody Student student) {
        return studentService.update(id, student);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
    @GetMapping("/students/age")
    public List<Student> getStudentsByAgeBetween(
            @RequestParam int min,
            @RequestParam int max
    ) {
        return studentService.getStudentsByAgeBetween(min, max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
}
