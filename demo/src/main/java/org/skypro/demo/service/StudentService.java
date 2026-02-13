package org.skypro.demo.service;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        logger.info("Was invoked method create");
        logger.debug("Creating student: name={}, age={}", student.getName(), student.getAge());
        Student saved = studentRepository.save(student);
        logger.debug("Student created with id={}", saved.getId());
        return saved;
    }

    public Student getById(Long id) {
        logger.info("Was invoked method getById");
        logger.debug("Searching student by id={}", id);

        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Student not found by id={}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Student not found"
                    );
                });
    }

    public List<Student> getAll() {
        logger.info("Was invoked method getAll");
        List<Student> students = studentRepository.findAll();
        logger.debug("Found {} students", students.size());
        return students;
    }

    public Student update(Long id, Student updatedStudent) {
        logger.info("Was invoked method update");
        logger.debug("Updating student id={}, newName={}, newAge={}",
                id, updatedStudent.getName(), updatedStudent.getAge());

        Student existing = getById(id); // тут уже есть логи

        existing.setName(updatedStudent.getName());
        existing.setAge(updatedStudent.getAge());

        Student saved = studentRepository.save(existing);
        logger.debug("Student updated id={}", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        logger.info("Was invoked method delete");
        logger.debug("Deleting student id={}", id);

        studentRepository.deleteById(id);
        logger.debug("Delete executed for id={}", id);
    }

    public List<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method getStudentsByAgeBetween");
        logger.debug("Searching students with age between {} and {}", min, max);

        List<Student> students = studentRepository.findByAgeBetween(min, max);
        logger.debug("Found {} students in age range", students.size());
        return students;
    }

    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Was invoked method getStudentFaculty");
        logger.debug("Getting faculty for studentId={}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found while getting faculty, studentId={}", studentId);
                    return new RuntimeException("Student not found");
                });

        Faculty faculty = student.getFaculty();
        if (faculty == null) {
            logger.warn("Student id={} has no faculty assigned", studentId);
        } else {
            logger.debug("Faculty for studentId={} is facultyId={}", studentId, faculty.getId());
        }

        return faculty;
    }

    public long getStudentsCount() {
        logger.info("Was invoked method getStudentsCount");
        long count = studentRepository.getStudentsCount();
        logger.debug("Students count={}", count);
        return count;
    }

    public Double getStudentsAverageAge() {
        logger.info("Was invoked method getStudentsAverageAge");

        Double avg = studentRepository.getStudentsAverageAge();
        Double result = Objects.requireNonNullElse(avg, 0.0);

        if (avg == null) {
            logger.warn("Average age is null (probably no students). Returning 0.0");
        } else {
            logger.debug("Average age calculated={}", result);
        }

        return result;
    }

    public List<Student> getLast5Students() {
        logger.info("Was invoked method getLast5Students");
        List<Student> students = studentRepository.findLast5Students();
        logger.debug("Found {} last students", students.size());
        return students;
    }
}
