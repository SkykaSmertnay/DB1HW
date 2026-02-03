package org.skypro.demo.service;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty update(Long id, Faculty updatedFaculty) {
        Faculty existing = getById(id);
        existing.setName(updatedFaculty.getName());
        existing.setColor(updatedFaculty.getColor());
        return facultyRepository.save(existing);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
    public List<Faculty> findByNameOrColor(String value) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(value, value);
    }
    public List<Student> getFacultyStudents(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        return faculty.getStudents();
    }
}
