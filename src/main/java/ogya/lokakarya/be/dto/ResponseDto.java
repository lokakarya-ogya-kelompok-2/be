package ogya.lokakarya.be.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto<T> {
    private boolean success;
    private T content;

    @Builder.Default
    @JsonProperty("time_taken")
    private String timeTaken = formatExecutionTime(getTimeTakenMs());

    @SuppressWarnings("null")
    private static Long getTimeTakenMs() {
        try {
            HttpServletRequest req =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest();

            Long startTime = (Long) req.getAttribute("startTime");
            return System.currentTimeMillis() - startTime;
        } catch (Exception e) {
            return 0L;
        }
    }

    private static String formatExecutionTime(long executionTimeMs) {
        long hours = executionTimeMs / 3600000;
        long minutes = (executionTimeMs % 3600000) / 60000;
        long seconds = (executionTimeMs % 60000) / 1000;
        long milliseconds = executionTimeMs % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }

    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    private String message;

    @JsonProperty("total_records")
    private Long totalRecords;

    @Builder.Default
    @JsonProperty("page_size")
    private Integer pageSize = 0;

    @Builder.Default
    @JsonProperty("page_number")
    private Integer pageNumber = 1;

    @Builder.Default
    @JsonProperty("total_pages")
    private Integer totalPages = 1;

    public ResponseEntity<ResponseDto<T>> toResponse(HttpStatus httpStatus) {
        return new ResponseEntity<>(this, httpStatus);
    }

}
