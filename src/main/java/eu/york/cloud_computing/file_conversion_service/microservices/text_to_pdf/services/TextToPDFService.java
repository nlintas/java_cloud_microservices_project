package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

@Service
public class TextToPDFService {
    // Methods
    public byte[] convertTextToPdf(String input) {
        // Create Pdf
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        var writer = new PdfWriter(output);
        var pdf = new PdfDocument(writer);
        var document = new Document(pdf);
        // Provide some text if the input was null
        if(input == null)
            input = "Seems like no input was provided, here is some text for you!";
        // Format
        format(document, input);
        // Finalize Operation
        document.close();
        return output.toByteArray();
    }

    // All pdf formatting should occur within. The document is preserved without a return.
    private void format(Document document, String input) {
        document.add(new Paragraph(input));
    }
}

