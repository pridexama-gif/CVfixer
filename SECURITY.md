# Security Policy

## Project status

CVfixer is an MVP/backend prototype and is not a production service. The repository is intended for authorized development, testing, and security-learning exercises only.

Do not test this project against systems, accounts, files, or data that you do not own or have explicit permission to assess.

## Supported versions

| Version | Supported |
| --- | --- |
| `main` | Yes, for development and authorized testing |
| Released production versions | None currently |

## Scope for authorized testing

When a maintainer provides a dedicated staging URL and written authorization, the following areas may be tested:

- Authentication and authorization controls
- Broken object-level authorization / IDOR
- CV and job-description access control
- File-upload validation and storage safety
- Path traversal and unsafe file handling
- Injection vulnerabilities
- Cross-site scripting where applicable
- Sensitive-data exposure
- Security misconfiguration
- Rate limiting and abuse controls
- Server-side request forgery where applicable

Testing must use only the supplied test accounts and synthetic CV/job data.

## Out of scope

The following are out of scope unless explicitly approved in writing:

- GitHub, Maven Central, Apache Tika, or other third-party infrastructure
- Denial-of-service, resource exhaustion, or stress testing
- Social engineering, phishing, or physical attacks
- Testing against production systems or other users' data
- Automated scanning that creates excessive traffic
- Spam, destructive actions, data deletion, or persistence
- Reports based only on missing best-practice headers without a demonstrated impact
- Vulnerabilities requiring an unsupported or obsolete dependency with no practical exploit path

## Safe testing rules

1. Obtain written authorization and a staging URL before testing a deployed instance.
2. Use synthetic data only; never upload real personal CVs or confidential information.
3. Avoid actions that modify, delete, expose, or disrupt data belonging to another user.
4. Stop testing and report immediately if you discover access to data outside your test account.
5. Do not publicly disclose a vulnerability before a fix and disclosure timeline are agreed.

## Reporting a vulnerability

Please report security issues privately to the repository owner through GitHub's private vulnerability reporting feature, when enabled, or through a private GitHub communication channel.

Include:

- A clear title and severity estimate
- Affected endpoint, file, or commit
- Preconditions and required permissions
- Reproduction steps or a minimal proof of concept
- Expected and actual behavior
- Security impact
- Suggested remediation, if available
- Screenshots or request/response samples with secrets and personal data removed

Please do not include real credentials, tokens, personal information, or confidential CV content in a report.

## Response expectations

This is a personal MVP project, so response and remediation times are best effort. Valid reports will be acknowledged when possible, investigated, and tracked privately. There is currently no paid bounty program or guarantee of monetary reward.

## Disclosure

Please allow reasonable time for investigation and remediation before any coordinated public disclosure. Do not publish exploit details or target information without explicit agreement from the maintainer.
