package alekssandher.free_library.exception;

public class Exceptions {

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class ConflictException extends RuntimeException {
        public ConflictException(String message) {
            super(message);
        }
    }

    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }

    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }

    }

    public static class InternalErrorException extends RuntimeException {
        public InternalErrorException(String message) {
            super(message);
        }
    }

    public static class ContentTooLargeException extends RuntimeException {
        public ContentTooLargeException(String message) {
            super(message);
        }
    }


    public static class TooManyRequestsException extends RuntimeException {
        public TooManyRequestsException(String message) {
            super(message);
        }
    }
}
