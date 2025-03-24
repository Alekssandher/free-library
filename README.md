# Free Library Backend Application

## Overview

Free Library is a Spring Boot-based backend application designed to manage a digital library system. The application provides functionality for user authentication, book management, PDF uploads, and administrative controls.

## Features

### Authentication
- User registration
- JWT-based authentication
- Role-based access control

### Book Management
- Create and list books
- Filter books by title, author, and category
- Administrative book management

### PDF Handling
- PDF file uploads
- Automatic cleanup of temporary PDF files

### Admin Capabilities
- User management
- Book deletion
- User activation/deactivation

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot
- **Security:** Spring Security
- **Authentication:** JWT
- **Database:** (Not specified in provided files, likely configurable)
- **Build Tool:** Gradle
- **Additional Libraries:**
  - Bcrypt for password hashing
  - Cloudinary for PDF storage
  - Swagger for API documentation

## Project Structure

```
src/main/java/alekssandher/free_library/
│
├── config/               # Configuration classes
├── dto/                  # Data Transfer Objects
├── entities/             # Database entity definitions
├── modules/              # Business logic modules
│   ├── admin/
│   ├── auth/
│   ├── book/
│   ├── pdf/
│   └── users/
├── repository/           # Database repositories
└── exception/            # Custom exception handling
```

## Key Components

1. **Authentication (`AuthController`):**
   - `/auth/register`: User registration
   - `/auth/login`: User login, returns JWT

2. **Book Management (`BookController`):**
   - `GET /books`: List books with filtering
   - `POST /books`: Create a new book

3. **Admin Operations (`AdminController`):**
   - `GET /admin/users/{name}`: Find users by name
   - `GET /admin/books`: List books (admin view)
   - `DELETE /admin/books/{bookPublicId}`: Delete a book
   - `PATCH /admin/users/{userPublicId}/{kind}`: Modify user status

4. **PDF Handling (`PdfController`):**
   - `POST /pdfs/uploadPdf`: Upload PDF files

## Security Features

- JWT-based authentication
- Role-based access control
- Custom access denied handling
- Stateless session management
- CORS configuration

## API Documentation

The project uses Swagger/OpenAPI for API documentation. Access the documentation at [`/docs.html`](https://free-library-ymg1.onrender.com/docs.html) when the application is running.

The API is available for online testing at: [Free Library API](https://free-library-ymg1.onrender.com/docs.html)

## Development Setup

### Prerequisites
- Java 21
- Gradle
- (Configured) Database
- Cloudinary account for PDF storage

### Development Configuration
For development mode, you can use the `dev` profile:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Build and Run
```bash
./gradlew clean build
./gradlew bootRun
```

## Docker Support

The project includes a Dockerfile for containerization:
- Uses multi-stage build
- Builds with Gradle
- Runs on Java 21

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the GPL 3 License - see the LICENSE file for details.
