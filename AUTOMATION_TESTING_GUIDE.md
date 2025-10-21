# Mobile App Automation Testing Guide

This guide provides comprehensive instructions for running various mobile app automation tests using the LambdaTest platform.

## Prerequisites

1. **LambdaTest Credentials**: Set your environment variables
   ```bash
   export LT_USERNAME=matthewpaul
   export LT_ACCESS_KEY=LT_ODGmhOPe2LqV8PuZXMKsY1IKOGyI1VZpkOQIkUl3aBTlLfb
   ```

2. **Maven**: Ensure Maven is installed and configured
3. **Java**: Java 8 or higher required

## Test Execution Commands

### 1. Execute 5 test cases on 5 Android devices parallelly via Appium Java

```bash
# Using the test runner script
./run_tests.sh android-5-devices

# Or directly with Maven
mvn test -D suite=android_5_devices_parallel.xml
```

**Devices tested:**
- Galaxy S22 5G (Android 12)
- Pixel 6 (Android 12)
- OnePlus 9 (Android 11)
- Galaxy Note20 Ultra 5G (Android 11)
- Xiaomi Mi 11 (Android 11)

### 2. Execute 5 test cases on 5 iOS devices parallelly via Appium 2.0

```bash
# Using the test runner script
./run_tests.sh ios-5-devices

# Or directly with Maven
mvn test -D suite=ios_5_devices_parallel.xml
```

**Devices tested:**
- iPhone 14 (iOS 16)
- iPhone 13 (iOS 15)
- iPhone 12 (iOS 14)
- iPhone 11 (iOS 14)
- iPhone SE (3rd generation) (iOS 15)

### 3. Execute 5 test cases on 5 Android emulators parallelly

```bash
# Using the test runner script
./run_tests.sh android-5-emulators

# Or directly with Maven
mvn test -D suite=android_5_emulators_parallel.xml
```

**Emulators tested:**
- Pixel 6 (Android 12)
- Pixel 5 (Android 11)
- Galaxy S21 (Android 11)
- Pixel 4 (Android 10)
- Galaxy S20 (Android 10)

### 4. Execute 5 test cases on 5 iOS simulators parallelly

```bash
# Using the test runner script
./run_tests.sh ios-5-simulators

# Or directly with Maven
mvn test -D suite=ios_5_simulators_parallel.xml
```

**Simulators tested:**
- iPhone 14 (iOS 16)
- iPhone 13 (iOS 15)
- iPhone 12 (iOS 14)
- iPhone 11 (iOS 14)
- iPhone SE (3rd generation) (iOS 15)

### 5. Run XCUI Test Suite on App Automation

```bash
# Using the test runner script
./run_tests.sh xcui-test

# Or directly with Maven
mvn test -D suite=xcui_test_suite.xml
```

**Features:**
- Uses XCUITest framework for iOS testing
- Tests on multiple iPhone models
- Optimized for iOS-specific interactions

### 6. Run XCUI Test with Sharding

```bash
# Using the test runner script
./run_tests.sh xcui-sharding

# Or directly with Maven
mvn test -D suite=xcui_sharding.xml
```

**Features:**
- Distributes tests across 5 shards
- Parallel execution for faster test completion
- Each shard runs on different devices

### 7. Run Espresso Test on App Automation

```bash
# Using the test runner script
./run_tests.sh espresso-test

# Or directly with Maven
mvn test -D suite=espresso_test.xml
```

**Features:**
- Uses Espresso framework for Android testing
- Tests on multiple Android devices
- Optimized for Android-specific interactions

### 8. Run Espresso with Sharding and generate JUnit report

```bash
# Using the test runner script
./run_tests.sh espresso-sharding

# Or directly with Maven
mvn test -D suite=espresso_sharding_junit.xml
```

**Features:**
- Distributes tests across 5 shards
- Generates JUnit XML reports
- Parallel execution for faster completion

### 9. Run App Automation Test with Maestro Framework

