package org.skypro.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skypro.demo.model.Faculty;
import org.skypro.demo.model.Student;
import org.skypro.demo.service.FacultyService;
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

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateFaculty() throws Exception {
        Faculty input = new Faculty();
        input.setName("Gryffindor");
        input.setColor("Red");

        Faculty returned = new Faculty();
        returned.setName("Gryffindor");
        returned.setColor("Red");

        when(facultyService.create(any(Faculty.class))).thenReturn(returned);

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    void shouldGetFacultyById() throws Exception {
        Faculty returned = new Faculty();
        returned.setName("Slytherin");
        returned.setColor("Green");

        when(facultyService.getById(1L)).thenReturn(returned);

        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Slytherin"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    void shouldGetAllFaculties() throws Exception {
        Faculty f1 = new Faculty();
        f1.setName("Ravenclaw");
        f1.setColor("Blue");

        when(facultyService.getAll()).thenReturn(List.of(f1));

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ravenclaw"))
                .andExpect(jsonPath("$[0].color").value("Blue"));
    }

    @Test
    void shouldUpdateFaculty() throws Exception {
        Faculty input = new Faculty();
        input.setName("Hufflepuff Updated");
        input.setColor("Yellow");

        Faculty returned = new Faculty();
        returned.setName("Hufflepuff Updated");
        returned.setColor("Yellow");

        when(facultyService.update(eq(1L), any(Faculty.class))).thenReturn(returned);

        mockMvc.perform(put("/faculties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hufflepuff Updated"))
                .andExpect(jsonPath("$.color").value("Yellow"));
    }

    @Test
    void shouldDeleteFaculty() throws Exception {
        doNothing().when(facultyService).delete(1L);

        mockMvc.perform(delete("/faculties/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSearchFacultiesByNameOrColor() throws Exception {
        Faculty f1 = new Faculty();
        f1.setName("Gryffindor");
        f1.setColor("Red");

        when(facultyService.findByNameOrColor("red")).thenReturn(List.of(f1));

        mockMvc.perform(get("/faculties/search")
                        .param("query", "red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    void shouldGetFacultyStudents() throws Exception {
        Student s1 = new Student();
        s1.setName("Harry");
        s1.setAge(11);

        when(facultyService.getFacultyStudents(1L)).thenReturn(List.of(s1));

        mockMvc.perform(get("/faculties/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry"))
                .andExpect(jsonPath("$[0].age").value(11));
    }
}

