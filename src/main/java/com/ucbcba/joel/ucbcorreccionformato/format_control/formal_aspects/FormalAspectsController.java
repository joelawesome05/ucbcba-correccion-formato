package com.ucbcba.joel.ucbcorreccionformato.format_control.formal_aspects;

import com.ucbcba.joel.ucbcorreccionformato.upload_download_file.service.FileStorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FormalAspectsController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/api/basicFormat/{fileName:.+}")
    public List<FormalAspectsResponse> getFormalAspects(@PathVariable String fileName) {
        List<FormalAspectsResponse> formalAspectsResponses = new ArrayList<>();
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        Logger logger = Logger.getLogger("com.ucbcba.joel.ucbcorreccionformato.control_format_rules.formal_aspects.FormalAspectsController");
        try {
            String dirPdfFile = resource.getFile().getAbsolutePath();
            PDDocument pdfdocument = PDDocument.load( new File(dirPdfFile) );
            FormalAspectsDetector formatErrorDetector = new FormalAspectsDetector(pdfdocument);
            formalAspectsResponses = formatErrorDetector.getFormalAspectsResponses();
            pdfdocument.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"No se pudo analziar el archivo PDF", e);
        }
        return formalAspectsResponses;
    }
}
