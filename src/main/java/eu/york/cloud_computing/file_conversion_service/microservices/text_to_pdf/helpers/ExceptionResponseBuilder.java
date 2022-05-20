package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.helpers;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseBuilder {
    public static Map<String, String> buildExceptionResponse(String content, String creator){
        // Create JSON structure and add provided data
        Map<String, String> body = new HashMap<>();
        body.put("message", content);
        body.put("failed_at", creator);
        // Add Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String timeStamp = dateFormat.format(new Date());
        body.put("timestamp", timeStamp);
        return body;
    }
}
