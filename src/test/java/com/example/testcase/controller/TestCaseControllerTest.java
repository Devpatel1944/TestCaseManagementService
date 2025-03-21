package com.example.testcase.controller;



import com.example.testcase.dto.TestCaseDTO;
import com.example.testcase.model.TestCase;
import com.example.testcase.model.Priority;
import com.example.testcase.model.Status;
import com.example.testcase.service.TestCaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TestCaseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TestCaseService service;

    @InjectMocks
    private TestCaseController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnAllTestCases() throws Exception {
        TestCase testCase = new TestCase("1", "Login Test", "Valid login scenario", Status.PENDING, Priority.HIGH, null, null);
        when(service.getAllTestCases(0, 10, null, null)).thenReturn(Arrays.asList(testCase));

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/api/testcases")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Login Test"));
    }

    @Test
    void shouldReturnTestCaseById() throws Exception {
        TestCase testCase = new TestCase("1", "Login Test", "Valid login scenario", Status.PENDING, Priority.HIGH, null, null);
        when(service.getTestCaseById("1")).thenReturn(Optional.of(testCase));

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/api/testcases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Login Test"));
    }

    @Test
    void shouldCreateTestCase() throws Exception {
        TestCaseDTO testCaseDTO = new TestCaseDTO();
        testCaseDTO.setTitle("New Test");
        testCaseDTO.setDescription("Description");
        testCaseDTO.setStatus(Status.IN_PROGRESS);
        testCaseDTO.setPriority(Priority.LOW);

        TestCase savedTestCase = new TestCase("1", "New Test", "Description", Status.IN_PROGRESS, Priority.LOW, null, null);
        when(service.createTestCase(any(TestCaseDTO.class))).thenReturn(savedTestCase);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/api/testcases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCaseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Test"));
    }

    @Test
    void shouldUpdateTestCase() throws Exception {
        TestCaseDTO testCaseDTO = new TestCaseDTO();
        testCaseDTO.setTitle("Updated Test");
        testCaseDTO.setDescription("Updated Description");
        testCaseDTO.setStatus(Status.PASSED);
        testCaseDTO.setPriority(Priority.HIGH);

        TestCase updatedTestCase = new TestCase("1", "Updated Test", "Updated Description", Status.PASSED, Priority.HIGH, null, null);
        when(service.updateTestCase(eq("1"), any(TestCaseDTO.class))).thenReturn(updatedTestCase);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(put("/api/testcases/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCaseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test"));
    }

    @Test
    void shouldDeleteTestCase() throws Exception {
        doNothing().when(service).deleteTestCase("1");

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(delete("/api/testcases/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteTestCase("1");
    }
}
