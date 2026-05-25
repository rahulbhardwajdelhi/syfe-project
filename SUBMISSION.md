# Submission Checklist

Use this before emailing **Fauzia Khan**.

## 1. GitHub

- [ ] Repository is **public**
- [ ] README explains how to run and test
- [ ] `financial_manager_tests.sh` is in the repo root
- [ ] No secrets committed (no real passwords in properties)

## 2. Local verification

```powershell
cd <your-project-folder>
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

In Git Bash (second terminal):

```bash
bash financial_manager_tests.sh http://localhost:8080/api
```

- [ ] **86/86** tests passed locally

## 3. Render deployment

See [DEPLOYMENT.md](DEPLOYMENT.md).

```bash
bash financial_manager_tests.sh https://YOUR-SERVICE.onrender.com/api
```

- [ ] **86/86** tests passed on live URL
- [ ] Screenshot of terminal showing “ALL TESTS PASSED”

## 4. Email content

Subject example: `Personal Finance Manager – [Your Name]`

Body:

1. GitHub link  
2. Live API base URL (e.g. `https://xxx.onrender.com/api`)  
3. Screenshot of E2E test summary  
4. Optional: JaCoCo coverage screenshot (`target/site/jacoco/index.html`)

## 5. What graders often look for

| Requirement | Where to find it |
|-------------|------------------|
| Layered architecture | `controller/`, `service/`, `repository/` |
| DTOs vs entities | `dto/` vs `entity/` |
| Global exception handler | `GlobalExceptionHandler.java` |
| Session auth | `SecurityConfig.java`, `AuthController.java` |
| Unit tests + coverage | `src/test/`, JaCoCo report |
| API matches spec | E2E script 86/86 |
