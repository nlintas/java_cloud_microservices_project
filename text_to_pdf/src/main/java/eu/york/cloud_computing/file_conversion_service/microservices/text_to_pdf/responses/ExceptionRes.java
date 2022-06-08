package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionRes extends RepresentationModel<ExceptionRes> {
    @JsonProperty("failed_at")
    private final String failed_at;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("timestamp")
    private final String timestamp;

    @JsonCreator
    public ExceptionRes(String message, String failed_at) {
        this.failed_at = failed_at;
        this.message = message;
        // Create Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        this.timestamp = dateFormat.format(new Date());
    }
}
