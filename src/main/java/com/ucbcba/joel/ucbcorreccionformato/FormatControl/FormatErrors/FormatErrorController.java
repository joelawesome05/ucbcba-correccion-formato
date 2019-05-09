package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse.FormatErrorResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FormatErrorController {

    private final AtomicLong idHighlights = new AtomicLong();


    @RequestMapping("/api/hightlight/errors/{fileName:.+}")
    public List<FormatErrorResponse> getHightlightErrors(@PathVariable String fileName
            ,@RequestParam(value="coverPage") Integer coverPage
            , @RequestParam(value="generalIndexStartPage") Integer generalIndexStartPage
            , @RequestParam(value="generalIndexEndPage") Integer generalIndexEndPage
            , @RequestParam(value="figureIndexStartPage") Integer figureIndexStartPage
            , @RequestParam(value="figureIndexEndPage") Integer figureIndexEndPage
            , @RequestParam(value="tableIndexStartPage") Integer tableIndexStartPage
            , @RequestParam(value="tableIndexEndPage") Integer tableIndexEndPage
            , @RequestParam(value="bibliographyStartPage") Integer bibliographyStartPage
            , @RequestParam(value="bibliographyEndPage") Integer bibliographyEndPage
            , @RequestParam(value="annexesStartPage") Integer annexesStartPage
            , @RequestParam(value="annexesEndPage") Integer annexesEndPage)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            if(generalIndexEndPage==null){
                generalIndexEndPage = 0;
            }
            if(figureIndexEndPage==null){
                figureIndexEndPage = 0;
            }
            if(tableIndexEndPage==null){
                tableIndexEndPage = 0;
            }
            int indexEndPage = getIndexPageEnd(generalIndexEndPage, figureIndexEndPage, tableIndexEndPage);
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors.addAll(formatErrorDetector.getCoverPageFormatErrors(coverPage));
            formatErrors.addAll(formatErrorDetector.getGeneralIndexFormatErrors(generalIndexStartPage,generalIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getFigureIndexFormatErrors(figureIndexStartPage,figureIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getTableIndexFormatErrors(tableIndexStartPage,tableIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getPageNumerationFormatErrors(indexEndPage,annexesStartPage,annexesEndPage));
            formatErrors.addAll(formatErrorDetector.getFigureTableFormatErrors(indexEndPage,bibliographyStartPage));
            formatErrors.addAll(formatErrorDetector.getEnglishWordsFormatErrors(indexEndPage,bibliographyStartPage));
            formatErrors.addAll(formatErrorDetector.getBibliographyFormatErrors(bibliographyStartPage,bibliographyEndPage));
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formatErrors;
    }




    private int getIndexPageEnd(Integer generalIndexPageEnd, Integer figureIndexPageEnd, Integer tableIndexPageEnd) {
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
