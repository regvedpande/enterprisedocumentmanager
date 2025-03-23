# Enterprise Document Manager

**Enterprise Document Manager** is a secure, user-friendly document management system built with Spring Boot and PostgreSQL. It allows users to upload, search, and download documents while enforcing permission-based access control. This application is designed for enterprise use, ensuring that sensitive documents are only accessible to authorized users.

## Features

- **Document Upload**: Upload documents (e.g., `.docx`, `.pdf`) with a maximum size of 10MB.
- **Search Documents**: Search for documents by title, with results filtered based on user permissions.
- **Permission-Based Access**: Documents are accessible only to users with appropriate permissions (e.g., `READ` or `WRITE`).
- **Download Documents**: Authorized users can download documents.
- **Transactional Safety**: Uses Spring’s `@Transactional` to ensure database operations are performed safely, especially for PostgreSQL Large Objects (LOBs).
- **Optimized Queries**: Avoids unnecessary fetching of large data (e.g., document content) during searches for better performance.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2.3
- **Database**: PostgreSQL 15
- **Frontend**: Thymeleaf for server-side rendering
- **Build Tool**: Maven
- **Dependencies**:
  - Spring Data JPA
  - Spring Web
  - PostgreSQL Driver
  - Lombok
  - Thymeleaf

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17**: [Download and install JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven**: [Download and install Maven](https://maven.apache.org/download.cgi)
- **PostgreSQL 15**: [Download and install PostgreSQL](https://www.postgresql.org/download/)
- **Git**: [Download and install Git](https://git-scm.com/downloads)

## Setup Instructions

### 1. Clone the Repository
Clone the project to your local machine:

```bash
git clone https://github.com/regvedpande/enterprisedocumentmanager.git
cd enterprisedocumentmanager
```

### 2. Configure PostgreSQL
Set up a PostgreSQL database for the application:

1. Start your PostgreSQL server.
2. Create a database named `documentmanagerdatabse`:
   ```sql
   psql -U postgres
   CREATE DATABASE documentmanagerdatabse;
   ```
3. Update the database configuration in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/documentmanagerdatabse
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### 3. Build the Project
Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application
Start the Spring Boot application:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## Usage

1. **Access the Home Page**:
   - Open `http://localhost:8080` in your browser.
   - You’ll see a simple interface with a search bar, a document list, and an upload form.

2. **Upload a Document**:
   - Enter a document title in the "Document Title" field.
   - Select a file (e.g., `.docx`, `.pdf`) with a size up to 10MB.
   - Click "Upload".
   - The document will be saved, and you’ll be redirected to the home page, where the document will appear in the list.

3. **Search for Documents**:
   - Enter a keyword in the search bar and click "Search".
   - The table will display documents whose titles match the keyword, filtered by your user permissions (default user: `anonymous`).

4. **Download a Document**:
   - Click the "Download" button next to a document in the list.
   - The document will be downloaded to your device if you have permission to access it.

5. **Verify Database**:
   - Check the `document` and `document_permission` tables in PostgreSQL:
     ```sql
     psql -U postgres -d documentmanagerdatabse
     SELECT * FROM document;
     SELECT * FROM document_permission;
     ```

## Troubleshooting

- **Whitelabel Error Page**: If you see a `Whitelabel Error Page` with a 500 status, check the console logs. Common issues include:
  - PostgreSQL not running or incorrect database configuration.
  - Missing database tables (ensure `spring.jpa.hibernate.ddl-auto=update` is set).
- **Large Object Error**: If you encounter `PSQLException: Large Objects may not be used in auto-commit mode`, ensure that database operations involving LOBs are wrapped in `@Transactional`.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -m "Add your feature"`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## Contact

For questions, suggestions, or issues, feel free to reach out:

- **Email**: [regregd@outlook.com](mailto:regregd@outlook.com)
- **GitHub**: [regvedpande](https://github.com/regvedpande)