```bash
# Install Maestro CLI first
curl -Ls "https://get.maestro.mobile.dev" | bash

# Run Maestro tests
maestro test maestro_test.yaml

# Or using the test runner script
./run_tests.sh maestro
```

**Features:**
- Uses Maestro framework for cross-platform testing
- YAML-based test configuration
- Supports both Android and iOS

## Test Configuration Files

### XML Test Suites
- `android_5_devices_parallel.xml` - 5 Android devices parallel
- `ios_5_devices_parallel.xml` - 5 iOS devices parallel
- `android_5_emulators_parallel.xml` - 5 Android emulators parallel
- `ios_5_simulators_parallel.xml` - 5 iOS simulators parallel
- `xcui_test_suite.xml` - XCUI Test suite
- `xcui_sharding.xml` - XCUI Test with sharding
- `espresso_test.xml` - Espresso Test suite
- `espresso_sharding_junit.xml` - Espresso with sharding and JUnit

### Java Test Classes
- `AndroidApp.java` - Base Android test class
- `IOSApp.java` - Base iOS test class
- `XCUITestApp.java` - XCUI Test implementation
- `EspressoTestApp.java` - Espresso Test implementation

### Maestro Configuration
- `maestro_test.yaml` - Maestro test configuration

## Test Features

### Parallel Execution
All test suites are configured for parallel execution to reduce total test time:
- Thread count: 5 (for 5-device tests)
- Parallel mode: tests
- Concurrent device execution

### Device Coverage
- **Android**: Latest flagship devices from Samsung, Google, OnePlus, Xiaomi
- **iOS**: Latest iPhone models with different iOS versions
- **Emulators/Simulators**: For cost-effective testing

### Framework Support
- **Appium Java**: Primary framework for cross-platform testing
- **XCUITest**: iOS-specific testing framework
- **Espresso**: Android-specific testing framework
- **Maestro**: Modern cross-platform testing framework

### Reporting
- **LambdaTest Dashboard**: Real-time test execution and results
- **JUnit Reports**: For Espresso tests with sharding
- **TestNG Reports**: For all Maven-based tests

## Monitoring and Results

1. **LambdaTest Dashboard**: View real-time test execution
   - URL: https://automation.lambdatest.com/logs/
   - Real-time video recording
   - Device logs and network logs
   - Screenshots and video recordings

2. **Test Reports**: Generated in `target/surefire-reports/`
   - Individual test results
   - Failure screenshots
   - Execution logs

## Troubleshooting

### Common Issues
1. **Authentication Errors**: Verify LT_USERNAME and LT_ACCESS_KEY
2. **App Upload Failures**: Check app file paths in `apps/` directory
3. **Device Availability**: Some devices may not be available; tests will queue
4. **Network Issues**: Ensure stable internet connection

### Debug Commands
```bash
# Run with verbose logging
mvn test -D suite=android_5_devices_parallel.xml -X

# Run specific test class
mvn test -D test=AndroidApp

# Check test configuration
mvn test -D suite=android_5_devices_parallel.xml -D dryRun=true
```

## Best Practices

1. **Test Data Management**: Use parameterized tests for different devices
2. **Error Handling**: Implement proper exception handling in test classes
3. **Resource Management**: Always quit drivers in @AfterMethod
4. **Parallel Execution**: Use appropriate thread counts for your LambdaTest plan
5. **App Management**: Upload apps once and reuse across tests

## Cost Optimization

1. **Use Emulators/Simulators**: Cheaper than real devices
2. **Parallel Execution**: Reduces total execution time
3. **Test Sharding**: Distribute tests efficiently
4. **Selective Testing**: Run only necessary test suites

## Support

- **LambdaTest Documentation**: https://www.lambdatest.com/support/docs/
- **Appium Documentation**: http://appium.io/docs/en/about-appium/intro/
- **TestNG Documentation**: https://testng.org/doc/
- **Maestro Documentation**: https://maestro.mobile.dev/
