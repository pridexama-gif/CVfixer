# CVfixer

CVfixer is a Spring Boot backend for analyzing CVs and matching candidates to job requirements. The project stores CV documents, extracts text from uploaded files, analyzes technical skill fit, extracts structured candidate profile details, and matches candidate profiles against job descriptions.

This repository is currently an MVP/backend prototype for a CV screening and recruitment-support workflow. It is a solid foundation for continued development, but it is not yet a full production-ready hiring platform.

## Tech stack

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Data JPA / Hibernate
- Jakarta Validation
- Apache Tika for document text extraction
- H2 database for local development

## Current capabilities

- Store CV documents in a database
- Upload CV files and extract readable text automatically
- Detect skills from CV content using keyword-based matching
- Extract candidate information such as:
  - full name
  - email
  - phone number
  - skills
  - experience years
  - education
  - summary
- Create job descriptions
- Match a candidate CV to a job description and produce a score
- Return matched skills and missing skills for a role

## Project status

This project is best described as a working MVP prototype rather than a complete production-ready application.

It is suitable for:
- learning backend architecture
- building a portfolio project
- demonstrating Spring Boot, REST APIs, JPA, and file processing
- expanding into a real recruitment matching product

It is not yet suitable for:
- production hiring workflows
- secure multi-user recruitment systems
- enterprise-grade deployment without further work

## Architecture overview

The project follows a standard layered Spring Boot structure:

- controllers for REST endpoints
- services for business logic
- repositories for persistence
- domain entities for CVs, candidate profiles, and job descriptions
- utility text extraction for uploaded CV files

## Getting started

### Prerequisites

- Java 17+
- Maven 3.8+

### Run locally

```bash
mvn spring-boot:run
```

Or, if Maven wrapper is available:

```bash
./mvnw spring-boot:run
```

Application URL:

```text
http://localhost:8080
```

## API overview

### 1. Create a CV document from JSON

```bash
curl -X POST http://localhost:8080/api/cvs \
  -H 'Content-Type: application/json' \
  -d '{"fileName":"resume.txt","content":"Java developer with Spring Boot experience"}'
```

### 2. Upload a CV file

```bash
curl -X POST http://localhost:8080/api/cvs/upload \
  -F "file=@/path/to/resume.pdf"
```

### 3. List CVs

```bash
curl http://localhost:8080/api/cvs
```

### 4. Get one CV by ID

```bash
curl http://localhost:8080/api/cvs/1
```

### 5. Analyze a CV for skill match

```bash
curl http://localhost:8080/api/cvs/1/analysis
```

### 6. Extract a structured candidate profile

```bash
curl http://localhost:8080/api/cvs/1/profile
```

### 7. Create a job description

```bash
curl -X POST http://localhost:8080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Java Backend Developer","description":"We need a Java engineer with Spring Boot, REST APIs, SQL, Docker and Git experience.","requiredSkills":"java, spring boot, rest, sql, docker, git","minExperienceYears":3}'
```

### 8. Match a candidate against a job

```bash
curl http://localhost:8080/api/jobs/1/match/1
```

## Example match response

```json
{
  "cvId": 1,
  "jobId": 1,
  "jobTitle": "Java Backend Developer",
  "score": 85,
  "recommendation": "Strong match",
  "candidateName": "John Smith",
  "matchedSkills": ["java", "spring boot", "rest", "sql", "docker"],
  "missingSkills": ["kubernetes"],
  "candidateExperienceYears": 6,
  "candidateEmail": "john.smith@example.com"
}
```

## Main endpoints

- `POST /api/cvs` — create a CV from JSON
- `POST /api/cvs/upload` — upload a CV file and store extracted text
- `GET /api/cvs` — list all CVs
- `GET /api/cvs/{id}` — get one CV
- `GET /api/cvs/{id}/analysis` — analyze skill coverage
- `GET /api/cvs/{id}/profile` — extract structured candidate profile
- `POST /api/jobs` — create a job description
- `GET /api/jobs/{jobId}/match/{cvId}` — compare a CV against a job
- `DELETE /api/cvs/{id}` — delete a CV record

## What is still needed to make it production-ready

The next improvement phases would include:

- PostgreSQL/MySQL production database setup
- authentication and authorization
- improved extraction and normalization of CV fields
- smarter weighted candidate scoring
- candidate shortlists and saved match results
- Docker and CI/CD setup
- API documentation with Swagger/OpenAPI
- recruiter/admin dashboard
- better security and environment configuration

## Summary

CVfixer is a practical backend MVP for a recruitment and CV-screening tool. It demonstrates core backend capabilities in a realistic domain and acts as a strong starting point for future product development.

This project is already a useful portfolio or demo project, but it is intentionally positioned as a prototype rather than a complete production system.

## License

This project currently does not include a formal public license. It is intended for personal development and learning use unless otherwise specified.
