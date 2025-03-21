package com.example.testcase.controller;


import com.example.testcase.dto.TestCaseDTO;
import com.example.testcase.model.TestCase;
import com.example.testcase.service.TestCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcases")
@Tag(name = "Test Case API", description = "Operations for test case management")
public class TestCaseController {

    @Autowired
    private TestCaseService service;

    @Operation(summary = "Get all test cases", description = "Get a paginated list of test cases with optional filtering")
    @GetMapping
    public List<TestCase> getAllTestCases(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) String priority) {
        return service.getAllTestCases(page, size, status, priority);
    }

    @Operation(summary = "Get test case by ID", description = "Get a specific test case by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test case found"),
        @ApiResponse(responseCode = "404", description = "Test case not found", content = @Content)
    })
    @GetMapping("/{id}")
    public TestCase getTestCaseById(@PathVariable String id) {
        return service.getTestCaseById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Operation(summary = "Create a test case", description = "Create a new test case")
    @PostMapping
    public TestCase createTestCase(@RequestBody TestCaseDTO testCaseDTO) {
        return service.createTestCase(testCaseDTO);
    }

    @Operation(summary = "Update a test case", description = "Update an existing test case by its ID")
    @PutMapping("/{id}")
    public TestCase updateTestCase(@PathVariable String id, @RequestBody TestCaseDTO testCaseDTO) {
        return service.updateTestCase(id, testCaseDTO);
    }

    @Operation(summary = "Delete a test case", description = "Delete a test case by its ID")
    @DeleteMapping("/{id}")
    public void deleteTestCase(@PathVariable String id) {
        service.deleteTestCase(id);
    }
}
