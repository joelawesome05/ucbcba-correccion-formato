package com.ucbcba.joel.ucbcorreccionformato.PageCalibration;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PageController {
    @RequestMapping("/api/getPages/{fileName:.+}")
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
