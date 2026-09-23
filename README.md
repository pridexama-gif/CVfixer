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

Other endpoints:

- `GET /api/cvs` — list CV documents
- `GET /api/cvs/{id}` — get one CV document
- `DELETE /api/cvs/{id}` — delete one CV document

This is the initial backend foundation. File upload, authentication, production database configuration, and the actual CV analysis engine still need to be added as separate features.
