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
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrorDetector.analyzeFormatPdf(coverPage,generalIndexPageStart,generalIndexPageEnd,figureTableIndexPageEnd,biographyPage,annexedPage);
            formatErrors = formatErrorDetector.getFormatErrorReports();
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

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

    @RequestMapping("/api/figuretableindex/errors/{fileName:.+}")
    public List<FormatErrorReport> getFigureTableIndexFormatErrors(@PathVariable String fileName
            , @RequestParam(value="generalIndexPageEnd") Integer generalIndexPageEnd
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getFigureTableIndexFormatErrors(generalIndexPageEnd,figureTableIndexPageEnd);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/numeration/errors/{fileName:.+}")
    public List<FormatErrorReport> getPageNumerationFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getPageNumerationFormatErrors(figureTableIndexPageEnd,annexedPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }

    @RequestMapping("/api/biography/errors/{fileName:.+}")
    public List<FormatErrorReport> getBiographyFormatErrors(@PathVariable String fileName
            , @RequestParam(value="biographyPageStart") Integer biographyPage
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getBiographyFormatErrors(biographyPage,annexedPage);
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

    @RequestMapping("/api/table/errors/{fileName:.+}")
    public List<FormatErrorReport> getTableFormatErrors(@PathVariable String fileName
            , @RequestParam(value="figureTableIndexPageEnd") Integer figureTableIndexPageEnd
            , @RequestParam(value="annexedPageStart") Integer annexedPage)  {
        List<FormatErrorReport> formatErrors = new ArrayList<>();
        String dirPdfFile = "uploads/" + fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load(new File(dirPdfFile));
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors = formatErrorDetector.getTableFormatErrors(figureTableIndexPageEnd,annexedPage);
            pdfdocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatErrors;
    }
}
