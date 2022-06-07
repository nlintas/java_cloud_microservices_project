package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf.services;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class TextToPDFService {
    // Methods
    public byte[] convertTextToPdf(String input) {
        try {
            // Create Pdf
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            var writer = new PdfWriter(output);
            var pdf = new PdfDocument(writer);
            var document = new Document(pdf);
            // Provide some text if the input was null
            if (input == null)
                input = "Seems like no input was provided, here is some text for you!";
            // Format
            format(document, input);
            // Finalize Operation
            document.close();
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Exception thrown when converting text to pdf at: " + e);
        }
    }

    // All pdf formatting should occur within. The document is preserved without a return.
    private void format(Document document, String input) {
        document.add(new Paragraph(input));
    }

    public byte[] convertPdftoImage(byte[] input) {
        // Check if input is valid
        if (input.length == 0)
            throw new RuntimeException("Exception thrown when converting pdf to image. Pdf received was empty.");
        try {
            // Load to parsable PDF
            PDDocument document = PDDocument.load(input);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            final int PDF_PAGES = document.getNumberOfPages();
            // Validate PDF length
            if (PDF_PAGES <= 0)
                throw new RuntimeException("Exception thrown when converting pdf to image. Pdf received has no pages or is corrupted.");
            // Setup values for conversion
            final int DPI = 300;
            final ImageType IMAGE_COLOUR_PROFILE = ImageType.RGB;
            // Setup output values
            ByteArrayOutputStream imageOutput;
            ByteArrayOutputStream zipOutput = new ByteArrayOutputStream();
            ZipOutputStream zipInMemoryStream = new ZipOutputStream(zipOutput);
            int page;
            ZipEntry entry;
            for (page = 0; page < PDF_PAGES; ++page) {
                // Create image
                System.out.println("Scanning image " + page + "...");
                // Re-initialise stream for each image
                imageOutput = new ByteArrayOutputStream();
                // Scan the image in a buffer according to the page, DPI and colour profile.
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_COLOUR_PROFILE);
                // Create the image in memory in png format
                ImageIO.write(bufferedImage, "PNG", imageOutput);
                // Zipping
                System.out.println("zipping " + page + "...");
                // Set name and size of file for zipping
                entry = new ZipEntry("page " + (page + 1) + ".png");
                entry.setSize(imageOutput.toByteArray().length);
                // Rotate entries
                zipInMemoryStream.putNextEntry(entry);
                // Write the image file in memory as a zip file
                zipInMemoryStream.write(imageOutput.toByteArray());
            }
            // Cleanup. Close pdf document, entry object and zip output.
            document.close();
            zipInMemoryStream.closeEntry();
            zipInMemoryStream.close();
            // Return
            return zipOutput.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Exception thrown when converting pdf to image at: " + e);
        }
    }
}

