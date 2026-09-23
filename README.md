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

Example profile response:

```json
{
  "cvId": 1,
  "fullName": "John Smith",
  "email": "john.smith@example.com",
  "phone": "+1 555 123 4567",
  "skills": ["java", "spring boot", "rest", "sql", "docker"],
  "experienceYears": 6,
  "education": "bachelor",
  "summary": "John Smith has 6 years of experience and skills in java, spring boot, rest, sql, docker. Education: bachelor."
}
```

Other endpoints:

- `POST /api/cvs` — create CV from JSON body
- `POST /api/cvs/upload` — upload a CV file and store its extracted text
- `GET /api/cvs` — list CV documents
- `GET /api/cvs/{id}` — get one CV document
- `GET /api/cvs/{id}/analysis` — score skills found in the CV
- `GET /api/cvs/{id}/profile` — extract a structured candidate profile from the CV
- `DELETE /api/cvs/{id}` — delete one CV document

This version adds a structured candidate profile extraction layer so the backend can identify candidates, emails, phone numbers, skill sets, education, and years of experience more realistically. The next major step is to move into job-description matching and recruiter-facing ranking.
