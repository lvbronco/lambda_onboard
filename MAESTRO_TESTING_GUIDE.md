# Maestro Framework Testing Guide

## Overview
Maestro is a modern mobile app testing framework that provides a simple YAML-based approach to writing tests. This guide covers how to run Maestro tests in the LambdaTest demo project.

## Prerequisites

### 1. Maestro CLI Installation
Maestro CLI is automatically installed when you run the test, but you can also install it manually:

```bash
curl -Ls "https://get.maestro.mobile.dev" | bash
export PATH="$PATH":"$HOME/.maestro/bin"
```

### 2. Device Requirements
- **Local Testing**: Requires a connected Android device or emulator
- **Cloud Testing**: Uses LambdaTest cloud devices (requires credentials)

## Test Files

### 1. `maestro_test.yaml` - Original Test
- Basic Maestro test configuration
- Single flow with comprehensive app testing

### 2. `maestro_local_test.yaml` - Local Device Test
- Optimized for local device/emulator testing
- Includes `launchApp` command
- Requires connected device

### 3. `maestro_cloud_test.yaml` - Cloud Device Test
- Optimized for LambdaTest cloud devices
- Includes `launchApp` command
- Uses cloud infrastructure

## Running Maestro Tests

### Basic Maestro Test
```bash
./run_tests.sh maestro
```

### Local Device Test
```bash
./run_tests.sh maestro-local
```

### Cloud Device Test
```bash
./run_tests.sh maestro-cloud
```

## Test Configuration

### YAML Structure
```yaml
# Configuration section
appId: proverbial-android
testTimeout: 30000
clearState: true
---
# Commands section
- launchApp
- tapOn: "Color"
- assertVisible: "Color"
```

### Available Commands
- `launchApp` - Launch the application
- `tapOn: "element"` - Tap on an element
- `assertVisible: "element"` - Assert element is visible
- `inputText: "text"` - Input text into a field
- `back` - Navigate back
- `assertNotVisible: "element"` - Assert element is not visible

## Test Scenarios

### 1. Basic App Flow Test
Tests the complete app functionality:
- Color change functionality
- Text input and display
- Toast notifications
- System notifications
- Geolocation features
- Speed test functionality
- Browser functionality

### 2. Individual Feature Tests
Each test can be run independently:
- Color change test
- Text input test
- Notification test
- Geolocation test
- Speed test
- Browser test

## Troubleshooting

### Common Issues

#### 1. "Not enough devices connected"
**Error**: `Want to use 0 devices, which is not enough to run 1 shards`
**Solution**: 
- Connect an Android device via USB
- Start an Android emulator
- Use cloud testing option

#### 2. "Maestro CLI not found"
**Error**: `maestro: command not found`
**Solution**:
```bash
export PATH="$PATH":"$HOME/.maestro/bin"
# Or reinstall
curl -Ls "https://get.maestro.mobile.dev" | bash
```

#### 3. "App not found"
**Error**: App installation fails
**Solution**:
- Ensure the app is installed on the device
- Check app ID in YAML file
- Verify device/emulator is running

### Debug Commands

#### Check Maestro Installation
```bash
maestro --version
```

#### List Connected Devices
```bash
maestro devices
```

#### Run with Verbose Output
```bash
maestro test maestro_local_test.yaml --verbose
```

#### Run Specific Flow
```bash
maestro test maestro_local_test.yaml --flow "Basic App Flow Test"
```

## Integration with LambdaTest

### Cloud Device Testing
Maestro can be integrated with LambdaTest cloud devices:

1. **Set up LambdaTest credentials**:
   ```bash
   export LT_USERNAME=your_username
   export LT_ACCESS_KEY=your_access_key
   ```

2. **Configure cloud device**:
   - Use LambdaTest device capabilities
   - Set up app installation
   - Configure test execution

### Parallel Testing
Maestro supports parallel test execution:
```bash
maestro test maestro_local_test.yaml --parallel
```

## Best Practices

### 1. Test Organization
- Keep tests focused and atomic
- Use descriptive test names
- Group related tests together

### 2. Element Selection
- Use accessibility IDs when possible
- Prefer stable selectors
- Avoid fragile XPath expressions

### 3. Test Data
- Use test data files for complex inputs
- Parameterize tests when possible
- Clean up test data after execution

### 4. Error Handling
- Add appropriate assertions
- Handle flaky elements with retries
- Use timeouts for long operations

## Advanced Features

### 1. Test Data Files
```yaml
# test-data.yaml
testUrl: "https://www.lambdatest.com"
testText: "Hello Maestro"
```

### 2. Custom Commands
```yaml
- runScript: |
    // Custom JavaScript execution
    console.log("Custom test logic");
```

### 3. Screenshot Capture
```yaml
- takeScreenshot: "test-screenshot.png"
```

### 4. Performance Testing
```yaml
- assertPerformance: 
    metric: "loadTime"
    maxValue: 5000
```

## Monitoring and Reporting

### 1. Test Results
- Maestro provides detailed test execution logs
- Screenshots are captured on failures
- Performance metrics are recorded

### 2. Integration with CI/CD
```yaml
# GitHub Actions example
- name: Run Maestro Tests
  run: |
    ./run_tests.sh maestro-local
```

### 3. Reporting
- Test results are displayed in console
- Screenshots saved to local directory
- Performance metrics exported

## Support and Resources

- **Maestro Documentation**: https://maestro.mobile.dev/
- **LambdaTest Documentation**: https://www.lambdatest.com/support/docs/
- **Community Support**: https://github.com/mobile-dev-inc/maestro

## Example Test Execution

```bash
# Install Maestro (if not already installed)
curl -Ls "https://get.maestro.mobile.dev" | bash
export PATH="$PATH":"$HOME/.maestro/bin"

# Run local test
./run_tests.sh maestro-local

# Run cloud test
./run_tests.sh maestro-cloud

# Check test results
ls -la maestro-results/
```
