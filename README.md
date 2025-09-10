# Parallel Automation Project (Java + TestNG)

## Overview
This project runs 20+ tests (10 API + 10 UI) in parallel using TestNG. Results are logged to:
- MySQL database (auto-created): `automation_results.test_results`
- CSV: `reports/results.csv`
- HTML: `reports/results.html`

## Requirements
- Java JDK 17+ (Java 24 compatible)
- Maven
- MySQL running locally (or reachable) with user root / password 77088

## How to run
1. Import project into IntelliJ as a Maven project.
2. Ensure MySQL is running on localhost:3306.
3. From terminal: `mvn test` OR run the TestNG suite `src/test/resources/testng.xml`.
4. After run:
   - Check DB table `automation_results.test_results` in SQL Workbench.
   - Open `reports/results.csv` or `reports/results.html` in your browser.

## Notes
- ChromeDriver is managed automatically by WebDriverManager.
- To run only API tests or disable UI tests, you can set the `useBrowser` parameter to false in TestNG or modify BaseTest setup.
