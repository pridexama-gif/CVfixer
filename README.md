# CVfixer

CVfixer is a Spring Boot backend for CV analysis and recruitment matching. The application stores CV documents, extracts text from uploaded files, analyzes skill coverage, extracts structured candidate profile information, and matches a candidate CV against a job description.

## Technology stack

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Data JPA / Hibernate
- Jakarta Bean Validation
- Apache Tika for PDF/DOCX/TXT text extraction
- H2 for local development

## Project status

This project is currently a functional MVP/backend prototype for a CV analysis and hiring-fit workflow. It is not yet a production-ready recruitment platform, but it provides a solid starting point for further development.

## Features

- Store CV documents in a database
- Upload CV files and extract text automatically
- Analyze CV content for technical skill match scores
- Extract structured candidate profile fields such as:
  - full name
  - email
  - phone number
  - skills
  - experience years
  - education
  - summary
- Create job descriptions
- Match a candidate CV against a job posting and return a score, matched skills, and missing skills

## Run locally

```bash
./mvnw spring-boot:run
```

If the Maven wrapper is not present, use:

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## API

### Create a CV from JSON

```bash
curl -X POST http://localhost:8080/api/cvs \
  -H 'Content-Type: application/json' \
  -d '{"fileName":"resume.txt","content":"Java developer with Spring Boot experience"}'
```

### Upload a CV file

```bash
curl -X POST http://localhost:8080/api/cvs/upload \
  -F "file=@/path/to/resume.pdf"
```

### List CVs

```bash
curl http://localhost:8080/api/cvs
```

### Get a CV by ID

```bash
curl http://localhost:8080/api/cvs/1
```

### Analyze a CV

```bash
curl http://localhost:8080/api/cvs/1/analysis
```

### Extract a structured candidate profile

```bash
curl http://localhost:8080/api/cvs/1/profile
```

### Create a job description

```bash
curl -X POST http://localhost:8080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Java Backend Developer","description":"We need a Java engineer with Spring Boot, REST APIs, SQL, Docker and Git experience.","requiredSkills":"java, spring boot, rest, sql, docker, git","minExperienceYears":3}'
```

### Match a candidate CV against a job description

```bash
curl http://localhost:8080/api/jobs/1/match/1
```

Example response:

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

## Endpoints summary

- `POST /api/cvs` — create CV from JSON body
- `POST /api/cvs/upload` — upload a CV file and store extracted text
- `GET /api/cvs` — list all CV documents
- `GET /api/cvs/{id}` — get one CV document
- `GET /api/cvs/{id}/analysis` — score CV skill fit
- `GET /api/cvs/{id}/profile` — extract structured candidate profile data
- `POST /api/jobs` — create a job description
- `GET /api/jobs/{jobId}/match/{cvId}` — compare a CV against a job posting
- `DELETE /api/cvs/{id}` — delete a CV document

## Roadmap

Planned next improvements:

- stronger CV parsing with better field extraction
- more intelligent job-to-candidate scoring
- ranked shortlist generation
- Postgres/MySQL production database setup
- authentication and user management
- deployment and CI/CD pipeline
- dashboard for recruiters and administrators

## License

This project is currently a personal development project and is not yet published with a formal license.
