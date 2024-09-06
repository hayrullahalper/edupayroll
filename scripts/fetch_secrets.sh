#!/bin/bash

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check if aws-cli is installed
if ! command_exists aws; then
    echo "Error: aws-cli is not installed."
    echo "Please install aws-cli to use this script."
    echo "For macOS, you can follow the instructions at:"
    echo "https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html"
    exit 1
fi

# Check if jq is installed
if ! command_exists jq; then
    echo "Error: jq is not installed."
    echo "Please install jq to use this script."
    echo "For macOS, you can install it using Homebrew:"
    echo "https://formulae.brew.sh/formula/jq#default"
    echo "Run: brew install jq"
    exit 1
fi

# Check if aws-cli is configured
if ! aws sts get-caller-identity &>/dev/null; then
    echo "Error: aws-cli is not configured or not logged in."
    echo "Please run 'aws configure' to set up your AWS credentials."
    exit 1
fi

# List directories under packages/
echo "Available packages:"
packages=($(ls -d packages/*/))
for i in "${!packages[@]}"; do
    echo "$((i+1)). ${packages[i]}"
done

# Ask user to select a package
read -p "Enter the number of the package you want to update: " package_number
package_index=$((package_number-1))

if [[ $package_index -lt 0 || $package_index -ge ${#packages[@]} ]]; then
    echo "Error: Invalid package number."
    exit 1
fi

selected_package=${packages[package_index]}
env_sample_file="${selected_package}.env.sample"

# Check if .env.sample file exists
if [[ ! -f "$env_sample_file" ]]; then
    echo "Error: .env.sample file not found in ${selected_package}"
    exit 1
fi

# Ask for the AWS secret name
read -p "Enter the name of the AWS secret you want to load: " secret_name

# Fetch the secret from AWS Secrets Manager and store the JSON in a variable
secret_json=$(aws secretsmanager get-secret-value --secret-id "$secret_name" --query SecretString --output text)

if [[ $? -ne 0 ]]; then
    echo "Error: Failed to fetch the secret from AWS Secrets Manager."
    exit 1
fi

# Parse the JSON into a local file or a variable for further processing
secret_data=$(echo "$secret_json" | jq -r '.')

# Copy .env.sample to .env
env_file="${selected_package}.env"
cp "$env_sample_file" "$env_file"

# Read the secret JSON and update .env file
while IFS="=" read -r key value; do
    # Skip comments and empty lines
    [[ $key =~ ^#.*$ || -z $key ]] && continue

    # Check if the value is empty in .env file
    if [[ -z $value ]]; then
        # Extract the value from the secret JSON using jq
        secret_value=$(echo "$secret_data" | jq -r ".${key} // \"\"")

        # If a value is found, update the .env file
        sed -i '' "s|^$key=.*|$key=$secret_value|" "$env_file"
    fi
done < "$env_file"

echo "Secret values have been updated in $env_file"