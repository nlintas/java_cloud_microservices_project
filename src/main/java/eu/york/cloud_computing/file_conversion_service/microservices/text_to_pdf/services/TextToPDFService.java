package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

// Responsible for managing the conversion process of text to pdf's
@Service
public class TextToPDFService {
    // Methods
    public byte[] c2f() {
        // Create Pdf
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        var writer = new PdfWriter(output);
        var pdf = new PdfDocument(writer);
        var document = new Document(pdf);
        // Format
        format(document);
        // Finalize Operation
        document.close();
        return output.toByteArray();
    }

    // All pdf formatting should occur within. The document is preserved without a return.
    private void format(Document document) {
        document.add(new Paragraph("Test"));
    }
}

