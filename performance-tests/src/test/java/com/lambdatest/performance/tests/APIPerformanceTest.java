package com.lambdatest.performance.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class APIPerformanceTest {
    
    private String baseURL;
    private RequestSpecification requestSpec;
    
    @BeforeClass
    public void setUp() {
        baseURL = System.getProperty("api.base.url", "https://jsonplaceholder.typicode.com");
        RestAssured.baseURI = baseURL;
        
        requestSpec = given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json");
    }
    
    @Test(description = "API Load Test - 100 concurrent requests")
    @Description("Test API performance with 100 concurrent requests")
    @Severity(SeverityLevel.CRITICAL)
    public void testAPILoadPerformance() throws Exception {
        int numberOfThreads = 100;
        int requestsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Long>> futures = new ArrayList<>();
        
        System.out.println("Starting load test with " + numberOfThreads + " threads, " + requestsPerThread + " requests each");
        
        for (int i = 0; i < numberOfThreads; i++) {
            Future<Long> future = executor.submit(() -> {
                long totalResponseTime = 0;
                for (int j = 0; j < requestsPerThread; j++) {
                    long startTime = System.currentTimeMillis();
                    try {
                        Response response = requestSpec
                            .when()
                                .get("/users")
                            .then()
                                .statusCode(200)
                                .extract().response();
                        long endTime = System.currentTimeMillis();
                        totalResponseTime += (endTime - startTime);
                    } catch (Exception e) {
                        System.err.println("Request failed: " + e.getMessage());
                    }
                }
                return totalResponseTime;
            });
            futures.add(future);
        }
        
        // Wait for all requests to complete
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
        
        // Calculate performance metrics
        long totalResponseTime = 0;
        int successfulRequests = 0;
        int failedRequests = 0;
        
        for (Future<Long> future : futures) {
            try {
                totalResponseTime += future.get();
                successfulRequests += requestsPerThread;
            } catch (Exception e) {
                failedRequests += requestsPerThread;
            }
        }
        
        double averageResponseTime = (double) totalResponseTime / successfulRequests;
        double requestsPerSecond = (double) successfulRequests / (totalResponseTime / 1000.0);
        
        System.out.println("Load Test Results:");
        System.out.println("Total Requests: " + (successfulRequests + failedRequests));
        System.out.println("Successful Requests: " + successfulRequests);
        System.out.println("Failed Requests: " + failedRequests);
        System.out.println("Average Response Time: " + averageResponseTime + "ms");
        System.out.println("Requests Per Second: " + requestsPerSecond);
        
        // Performance assertions
        Assert.assertTrue(averageResponseTime < 1000, "Average response time should be less than 1000ms");
        Assert.assertTrue(requestsPerSecond > 50, "Should handle more than 50 requests per second");
        Assert.assertTrue(failedRequests < successfulRequests * 0.05, "Failure rate should be less than 5%");
    }
    
    @Test(description = "API Stress Test - High Load")
    @Description("Test API performance under high stress conditions")
    @Severity(SeverityLevel.CRITICAL)
    public void testAPIStressPerformance() throws Exception {
        int numberOfThreads = 200;
        int requestsPerThread = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Boolean>> futures = new ArrayList<>();
        
        System.out.println("Starting stress test with " + numberOfThreads + " threads");
        
        for (int i = 0; i < numberOfThreads; i++) {
            Future<Boolean> future = executor.submit(() -> {
                boolean allSuccessful = true;
                for (int j = 0; j < requestsPerThread; j++) {
                    try {
                        long startTime = System.currentTimeMillis();
                        Response response = requestSpec
                            .when()
                                .get("/posts")
                            .then()
                                .statusCode(200)
                                .extract().response();
                        long endTime = System.currentTimeMillis();
                        
                        if (endTime - startTime > 5000) { // 5 second timeout
                            allSuccessful = false;
                        }
                    } catch (Exception e) {
                        allSuccessful = false;
                    }
                }
                return allSuccessful;
            });
            futures.add(future);
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        
        int successfulThreads = 0;
        for (Future<Boolean> future : futures) {
            try {
                if (future.get()) {
                    successfulThreads++;
                }
            } catch (Exception e) {
                // Thread failed
            }
        }
        
        double successRate = (double) successfulThreads / numberOfThreads;
        System.out.println("Stress Test Results:");
        System.out.println("Successful Threads: " + successfulThreads + "/" + numberOfThreads);
        System.out.println("Success Rate: " + (successRate * 100) + "%");
        
        Assert.assertTrue(successRate > 0.8, "Success rate should be greater than 80%");
    }
    
    @Test(description = "API Endurance Test - Long Duration")
    @Description("Test API performance over extended period")
    @Severity(SeverityLevel.HIGH)
    public void testAPIEndurancePerformance() throws Exception {
        int durationMinutes = 5; // 5 minutes endurance test
        int requestsPerSecond = 10;
        ExecutorService executor = Executors.newFixedThreadPool(20);
        List<Long> responseTimes = new ArrayList<>();
        List<Boolean> successResults = new ArrayList<>();
        
        System.out.println("Starting " + durationMinutes + " minute endurance test");
        
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationMinutes * 60 * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            for (int i = 0; i < requestsPerSecond; i++) {
                executor.submit(() -> {
                    try {
                        long requestStart = System.currentTimeMillis();
                        Response response = requestSpec
                            .when()
                                .get("/users/1")
                            .then()
                                .statusCode(200)
                                .extract().response();
                        long requestEnd = System.currentTimeMillis();
                        
                        synchronized (responseTimes) {
                            responseTimes.add(requestEnd - requestStart);
                        }
                        synchronized (successResults) {
                            successResults.add(true);
                        }
                    } catch (Exception e) {
                        synchronized (successResults) {
                            successResults.add(false);
                        }
                    }
                });
            }
            Thread.sleep(1000); // Wait 1 second
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        
        // Calculate metrics
        double averageResponseTime = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        long successfulRequests = successResults.stream().mapToLong(b -> b ? 1 : 0).sum();
        double successRate = (double) successfulRequests / successResults.size();
        
        System.out.println("Endurance Test Results:");
        System.out.println("Total Requests: " + successResults.size());
        System.out.println("Successful Requests: " + successfulRequests);
        System.out.println("Success Rate: " + (successRate * 100) + "%");
        System.out.println("Average Response Time: " + averageResponseTime + "ms");
        
        Assert.assertTrue(successRate > 0.95, "Success rate should be greater than 95%");
        Assert.assertTrue(averageResponseTime < 2000, "Average response time should be less than 2 seconds");
    }
    
    @Test(description = "API Memory Usage Test")
    @Description("Test API performance under memory constraints")
    @Severity(SeverityLevel.MEDIUM)
    public void testAPIMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.println("Initial Memory Usage: " + (initialMemory / 1024 / 1024) + " MB");
        
        // Perform memory-intensive operations
        for (int i = 0; i < 1000; i++) {
            try {
                Response response = requestSpec
                    .when()
                        .get("/posts")
                    .then()
                        .statusCode(200)
                        .extract().response();
                
                // Process large response
                String responseBody = response.getBody().asString();
                if (responseBody.length() > 10000) {
                    // Simulate processing large data
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                System.err.println("Request failed: " + e.getMessage());
            }
        }
        
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = finalMemory - initialMemory;
        
        System.out.println("Final Memory Usage: " + (finalMemory / 1024 / 1024) + " MB");
        System.out.println("Memory Used: " + (memoryUsed / 1024 / 1024) + " MB");
        
        // Force garbage collection
        System.gc();
        long afterGCMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryAfterGC = afterGCMemory - initialMemory;
        
        System.out.println("Memory After GC: " + (memoryAfterGC / 1024 / 1024) + " MB");
        
        Assert.assertTrue(memoryAfterGC < 100 * 1024 * 1024, "Memory usage should be less than 100MB after GC");
    }
}
