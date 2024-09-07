# edupayroll.com

## Overview

...

## Installation

Follow these steps to set up the project on your local machine:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/hayrullahalper/edupayroll.git
   cd edupayroll
   ```

## Configuration

Before running the project, ensure you have configured all necessary environment variables and settings.

### Fetching Environment Variables

To manage environment variables, you need to fetch them from AWS Secrets Manager. Here’s how you can do it:

1. **Install and Configure AWS CLI**

   Install the AWS CLI by following the instructions in
   the [official documentation](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html). <br/>
   Run the following command and provide your AWS credentials and region:
   ```bash
   aws configure
   ```
    - **AWS Access Key ID**
    - **AWS Secret Access Key**
    - **Default region name**: Enter `eu-central-1`
    - **Default output format**: Leave as `json` (default)


2. **Fetch Secrets**

   Use the `fetch-secrets` target in the `Makefile` to fetch and update your environment variables. Run:
   ```bash
   make fetch-secrets
   ```

   This command makes the `fetch_secrets.sh` script executable and runs it, updating the `.env` file with secrets from
   AWS Secrets Manager.
