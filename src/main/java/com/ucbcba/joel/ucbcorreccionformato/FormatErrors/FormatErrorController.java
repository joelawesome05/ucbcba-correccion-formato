package com.ucbcba.joel.ucbcorreccionformato.FormatErrors;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
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
public class FormatErrorController {

    @RequestMapping("/api/formatErrors/{fileName:.+}")
    public List<FormatErrorReport> greeting(@PathVariable String fileName, @RequestParam(value="coverPage") Integer coverPage
            , @RequestParam(value="generalIndexPageStart") Integer generalIndexPageStart, @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd, @RequestParam(value="biographyPageStart") Integer biographyPage
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument);
            formatErrorDetector.analyzeFormatPdf(coverPage,generalIndexPageStart,generalIndexPageEnd,figureTableIndexPageEnd,biographyPage,annexedPage);
            formatErrors = formatErrorDetector.getFormatErrorReports();
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }
}
