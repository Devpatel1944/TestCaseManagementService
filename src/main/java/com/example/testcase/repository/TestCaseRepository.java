package com.example.testcase.repository;



import com.example.testcase.model.Priority;
import com.example.testcase.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.testcase.model.TestCase;
import java.util.List;

public interface TestCaseRepository extends MongoRepository<TestCase, String> {
    List<TestCase> findByStatusAndPriority(Status status, Priority priority);
}
