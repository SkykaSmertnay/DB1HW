package org.skypro.demo.service;

import org.skypro.demo.model.Faculty;
import org.skypro.demo.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
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
}
