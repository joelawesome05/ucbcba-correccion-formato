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

    @RequestMapping("/api/coverpage/errors/{fileName:.+}")
    public List<FormatErrorResponse> getCoverPageFormatErrors(@PathVariable String fileName, @RequestParam(value="coverPage") Integer coverPage)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getCoverPageFormatErrors(coverPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/indexpage/errors/{fileName:.+}")
    public List<FormatErrorResponse> getIndexPageFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageStart") Integer generalIndexPageStart
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getGeneralIndexFormatErrors(generalIndexPageStart,generalIndexPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/figureindex/errors/{fileName:.+}")
    public List<FormatErrorResponse> getFigureIndexFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureIndexPageStart") Integer figureIndexPageStart
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getFigureIndexFormatErrors(figureIndexPageStart,figureIndexPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/tableindex/errors/{fileName:.+}")
    public List<FormatErrorResponse> getTableIndexFormatErrors(@PathVariable String fileName
            , @RequestParam(value="tableIndexPageStart") Integer tableIndexPageStart
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getTableIndexFormatErrors(tableIndexPageStart,tableIndexPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/numeration/errors/{fileName:.+}")
    public List<FormatErrorResponse> getPageNumerationFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        int indexPageEnd = getIndexPageEnd(generalIndexPageEnd, figureIndexPageEnd, tableIndexPageEnd);
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getPageNumerationFormatErrors(indexPageEnd,annexedPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/figuretable/errors/{fileName:.+}")
    public List<FormatErrorResponse> getFigureTableFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        int indexPageEnd = getIndexPageEnd(generalIndexPageEnd, figureIndexPageEnd, tableIndexPageEnd);
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getFigureTableFormatErrors(indexPageEnd,annexedPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/englishwords/errors/{fileName:.+}")
    public List<FormatErrorResponse> getEnglishWordsFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="biographyPageStart") Integer biographyPageStart)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        int indexPageEnd = getIndexPageEnd(generalIndexPageEnd, figureIndexPageEnd, tableIndexPageEnd);
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getEnglishWordsFormatErrors(indexPageEnd,biographyPageStart);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/biography/errors/{fileName:.+}")
    public List<FormatErrorResponse> getBibliographyFormatErrors(@PathVariable String fileName
            , @RequestParam(value="biographyPageStart") Integer biographyPage
            , @RequestParam(value="biographyPageEnd") Integer biographyPageEnd)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getBibliographyFormatErrors(biographyPage,biographyPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/figure/errors/{fileName:.+}")
    public List<FormatErrorResponse> getFigureFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getFigureFormatErrors(figureTableIndexPageEnd,annexedPage);
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
