package com.ucbcba.joel.ucbcorreccionformato.format_control.format_formal_aspects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FormalAspectsController {
    @PostMapping("/api/basicFormat/{fileName:.+}")
    public List<FormalAspectsResponse> getFormalAspects(@PathVariable String fileName) {
        List<FormalAspectsResponse> formalAspectsResponses = new ArrayList<>();
        String dirPdfFile = "uploads/"+fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load( new File(dirPdfFile) );
            FormalAspectsDetector formatErrorDetector = new FormalAspectsDetector(pdfdocument);
            formalAspectsResponses = formatErrorDetector.getFormalAspectsResponses();
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formalAspectsResponses;
    }
}
