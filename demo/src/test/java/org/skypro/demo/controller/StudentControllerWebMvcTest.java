package org.skypro.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateStudent() throws Exception {
        Student input = new Student();
        input.setName("Harry");
        input.setAge(11);

        Student returned = new Student();
        returned.setName("Harry");
        returned.setAge(11);

        when(studentService.create(any(Student.class))).thenReturn(returned);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    void shouldGetStudentById() throws Exception {
        Student returned = new Student();
        returned.setName("Harry");
        returned.setAge(11);

        when(studentService.getById(1L)).thenReturn(returned);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    void shouldGetAllStudents() throws Exception {
        Student s1 = new Student();
        s1.setName("Harry");
        s1.setAge(11);

        when(studentService.getAll()).thenReturn(List.of(s1));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry"))
                .andExpect(jsonPath("$[0].age").value(11));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        Student input = new Student();
        input.setName("Harry Updated");
        input.setAge(12);

        Student returned = new Student();
        returned.setName("Harry Updated");
        returned.setAge(12);

        when(studentService.update(eq(1L), any(Student.class))).thenReturn(returned);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry Updated"))
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        doNothing().when(studentService).delete(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetStudentsByAgeBetween() throws Exception {
        Student s1 = new Student();
        s1.setName("Harry");
        s1.setAge(11);

        when(studentService.getStudentsByAgeBetween(10, 20)).thenReturn(List.of(s1));

        mockMvc.perform(get("/students/age")
                        .param("min", "10")
                        .param("max", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry"))
                .andExpect(jsonPath("$[0].age").value(11));
    }

    @Test
    void shouldGetStudentFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        when(studentService.getStudentFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/students/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }
}


