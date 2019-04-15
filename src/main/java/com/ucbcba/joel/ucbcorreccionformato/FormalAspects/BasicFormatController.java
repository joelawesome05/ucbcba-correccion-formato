package com.ucbcba.joel.ucbcorreccionformato.FormalAspects;

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
public class BasicFormatController {
    @RequestMapping("/api/basicFormat/{fileName:.+}")
    public List<BasicFormatReport> getBasicMisstakes(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage) {
        List<BasicFormatReport> basicFormatReports = new ArrayList<>();
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
        String dirPdfFile = "uploads/"+fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load( new File(dirPdfFile) );
            BasicFormatDetector formatErrorDetector = new BasicFormatDetector(pdfdocument);
            formatErrorDetector.analyzeBasicFormat(indexPageEnd,annexedPage);
            basicFormatReports = formatErrorDetector.getBasicFormatReports();
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return basicFormatReports;
    }
}
