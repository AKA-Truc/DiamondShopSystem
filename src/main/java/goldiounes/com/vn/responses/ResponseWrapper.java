package goldiounes.com.vn.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseWrapper<T> {
    // Getters and setters
    private String message;
    private T data;

    public ResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
