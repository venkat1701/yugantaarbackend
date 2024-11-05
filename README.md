# Yugantaar Backend

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/venkat1701/yugantaarbackend/issues)

Backend infrastructure for Yugantaar - The Tech Event by Scaler School of Technology.

## 📖 Table of Contents
- [About](#about)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Environment Variables](#environment-variables)
- [Contributing](#contributing)
- [Contact](#contact)
- [License](#license)

## About
This repository contains the backend implementation for Yugantaar, a premier technical event organized by Scaler School of Technology. The project handles event registrations, user management, payment processing, and other essential functionalities required for seamless event operations.

## Tech Stack
- Java 23
- Spring Boot 3.3.5
- PostgreSQL

## Prerequisites
Before running this project, make sure you have the following installed:
- JDK 23 or later
- Maven 3.6 or later
- PostgreSQL 13 or later
- Git

## Setup & Installation

1. Clone the repository
```bash
git clone https://github.com/venkat1701/yugantaarbackend.git
cd yugantaarbackend
```

2. Configure PostgreSQL
```sql
CREATE DATABASE yugantaar;
```

3. Configure application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yugantaar
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Build the project
```bash
mvn clean install
```

5. Run the application
```bash
mvn spring-boot:run
```

The application should now be running on `http://localhost:8080`

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── io/
│   │       └── github/venkat1701/
│   │           └── yugantaarbackend/
│   │               ├── config/
│   │               ├── controllers/
│   │               ├── dto/
│   │               ├── exceptions/
│   │               ├── models/
│   │               ├── repositories/
│   │               ├── services/
│   │               └── YugantaarApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## API Documentation
Once the application is running, you can access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

## Environment Variables
The following environment variables need to be set:
```
POSTGRES_URL=jdbc:postgresql://localhost:5432/yugantaar
POSTGRES_USERNAME=your_username
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
```

## Contributing
We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a new branch
```bash
git checkout -b feature/amazing-feature
```
3. Make your changes
4. Commit your changes
```bash
git commit -m 'Add some amazing feature'
```
5. Push to the branch
```bash
git push origin feature/amazing-feature
```
6. Create a Pull Request

### Pull Request Guidelines
- Create a detailed PR description explaining what the changes are for
- Include any relevant issue numbers
- Add screenshots if applicable
- Ensure all tests pass
- Update documentation if necessary

## Contact
- Project Initializer and Maintainer: Krish Jaiswal (Venkat) - [LinkedIn](https://linkedin.com/in/jaiswal-krish)
- Project Supervisor: Abhinav Gupta (Tech Committee Head) - [GitHub](https://github.com/abhinavgupta-de)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
This project is owned and maintained by Krish Jaiswal and Abhinav Gupta.
