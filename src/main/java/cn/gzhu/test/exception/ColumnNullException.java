package cn.gzhu.test.exception;

public class ColumnNullException extends RuntimeException {

    public ColumnNullException() {
    }

    public ColumnNullException(String message) {
        super(message);
    }
}
