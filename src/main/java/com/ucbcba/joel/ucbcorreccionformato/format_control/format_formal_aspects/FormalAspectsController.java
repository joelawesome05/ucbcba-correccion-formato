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
    public List<FormalAspectsResponse> getFormalAspects(@PathVariable String fileName
            , @RequestParam(value="generalIndexEndPage") Integer generalIndexEndPage
            , @RequestParam(value="figureIndexEndPage") Integer figureIndexEndPage
            , @RequestParam(value="tableIndexEndPage") Integer tableIndexEndPage
            , @RequestParam(value="annexesStartPage") Integer annexedPage) {
        List<FormalAspectsResponse> formalAspectsResponses = new ArrayList<>();
        String dirPdfFile = "uploads/"+fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load( new File(dirPdfFile) );
            if(annexedPage==0){
                annexedPage = pdfdocument.getNumberOfPages();
            }
            int indexPageEnd = getIndexEndPage(generalIndexEndPage, figureIndexEndPage, tableIndexEndPage);
            FormalAspectsDetector formatErrorDetector = new FormalAspectsDetector(pdfdocument);
            formalAspectsResponses = formatErrorDetector.getFormalAspectsResponses(indexPageEnd,annexedPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formalAspectsResponses;
    }

    private int getIndexEndPage(Integer generalIndexPageEnd, Integer figureIndexPageEnd, Integer tableIndexPageEnd) {
        int indexPageEnd = 0;
        if (generalIndexPageEnd > figureIndexPageEnd) {
            if (generalIndexPageEnd > tableIndexPageEnd) {
                indexPageEnd = generalIndexPageEnd;
            } else {
                indexPageEnd = tableIndexPageEnd;
            }
        } else if (figureIndexPageEnd > tableIndexPageEnd) {
            indexPageEnd = figureIndexPageEnd;
        } else {
            indexPageEnd = tableIndexPageEnd;
        }
        return indexPageEnd;
    }
}
