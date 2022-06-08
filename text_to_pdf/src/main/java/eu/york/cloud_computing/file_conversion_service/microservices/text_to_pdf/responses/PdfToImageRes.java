package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class PdfToImageRes extends RepresentationModel<PdfToImageRes> {
    @JsonProperty("zip")
    public final byte[] zip;

    @JsonCreator
    public PdfToImageRes(byte[] zip) {
        this.zip = zip;
    }
}
