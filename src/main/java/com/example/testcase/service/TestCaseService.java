package com.example.testcase.service;



import com.example.testcase.dto.TestCaseDTO;
import com.example.testcase.model.TestCase;
import java.util.List;
import java.util.Optional;

public interface TestCaseService {
    List<TestCase> getAllTestCases(int page, int size, String status, String priority);
    Optional<TestCase> getTestCaseById(String id);
    TestCase createTestCase(TestCaseDTO testCaseDTO);
    TestCase updateTestCase(String id, TestCaseDTO testCaseDTO);
    void deleteTestCase(String id);
}
