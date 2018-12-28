package cn.gzhu.test.exception;

public class NotExcelException extends RuntimeException {

    public NotExcelException() {
    }

    public NotExcelException(String message) {
        super(message);
    }
}
