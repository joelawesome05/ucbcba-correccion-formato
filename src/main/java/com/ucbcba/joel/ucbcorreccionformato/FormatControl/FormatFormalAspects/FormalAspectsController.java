package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatFormalAspects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FormalAspectsController {
    @RequestMapping("/api/basicFormat/{fileName:.+}")
    public List<FormalAspectsResponse> getBasicMisstakes(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage) {
        List<FormalAspectsResponse> formalAspectsResponses = new ArrayList<>();
        int indexPageEnd = getIndexEndPage(generalIndexPageEnd, figureIndexPageEnd, tableIndexPageEnd);
        String dirPdfFile = "uploads/"+fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load( new File(dirPdfFile) );
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
