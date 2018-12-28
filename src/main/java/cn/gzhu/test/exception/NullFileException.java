package cn.gzhu.test.exception;

/**
 * 空文件异常
 */
public class NullFileException  extends RuntimeException  {

    public NullFileException() {
    }

    public NullFileException(String message) {
        super(message);
    }
}
