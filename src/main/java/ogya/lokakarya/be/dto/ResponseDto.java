package ogya.lokakarya.be.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto<T> {
    private boolean success;
    private T content;

    @JsonProperty("time_taken")
    private String timeTaken;

    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    private String message;


    public ResponseEntity<ResponseDto<T>> toResponse(HttpStatus httpStatus) {
        return new ResponseEntity<>(this, httpStatus);
    }
}
