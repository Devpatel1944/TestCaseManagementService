package com.example.testcase.service;



import com.example.testcase.dto.TestCaseDTO;
import com.example.testcase.exception.ResourceNotFoundException;
import com.example.testcase.model.TestCase;
import com.example.testcase.model.Priority;
import com.example.testcase.model.Status;
import com.example.testcase.repository.TestCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCaseServiceTest {

    @Mock
    private TestCaseRepository repository;

    @InjectMocks
    private TestCaseServiceImpl service;

    private TestCase testCase;
    private TestCaseDTO testCaseDTO;

    @BeforeEach
    void setup() {
        testCase = new TestCase("1", "Login Test", "Valid login scenario", Status.PENDING, Priority.HIGH, null, null);
        testCaseDTO = new TestCaseDTO();
        testCaseDTO.setTitle("Updated Test");
        testCaseDTO.setDescription("Updated Description");
        testCaseDTO.setStatus(Status.PASSED);
        testCaseDTO.setPriority(Priority.MEDIUM);
    }

    @Test
    void shouldReturnAllTestCases() {
        when(repository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(Arrays.asList(testCase)));

        List<TestCase> testCases = service.getAllTestCases(0, 10, null, null);

        assertFalse(testCases.isEmpty());
        assertEquals(1, testCases.size());
    }

    @Test
    void shouldReturnTestCaseById() {
        when(repository.findById("1")).thenReturn(Optional.of(testCase));

        Optional<TestCase> result = service.getTestCaseById("1");

        assertTrue(result.isPresent());
        assertEquals("Login Test", result.get().getTitle());
    }

    @Test
    void shouldThrowExceptionWhenTestCaseNotFound() {
        when(repository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getTestCaseById("999").orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @Test
    void shouldCreateTestCase() {
        when(repository.save(any(TestCase.class))).thenReturn(testCase);

        TestCase createdTestCase = service.createTestCase(testCaseDTO);

        assertNotNull(createdTestCase);
        assertEquals(testCase.getTitle(), createdTestCase.getTitle());
    }

    @Test
    void shouldUpdateTestCase() {
        when(repository.findById("1")).thenReturn(Optional.of(testCase));
        when(repository.save(any(TestCase.class))).thenReturn(testCase);

        TestCase updatedTestCase = service.updateTestCase("1", testCaseDTO);

        assertEquals("Updated Test", updatedTestCase.getTitle());
        assertEquals(Status.PASSED, updatedTestCase.getStatus());
    }

    @Test
    void shouldDeleteTestCase() {
        // Mock existsById() to return true (so the test case is found)
        when(repository.existsById("1")).thenReturn(true);

        // Mock deleteById() to do nothing
        doNothing().when(repository).deleteById("1");

        // Call the delete method
        service.deleteTestCase("1");

        // Verify that the delete method was actually called
        verify(repository, times(1)).deleteById("1");
    }

}
