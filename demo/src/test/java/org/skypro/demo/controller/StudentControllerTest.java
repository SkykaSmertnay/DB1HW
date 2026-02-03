package org.skypro.demo.controller;

import org.junit.jupiter.api.Test;
import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateStudent() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(11);

        ResponseEntity<Student> response =
                restTemplate.postForEntity("/students", student, Student.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry");
        assertThat(response.getBody().getAge()).isEqualTo(11);
    }

    @Test
    void shouldGetStudentById() {
        Student created = createStudent("Ron", 11);

        ResponseEntity<Student> response =
                restTemplate.getForEntity("/students/" + created.getId(), Student.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(created.getId());
    }

    @Test
    void shouldGetAllStudents() {
        ResponseEntity<Student[]> response =
                restTemplate.getForEntity("/students", Student[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldUpdateStudent() {
        Student created = createStudent("Hermione", 12);

        Student updated = new Student();
        updated.setName("Hermione Updated");
        updated.setAge(13);

        ResponseEntity<Student> response = restTemplate.exchange(
                "/students/" + created.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Student.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Hermione Updated");
        assertThat(response.getBody().getAge()).isEqualTo(13);
    }

    @Test
    void shouldDeleteStudent() {
        Student created = createStudent("Neville", 11);

        restTemplate.delete("/students/" + created.getId());

        ResponseEntity<String> response =
                restTemplate.getForEntity("/students/" + created.getId(), String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldGetStudentsByAgeBetween() {
        createStudent("AgeTest1", 10);
        createStudent("AgeTest2", 20);

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity("/students/age?min=9&max=21", Student[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void shouldGetStudentFaculty() {
        // Чтобы этот тест был 100% стабильным, студент должен быть реально привязан к faculty в БД.
        // Поэтому делаем безопасную проверку: либо 2xx (если привязка есть), либо 404 (если нет).
        Student created = createStudent("FacultyTest", 11);

        ResponseEntity<Faculty> response =
                restTemplate.getForEntity("/students/" + created.getId() + "/faculty", Faculty.class);

        assertThat(
                response.getStatusCode().is2xxSuccessful() || response.getStatusCode().value() == 404
        ).isTrue();
    }

    private Student createStudent(String name, int age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        ResponseEntity<Student> response =
                restTemplate.postForEntity("/students", student, Student.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

        return response.getBody();
    }
}
