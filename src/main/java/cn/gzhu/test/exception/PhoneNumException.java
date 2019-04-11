package cn.gzhu.test.exception;

public class PhoneNumException extends RuntimeException {

    public PhoneNumException() {
    }

    public PhoneNumException(String message) {
        super(message);
    }
}
