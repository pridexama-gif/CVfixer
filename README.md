# CVfixer

CV analysis application backend built with:

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Data JPA / Hibernate
- Jakarta Bean Validation
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

Create a CV document:

```bash
curl -X POST http://localhost:8080/api/cvs \
  -H 'Content-Type: application/json' \
  -d '{"fileName":"resume.txt","content":"Java developer with Spring Boot experience"}'
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

Other endpoints:

- `GET /api/cvs` — list CV documents
- `GET /api/cvs/{id}` — get one CV document
- `GET /api/cvs/{id}/analysis` — score skills found in the CV
- `DELETE /api/cvs/{id}` — delete one CV document

This backend is now a starter foundation for a CV analysis workflow. The next major upgrades will include real PDF/DOCX parsing, improved skill extraction, candidate scoring, and a production database.
