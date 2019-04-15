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
            pages.add(document.getGeneralIndexPageStart());
            pages.add(document.getGeneralIndexPageEnd());
            pages.add(document.getFigureIndexPageStart());
            pages.add(document.getFigureIndexPageEnd());
            pages.add(document.getTableIndexPageStart());
            pages.add(document.getTableIndexPageEnd());
            pages.add(document.getBiographyPageStart());
            pages.add(document.getBiographyPageEnd());
            pages.add(document.getAnnexedPageStart());
            pages.add(document.getAnnexedPageEnd());
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
