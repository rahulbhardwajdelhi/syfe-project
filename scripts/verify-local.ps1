# Quick local check on Windows (run from project root)
$ErrorActionPreference = "Stop"

Write-Host "Building and running unit tests..." -ForegroundColor Cyan
& .\mvnw.cmd -q clean test
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host ""
Write-Host "Tests OK. Start the app with:  .\mvnw.cmd spring-boot:run" -ForegroundColor Green
Write-Host "Then in Git Bash:  bash financial_manager_tests.sh http://localhost:8080/api" -ForegroundColor Green
