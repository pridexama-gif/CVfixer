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

This upload endpoint supports readable text-based CV formats, including PDF and DOCX files through Apache Tika.

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

- `POST /api/cvs` — create CV from JSON body
- `POST /api/cvs/upload` — upload a CV file and store its extracted text
- `GET /api/cvs` — list CV documents
- `GET /api/cvs/{id}` — get one CV document
- `GET /api/cvs/{id}/analysis` — score skills found in the CV
- `DELETE /api/cvs/{id}` — delete one CV document

This version adds real text extraction for uploaded CV files, which is the key next step toward a practical CV evaluation backend. The remaining improvements are richer CV parsing, better skill extraction, structured candidate profiles, and production database configuration.
