package jpa.study.exception;

public class NotEnoughtStockException extends RuntimeException {

    /**
     * 아래오같이 ovverride한 이유는
     * message와 근원적 유같은것들을 추가하기 위해서
     */

    public NotEnoughtStockException() {
        super();
    }

    public NotEnoughtStockException(String message) {
        super(message);
    }

    public NotEnoughtStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughtStockException(Throwable cause) {
        super(cause);
    }
}

