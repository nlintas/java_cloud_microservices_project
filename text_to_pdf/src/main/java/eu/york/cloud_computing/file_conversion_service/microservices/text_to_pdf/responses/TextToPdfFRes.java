package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class TextToPdfFRes extends RepresentationModel<TextToPdfFRes> {
    @JsonProperty("pdf")
    public final byte[] pdf;

    @JsonCreator
    public TextToPdfFRes(byte[] pdf) {
        this.pdf = pdf;
    }

}
