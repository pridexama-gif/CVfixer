# CVfixer

CV analysis application backend built with:

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Data JPA / Hibernate
- Jakarta Bean Validation
- Apache Tika for PDF/DOCX/TXT text extraction
- H2 for local development

## Run locally

```bash
./mvnw spring-boot:run
```

If Maven Wrapper has not been generated yet, use:

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

## Current API

Create a CV document from JSON:

```bash
curl -X POST http://localhost:8080/api/cvs \
  -H 'Content-Type: application/json' \
  -d '{"fileName":"resume.txt","content":"Java developer with Spring Boot experience"}'
```

Upload a CV file for parsing and storage:

```bash
curl -X POST http://localhost:8080/api/cvs/upload \
  -F "file=@/path/to/resume.pdf"
```

List all CV records:

```bash
curl http://localhost:8080/api/cvs
```

Get one CV record:

```bash
curl http://localhost:8080/api/cvs/1
```

Analyze a CV for skill match:

```bash
curl http://localhost:8080/api/cvs/1/analysis
```

Extract a structured CV profile:

```bash
curl http://localhost:8080/api/cvs/1/profile
```

Create a job description:

```bash
curl -X POST http://localhost:8080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Java Backend Developer","description":"We need a Java engineer with Spring Boot, REST APIs, SQL, Docker and Git experience.","requiredSkills":"java, spring boot, rest, sql, docker, git","minExperienceYears":3}'
```

Match a candidate CV against a job description:

```bash
curl http://localhost:8080/api/jobs/1/match/1
```

Example match response:

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

Other endpoints:

- `POST /api/cvs` — create CV from JSON body
- `POST /api/cvs/upload` — upload a CV file and store its extracted text
- `GET /api/cvs` — list CV documents
- `GET /api/cvs/{id}` — get one CV document
- `GET /api/cvs/{id}/analysis` — score skills found in the CV
- `GET /api/cvs/{id}/profile` — extract a structured candidate profile from the CV
- `POST /api/jobs` — add a job description
- `GET /api/jobs/{jobId}/match/{cvId}` — compare a CV to a job description and return a match score
- `DELETE /api/cvs/{id}` — delete one CV document

This version adds recruiter-facing job-to-candidate matching so the backend can compare a CV against a position and give a match score with matched and missing skills. The next major advancement is a more intelligent ranking model and a production-grade database setup.
