#!/bin/bash

# LambdaTest Hyperexecute Dashboard Opener
# This script helps you open the Hyperexecute dashboard and prepare files for upload

echo "🚀 LambdaTest Hyperexecute Dashboard Helper"
echo "==========================================="
echo ""

# Function to open Hyperexecute dashboard
open_dashboard() {
    echo "🌐 Opening LambdaTest Hyperexecute Dashboard..."
    echo "URL: https://hyperexecute.lambdatest.com"
    echo ""
    
    # Try to open in default browser
    if command -v open &> /dev/null; then
        open "https://hyperexecute.lambdatest.com"
        echo "✅ Dashboard opened in your default browser"
    elif command -v xdg-open &> /dev/null; then
        xdg-open "https://hyperexecute.lambdatest.com"
        echo "✅ Dashboard opened in your default browser"
    else
        echo "📋 Please manually open: https://hyperexecute.lambdatest.com"
    fi
}

# Function to show upload instructions
show_upload_instructions() {
    echo "📤 Upload Instructions:"
    echo "======================="
    echo ""
    echo "1. 🌐 Go to: https://hyperexecute.lambdatest.com"
    echo "2. 🔐 Login with your LambdaTest credentials:"
    echo "   - Username: matthewpaul"
    echo "   - Password: [Your LambdaTest password]"
    echo ""
    echo "3. 📁 Look for 'Upload YAML' or 'Import Configuration' button"
    echo "4. 📂 Select one of these YAML files from your project:"
    echo ""
    
    # List available YAML files
    echo "Available YAML files:"
    for file in hyperexecute_*.yaml; do
        if [ -f "$file" ]; then
            echo "   📄 $file"
        fi
    done
    echo ""
    
    echo "5. 🎯 Recommended for first test: hyperexecute_testng.yaml"
    echo "6. ⚙️  Configure settings and click 'Run Test'"
    echo ""
}

# Function to show file locations
show_file_locations() {
    echo "📁 File Locations:"
    echo "=================="
    echo ""
    echo "Your YAML files are located in:"
    echo "📂 $(pwd)"
    echo ""
    echo "Available files:"
    ls -la hyperexecute_*.yaml | while read line; do
        echo "   $line"
    done
    echo ""
}

# Function to copy YAML content to clipboard (if possible)
copy_yaml_to_clipboard() {
    local yaml_file=$1
    
    if [ -f "$yaml_file" ]; then
        if command -v pbcopy &> /dev/null; then
            cat "$yaml_file" | pbcopy
            echo "📋 Content of $yaml_file copied to clipboard"
        elif command -v xclip &> /dev/null; then
            cat "$yaml_file" | xclip -selection clipboard
            echo "📋 Content of $yaml_file copied to clipboard"
        else
            echo "📄 Content of $yaml_file:"
            echo "========================"
            cat "$yaml_file"
        fi
    else
        echo "❌ File $yaml_file not found"
    fi
}

# Function to validate YAML syntax
validate_yaml() {
    local yaml_file=$1
    
    if [ -f "$yaml_file" ]; then
        echo "🔍 Validating YAML syntax for $yaml_file..."
        
        # Basic YAML validation
        if python3 -c "import yaml; yaml.safe_load(open('$yaml_file'))" 2>/dev/null; then
            echo "✅ YAML syntax is valid"
        else
            echo "❌ YAML syntax error detected"
            echo "💡 Tip: Check indentation and formatting"
        fi
    else
        echo "❌ File $yaml_file not found"
    fi
}

# Main execution
case "$1" in
    "open")
        open_dashboard
        ;;
    "instructions")
        show_upload_instructions
        ;;
    "files")
        show_file_locations
        ;;
    "copy")
        if [ -n "$2" ]; then
            copy_yaml_to_clipboard "$2"
        else
            copy_yaml_to_clipboard "hyperexecute_testng.yaml"
        fi
        ;;
    "validate")
        if [ -n "$2" ]; then
            validate_yaml "$2"
        else
            validate_yaml "hyperexecute_testng.yaml"
        fi
        ;;
    "all")
        open_dashboard
        echo ""
        show_upload_instructions
        echo ""
        show_file_locations
        ;;
    *)
        echo "Usage: $0 {open|instructions|files|copy|validate|all}"
        echo ""
        echo "Commands:"
        echo "  open         - Open Hyperexecute dashboard in browser"
        echo "  instructions - Show upload instructions"
        echo "  files        - Show file locations"
        echo "  copy [file]  - Copy YAML content to clipboard"
        echo "  validate [file] - Validate YAML syntax"
        echo "  all          - Open dashboard + show instructions + files"
        echo ""
        echo "Examples:"
        echo "  $0 open                    # Open dashboard"
        echo "  $0 instructions           # Show upload steps"
        echo "  $0 copy testng            # Copy testng YAML to clipboard"
        echo "  $0 validate testng        # Validate testng YAML"
        echo "  $0 all                    # Do everything"
        exit 1
        ;;
esac

echo ""
echo "🎉 Ready to upload your YAML files to Hyperexecute!"
echo "📊 Dashboard: https://hyperexecute.lambdatest.com"
