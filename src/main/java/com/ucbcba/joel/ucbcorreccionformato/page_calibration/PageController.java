package com.ucbcba.joel.ucbcorreccionformato.page_calibration;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class PageController {
    @PostMapping("/api/getPages/{fileName:.+}")
    public PdfDocumentResponse getPages(@PathVariable String fileName)  {
        PdfDocumentResponse resp = null;
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            PdfDocumentDetector document = new PdfDocumentDetector(pdfdocument);
            resp = document.getPdfPages();
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
