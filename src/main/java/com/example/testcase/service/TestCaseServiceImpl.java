package com.example.testcase.service;

import com.example.testcase.dto.TestCaseDTO;
import com.example.testcase.exception.ResourceNotFoundException;
import com.example.testcase.model.TestCase;
import com.example.testcase.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository repository;

    @Override
    public List<TestCase> getAllTestCases(int page, int size, String status, String priority) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public Optional<TestCase> getTestCaseById(String id) {
        return repository.findById(id);
    }

    @Override
    public TestCase createTestCase(TestCaseDTO testCaseDTO) {
        // ✅ Manually creating an object since builder() is not available
        TestCase testCase = new TestCase();
        testCase.setTitle(testCaseDTO.getTitle());
        testCase.setDescription(testCaseDTO.getDescription());
        testCase.setStatus(testCaseDTO.getStatus());
        testCase.setPriority(testCaseDTO.getPriority());
        testCase.setCreatedAt(new Date());
        testCase.setUpdatedAt(new Date());

        return repository.save(testCase);
    }

    @Override
    public TestCase updateTestCase(String id, TestCaseDTO testCaseDTO) {
        TestCase testCase = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found"));

        testCase.setTitle(testCaseDTO.getTitle());
        testCase.setDescription(testCaseDTO.getDescription());
        testCase.setStatus(testCaseDTO.getStatus());
        testCase.setPriority(testCaseDTO.getPriority());
        testCase.setUpdatedAt(new Date());

        return repository.save(testCase);
    }

    @Override
    public void deleteTestCase(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("TestCase not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
