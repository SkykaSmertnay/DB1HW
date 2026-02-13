package org.skypro.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method createFaculty");
        logger.debug("Creating faculty: name={}, color={}", faculty.getName(), faculty.getColor());
        return facultyRepository.save(faculty);
    }

    public Faculty getById(Long id) {
        logger.info("Was invoked method getFacultyById");
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Faculty not found with id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found");
                });
    }

    public List<Faculty> getAll() {
        logger.info("Was invoked method getAllFaculties");
        List<Faculty> faculties = facultyRepository.findAll();
        logger.debug("Found {} faculties", faculties.size());
        return faculties;
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        logger.info("Was invoked method updateFaculty");
        Faculty existing = getById(id);
        existing.setName(updatedFaculty.getName());
        existing.setColor(updatedFaculty.getColor());
        return facultyRepository.save(existing);
    }

    public void delete(Long id) {
        logger.info("Was invoked method deleteFaculty");
        facultyRepository.deleteById(id);
        logger.warn("Faculty with id={} was deleted", id);
    }

    public List<Faculty> findByNameOrColor(String value) {
        logger.info("Was invoked method findFacultyByNameOrColor");
        logger.debug("Search value={}", value);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(value, value);
    }

    public List<Student> getFacultyStudents(Long facultyId) {
        logger.info("Was invoked method getFacultyStudents");
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> {
                    logger.error("Faculty not found with id={}", facultyId);
                    return new RuntimeException("Faculty not found");
                });

        List<Student> students = faculty.getStudents();
        logger.debug("Faculty id={} has {} students", facultyId, students.size());
        return students;
    }

    public String getLongestFacultyName() {
        logger.info("Was invoked method getLongestFacultyName");

        List<Faculty> faculties = facultyRepository.findAll();
        logger.debug("Total faculties found: {}", faculties.size());

        return faculties.stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> {
                    logger.error("No faculties found in database");
                    return new IllegalStateException("No faculties found");
                });
    }

}

