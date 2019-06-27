package com.ucbcba.joel.ucbcorreccionformato.page_calibration;

import com.ucbcba.joel.ucbcorreccionformato.upload_download_file.service.FileStorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/api/getPages/{fileName:.+}")
    public PdfDocumentResponse getPages(@PathVariable String fileName)  {
        PdfDocumentResponse resp = null;
        Logger logger = Logger.getLogger("com.ucbcba.joel.ucbcorreccionformato.page_calibration.PageController");
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        try {
            String dirPdfFile = resource.getFile().getAbsolutePath();
            PDDocument pdfdocument = PDDocument.load(new File(dirPdfFile));
            PdfDocumentDetector document = new PdfDocumentDetector(pdfdocument);
            resp = document.getPdfPages();
            pdfdocument.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"No se pudo analizar el archivo PDF", e);
        }
        return resp;
    }
}
