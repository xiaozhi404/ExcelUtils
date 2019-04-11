package cn.gzhu.test.exception;

public class IDCardNoException extends RuntimeException {

    public IDCardNoException() {
    }

    public IDCardNoException(String message) {
        super(message);
    }
}
