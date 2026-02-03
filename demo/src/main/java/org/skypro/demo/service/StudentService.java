package org.skypro.demo.service;


import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student not found"
                ));
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student update(Long id, Student updatedStudent) {
        Student existing = getById(id);
        existing.setName(updatedStudent.getName());
        existing.setAge(updatedStudent.getAge());
        return studentRepository.save(existing);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
    public List<Student> getStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
    public Faculty getStudentFaculty(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getFaculty();
    }
}
