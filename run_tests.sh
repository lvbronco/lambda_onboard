#!/bin/bash

# LambdaTest Demo Test Runner
# Set your credentials here
export LT_USERNAME=matthewpaul
export LT_ACCESS_KEY=LT_ODGmhOPe2LqV8PuZXMKsY1IKOGyI1VZpkOQIkUl3aBTlLfb

echo "LambdaTest Demo Test Runner"
echo "=========================="
echo ""

case "$1" in
    "single")
        echo "Running Single Test (Web Browser Automation)..."
        mvn test -D suite=single.xml
        ;;
    "mobile")
        echo "Running Mobile Browser Test..."
        mvn test -D suite=mobile.xml
        ;;
    "accessibility")
        echo "Running Accessibility Test..."
        mvn test -D suite=accessibility.xml
        ;;
    "parallel")
        echo "Running Parallel Test (Web Browser Automation)..."
        mvn test -D suite=parallel.xml
        ;;
    "android-single")
        echo "Running Android Single Test..."
        mvn test -D suite=android_single.xml
        ;;
    "android-parallel")
        echo "Running Android Parallel Test..."
        mvn test -D suite=android_parallel.xml
        ;;
    "ios-single")
        echo "Running iOS Single Test..."
        mvn test -D suite=iOS_single.xml
        ;;
    "ios-parallel")
        echo "Running iOS Parallel Test..."
        mvn test -D suite=iOS_parallel.xml
        ;;
    "android-5-devices")
        echo "Running 5 Android Devices Parallel Test..."
        mvn test -D suite=android_5_devices_parallel.xml
        ;;
    "ios-5-devices")
        echo "Running 5 iOS Devices Parallel Test..."
        mvn test -D suite=ios_5_devices_parallel.xml
        ;;
    "android-5-emulators")
        echo "Running 5 Android Emulators Parallel Test..."
        mvn test -D suite=android_5_emulators_parallel.xml
        ;;
    "ios-5-simulators")
        echo "Running 5 iOS Simulators Parallel Test..."
        mvn test -D suite=ios_5_simulators_parallel.xml
        ;;
    "xcui-test")
        echo "Running XCUI Test Suite..."
        mvn test -D suite=xcui_test_suite.xml
        ;;
    "xcui-sharding")
        echo "Running XCUI Test with Sharding..."
        mvn test -D suite=xcui_sharding.xml
        ;;
    "espresso-test")
        echo "Running Espresso Test Suite..."
        mvn test -D suite=espresso_test.xml
        ;;
    "espresso-sharding")
        echo "Running Espresso Test with Sharding and JUnit Report..."
        mvn test -D suite=espresso_sharding_junit.xml
        ;;
    "maestro")
        echo "Running Maestro Framework Test..."
        export PATH="$PATH":"$HOME/.maestro/bin"
        if command -v maestro &> /dev/null; then
            echo "Maestro CLI found, running tests..."
            echo "Note: Maestro tests require a connected device or emulator"
            echo "Available test files:"
            echo "  - maestro_test.yaml (original)"
            echo "  - maestro_local_test.yaml (for local devices)"
            echo "  - maestro_cloud_test.yaml (for cloud devices)"
            echo ""
            echo "Running local test (requires connected device/emulator):"
            maestro test maestro_local_test.yaml
        else
            echo "Maestro CLI not found. Installing..."
            curl -Ls "https://get.maestro.mobile.dev" | bash
            export PATH="$PATH":"$HOME/.maestro/bin"
            echo "Please run the test again: ./run_tests.sh maestro"
        fi
        ;;
    "maestro-local")
        echo "Running Maestro Local Test..."
        export PATH="$PATH":"$HOME/.maestro/bin"
        if command -v maestro &> /dev/null; then
            echo "Running Maestro local test (requires connected device/emulator)..."
            maestro test maestro_local_test.yaml
        else
            echo "Maestro CLI not found. Please install first: ./run_tests.sh maestro"
        fi
        ;;
    "maestro-cloud")
        echo "Running Maestro Cloud Test..."
        export PATH="$PATH":"$HOME/.maestro/bin"
        if command -v maestro &> /dev/null; then
            echo "Running Maestro cloud test..."
            maestro test maestro_cloud_test.yaml
        else
            echo "Maestro CLI not found. Please install first: ./run_tests.sh maestro"
        fi
        ;;
    "visual-baseline")
        if [ -z "$2" ]; then
            echo "Usage: $0 visual-baseline <project-name>"
            echo "Example: $0 visual-baseline MyProject"
            exit 1
        fi
        echo "Running Visual Baseline Test for project: $2"
        mvn test -D suite=visual_baseline.xml -Denv.project="$2"
        ;;
    "visual-change")
        if [ -z "$2" ]; then
            echo "Usage: $0 visual-change <project-name>"
            echo "Example: $0 visual-change MyProject"
            exit 1
        fi
        echo "Running Visual Change Build Test for project: $2"
        mvn test -D suite=visualchangebuild.xml -Denv.project="$2"
        ;;
    "cypress")
        echo "Running Cypress Tests..."
        npm test
        ;;
    "playwright-single")
        echo "Running Playwright Single Thread Test..."
        node playwright_single.js
        ;;
    "playwright-parallel")
        echo "Running Playwright Parallel Test..."
        node playwright_parallel.js
        ;;
    *)
        echo "Usage: $0 {test-type} [project-name]"
        echo ""
        echo "Available test types:"
        echo "  single              - Single test (Web Browser Automation)"
        echo "  mobile              - Mobile Browser Test"
        echo "  accessibility        - Accessibility Test"
        echo "  parallel           - Parallel Test (Web Browser Automation)"
        echo "  android-single     - Android Single Test"
        echo "  android-parallel   - Android Parallel Test"
        echo "  ios-single         - iOS Single Test"
        echo "  ios-parallel       - iOS Parallel Test"
        echo "  android-5-devices  - 5 Android Devices Parallel Test"
        echo "  ios-5-devices      - 5 iOS Devices Parallel Test"
        echo "  android-5-emulators - 5 Android Emulators Parallel Test"
        echo "  ios-5-simulators   - 5 iOS Simulators Parallel Test"
        echo "  xcui-test          - XCUI Test Suite"
        echo "  xcui-sharding      - XCUI Test with Sharding"
        echo "  espresso-test      - Espresso Test Suite"
        echo "  espresso-sharding  - Espresso Test with Sharding and JUnit Report"
        echo "  maestro            - Maestro Framework Test"
        echo "  maestro-local      - Maestro Local Test (requires device/emulator)"
        echo "  maestro-cloud      - Maestro Cloud Test"
        echo "  visual-baseline    - Visual Baseline Test (requires project name)"
        echo "  visual-change      - Visual Change Build Test (requires project name)"
        echo "  cypress            - Cypress Tests"
        echo "  playwright-single  - Playwright Single Thread"
        echo "  playwright-parallel - Playwright Parallel"
        echo ""
        echo "Examples:"
        echo "  $0 single"
        echo "  $0 parallel"
        echo "  $0 visual-baseline MyProject"
        echo "  $0 cypress"
        exit 1
        ;;
esac
