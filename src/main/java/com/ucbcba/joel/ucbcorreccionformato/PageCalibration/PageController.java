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
    public List<Integer> getPages(@PathVariable String fileName)  {
        List<Integer> pages = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            PdfDocument document = new PdfDocument(pdfdocument);
            pages.add(document.getCoverPage());
            pages.add(document.getGeneralIndexStartPage());
            pages.add(document.getGeneralIndexEndPage());
            pages.add(document.getFigureIndexStartPage());
            pages.add(document.getFigureIndexEndPage());
            pages.add(document.getTableIndexStartPage());
            pages.add(document.getTableIndexEndPage());
            pages.add(document.getBibliographyStartPage());
            pages.add(document.getBibliographyEndPage());
            pages.add(document.getAnnexesStartPage());
            pages.add(document.getAnnexesEndPage());
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
