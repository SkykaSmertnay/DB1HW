package org.skypro.demo.repository;

import org.skypro.demo.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
