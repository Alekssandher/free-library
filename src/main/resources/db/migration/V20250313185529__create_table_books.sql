CREATE TABLE BOOKS (
    id BIGSERIAL PRIMARY KEY,
    public_id BIGINT UNIQUE NOT NULL, 
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    language VARCHAR(20) NOT NULL,
    file_id BIGINT UNIQUE NOT NULL, 
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    uploaded_by BIGINT NOT NULL, 
    published_at SMALLINT CHECK (published_at IS NULL OR (published_at >= 1 AND published_at <= 9999)),
    publisher VARCHAR(255) DEFAULT NULL,
    category VARCHAR(100) NOT NULL,
    CONSTRAINT FK_BOOK_UPLOADER FOREIGN KEY (uploaded_by) REFERENCES USERS (public_id)
);