#!/bin/bash

# LambdaTest Hyperexecute Test Runner
# This script helps you run Hyperexecute tests using different methods

# Set your LambdaTest credentials
export LT_USERNAME=matthewpaul
export LT_ACCESS_KEY=LT_ODGmhOPe2LqV8PuZXMKsY1IKOGyI1VZpkOQIkUl3aBTlLfb

echo "🚀 LambdaTest Hyperexecute Test Runner"
echo "======================================"
echo ""

# Function to run tests via Hyperexecute API
run_via_api() {
    local yaml_file=$1
    local test_name=$2
    
    echo "📡 Running $test_name via Hyperexecute API..."
    
    # Base64 encode the YAML file
    local yaml_content=$(cat "$yaml_file" | base64)
    
    # Create JSON payload
    local json_payload=$(cat << EOF
{
    "yaml": "$yaml_content",
    "buildName": "$test_name - $(date '+%Y-%m-%d %H:%M:%S')",
    "buildId": "build-$(date +%s)"
}
EOF
)
    
    # Make API call to Hyperexecute
    echo "Sending request to Hyperexecute API..."
    curl -X POST "https://api.lambdatest.com/hyperexecute/v1/run" \
        -H "Authorization: Basic $(echo -n "$LT_USERNAME:$LT_ACCESS_KEY" | base64)" \
        -H "Content-Type: application/json" \
        -d "$json_payload" \
        -o "hyperexecute_response_${test_name}.json"
    
    if [ $? -eq 0 ]; then
        echo "✅ API request sent successfully!"
        echo "📄 Response saved to: hyperexecute_response_${test_name}.json"
        
        # Extract execution ID if available
        if command -v jq &> /dev/null; then
            local execution_id=$(cat "hyperexecute_response_${test_name}.json" | jq -r '.executionId // empty')
            if [ -n "$execution_id" ]; then
                echo "🆔 Execution ID: $execution_id"
                echo "🌐 View execution at: https://hyperexecute.lambdatest.com/executions/$execution_id"
            fi
        fi
    else
        echo "❌ Failed to send API request"
    fi
}

# Function to show manual execution steps
show_manual_steps() {
    local yaml_file=$1
    local test_name=$2
    
    echo "📋 Manual Execution Steps for $test_name:"
    echo "========================================"
    echo ""
    echo "1. 🌐 Go to LambdaTest Hyperexecute Dashboard:"
    echo "   https://hyperexecute.lambdatest.com"
    echo ""
    echo "2. 📁 Upload your YAML file: $yaml_file"
    echo ""
    echo "3. ⚙️  Configure the following settings:"
    echo "   - Username: $LT_USERNAME"
    echo "   - Access Key: $LT_ACCESS_KEY"
    echo "   - Platform: Windows/Linux (as specified in YAML)"
    echo ""
    echo "4. 🚀 Click 'Run Tests' to start execution"
    echo ""
    echo "5. 📊 Monitor execution progress in the dashboard"
    echo ""
    echo "📄 YAML Configuration:"
    echo "======================"
    cat "$yaml_file"
    echo ""
}

# Function to run using Docker (if available)
run_via_docker() {
    local yaml_file=$1
    local test_name=$2
    
    if command -v docker &> /dev/null; then
        echo "🐳 Running $test_name via Docker..."
        
        # Create a temporary directory for the test
        local temp_dir=$(mktemp -d)
        cp "$yaml_file" "$temp_dir/"
        cp -r . "$temp_dir/test-project/"
        
        # Run Hyperexecute in Docker
        docker run --rm \
            -v "$temp_dir:/workspace" \
            -e LT_USERNAME="$LT_USERNAME" \
            -e LT_ACCESS_KEY="$LT_ACCESS_KEY" \
            lambdatest/hyperexecute:latest \
            run "/workspace/$(basename "$yaml_file")"
        
        rm -rf "$temp_dir"
    else
        echo "❌ Docker not available. Please install Docker to use this method."
    fi
}

# Main execution logic
case "$1" in
    "testng")
        echo "🧪 Running TestNG Tests on Hyperexecute..."
        run_via_api "hyperexecute_testng.yaml" "TestNG"
        ;;
    "cucumber")
        echo "🥒 Running Cucumber Tests on Hyperexecute..."
        run_via_api "hyperexecute_cucumber.yaml" "Cucumber"
        ;;
    "api")
        echo "🔌 Running API Tests on Hyperexecute..."
        run_via_api "hyperexecute_api.yaml" "API"
        ;;
    "performance")
        echo "⚡ Running Performance Tests on Hyperexecute..."
        run_via_api "hyperexecute_performance.yaml" "Performance"
        ;;
    "mobile")
        echo "📱 Running Mobile Tests on Hyperexecute..."
        run_via_api "hyperexecute_mobile.yaml" "Mobile"
        ;;
    "allure")
        echo "📊 Running Allure Email Tests on Hyperexecute..."
        run_via_api "hyperexecute_allure_email.yaml" "Allure"
        ;;
    "github")
        echo "🐙 Running GitHub Integration Tests on Hyperexecute..."
        run_via_api "hyperexecute_github.yaml" "GitHub"
        ;;
    "private-maven")
        echo "🔒 Running Private Maven Tests on Hyperexecute..."
        run_via_api "hyperexecute_private_maven.yaml" "PrivateMaven"
        ;;
    "manual")
        echo "📋 Showing manual execution steps..."
        show_manual_steps "hyperexecute_testng.yaml" "TestNG"
        ;;
    "docker")
        echo "🐳 Running via Docker..."
        run_via_docker "hyperexecute_testng.yaml" "TestNG"
        ;;
    *)
        echo "Usage: $0 {testng|cucumber|api|performance|mobile|allure|github|private-maven|manual|docker}"
        echo ""
        echo "Available test types:"
        echo "  testng          - Run TestNG tests on Hyperexecute"
        echo "  cucumber        - Run Cucumber BDD tests on Hyperexecute"
        echo "  api             - Run API tests on Hyperexecute"
        echo "  performance     - Run performance tests on Hyperexecute"
        echo "  mobile          - Run mobile tests on Hyperexecute"
        echo "  allure          - Run tests with Allure email reports"
        echo "  github          - Run tests with GitHub integration"
        echo "  private-maven    - Run tests with private Maven dependencies"
        echo "  manual          - Show manual execution steps"
        echo "  docker          - Run via Docker (if available)"
        echo ""
        echo "Examples:"
        echo "  $0 testng"
        echo "  $0 cucumber"
        echo "  $0 manual"
        exit 1
        ;;
esac

echo ""
echo "🎉 Hyperexecute test execution completed!"
echo "📊 Check the LambdaTest dashboard for results:"
echo "   https://hyperexecute.lambdatest.com"
