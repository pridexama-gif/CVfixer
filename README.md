# CVfixer
# CV Fixer - AI CV Analysis

Java Spring Boot service that analyzes CVs and gives a score + missing sections, issues and suggestions.

## Tech Stack
- Java 17+
- Spring Boot 3
- Spring Data JPA
- Jakarta Validation
- Maven
- PostgreSQL / H2

## Entity: CvAnalysis
```java
- id: Long (auto-generated)
- score: int 0-100
- missingSections: String (max 2000)
- issues: TEXT
- suggestions: TEXT
