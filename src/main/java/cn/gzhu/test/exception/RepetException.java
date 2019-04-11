package cn.gzhu.test.exception;

public class RepetException extends RuntimeException {

    public RepetException() {
    }

    public RepetException(String message) {
        super(message);
    }
}
