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
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FormatErrorController {

    private final AtomicLong idHighlights = new AtomicLong();

    @RequestMapping("/api/coverpage/errors/{fileName:.+}")
    public List<FormatErrorReport> getCoverPageFormatErrors(@PathVariable String fileName, @RequestParam(value="coverPage") Integer coverPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
    public List<FormatErrorReport> getIndexPageFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageStart") Integer generalIndexPageStart
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
    public List<FormatErrorReport> getFigureIndexFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureIndexPageStart") Integer figureIndexPageStart
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
    public List<FormatErrorReport> getTableIndexFormatErrors(@PathVariable String fileName
            , @RequestParam(value="tableIndexPageStart") Integer tableIndexPageStart
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
    public List<FormatErrorReport> getPageNumerationFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
    public List<FormatErrorReport> getFigureTableFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureIndexPageEnd") Integer figureIndexPageEnd
            , @RequestParam(value="tableIndexPageEnd") Integer tableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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

    @RequestMapping("/api/biography/errors/{fileName:.+}")
    public List<FormatErrorReport> getBiographyFormatErrors(@PathVariable String fileName
            , @RequestParam(value="biographyPageStart") Integer biographyPage
            , @RequestParam(value="biographyPageEnd") Integer biographyPageEnd)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getBiographyFormatErrors(biographyPage,biographyPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/figure/errors/{fileName:.+}")
    public List<FormatErrorReport> getFigureFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
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
