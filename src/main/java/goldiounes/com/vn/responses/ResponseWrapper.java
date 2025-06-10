package goldiounes.com.vn.responses;

public class ResponseWrapper<T> {
    private String message;
    private T data;

    public ResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
