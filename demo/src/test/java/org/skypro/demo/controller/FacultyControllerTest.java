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
class FacultyControllerTest {

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void shouldCreateFaculty() {
            Faculty faculty = new Faculty();
            faculty.setName("Gryffindor");
            faculty.setColor("Red");

            ResponseEntity<Faculty> response =
                    restTemplate.postForEntity("/faculties", faculty, Faculty.class);

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isNotNull();
        }
        @Test
        void shouldGetFacultyById() {
            Faculty created = createFaculty("Slytherin", "Green");

            ResponseEntity<Faculty> response =
                    restTemplate.getForEntity("/faculties/" + created.getId(), Faculty.class);

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isEqualTo(created.getId());
        }

        @Test
        void shouldGetAllFaculties() {
            ResponseEntity<Faculty[]> response =
                    restTemplate.getForEntity("/faculties", Faculty[].class);

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
        }

        @Test
        void shouldUpdateFaculty() {
            Faculty created = createFaculty("Ravenclaw", "Blue");

            Faculty updated = new Faculty();
            updated.setName("Ravenclaw Updated");
            updated.setColor("Dark Blue");

            ResponseEntity<Faculty> response = restTemplate.exchange(
                    "/faculties/" + created.getId(),
                    HttpMethod.PUT,
                    new HttpEntity<>(updated),
                    Faculty.class
            );

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getName()).isEqualTo("Ravenclaw Updated");
            assertThat(response.getBody().getColor()).isEqualTo("Dark Blue");

        }

        @Test
        void shouldDeleteFaculty() {
            Faculty created = createFaculty("Hufflepuff", "Yellow");

            restTemplate.delete("/faculties/" + created.getId());

            ResponseEntity<String> response =
                    restTemplate.getForEntity("/faculties/" + created.getId(), String.class);

            assertThat(response.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        void shouldSearchFacultyByNameOrColor() {
            createFaculty("Gryffindor", "Red");

            ResponseEntity<Faculty[]> response =
                    restTemplate.getForEntity("/faculties/search?query=red", Faculty[].class);

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().length).isGreaterThan(0);
        }

        @Test
        void shouldGetFacultyStudents() {
            Faculty created = createFaculty("TestFaculty", "Black");

            ResponseEntity<Student[]> response =
                    restTemplate.getForEntity(
                            "/faculties/" + created.getId() + "/students",
                            Student[].class
                    );

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
        }

        private Faculty createFaculty(String name, String color) {
            Faculty faculty = new Faculty();
            faculty.setName(name);
            faculty.setColor(color);

            ResponseEntity<Faculty> response =
                    restTemplate.postForEntity("/faculties", faculty, Faculty.class);

            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isNotNull();

            return response.getBody();
        }

}
