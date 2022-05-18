package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Responsible for managing the conversion process of text to pdf's
@Service
public class TextToPDFService {
    // Methods
    public void c2f(HttpServletResponse response) throws IOException {
        // Create Pdf
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        format(document);
    }

    // All pdf formatting should occur within. The document is preserved without a return.
    private void format(Document document) {
        document.open();
        Paragraph paragraph2 = new Paragraph("test");
        document.add(paragraph2);
        document.close();
    }
}

