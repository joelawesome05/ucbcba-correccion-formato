package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography.Bibliography;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography.BibliographyFactory;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BibliographyPageFormat implements PageFormatRule {

    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private int biographyPageStart;
    private Bibliography bibliography;

    public BibliographyPageFormat(PDDocument pdfdocument, AtomicLong idHighlights, int biographyPageStart, String bibliograhyType){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.biographyPageStart = biographyPageStart;
        BibliographyFactory bibliographyFactory = new BibliographyFactory();
        this.bibliography = bibliographyFactory.getBibliography(bibliograhyType);
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        if(bibliography!=null) {
            float pageWidth = pdfdocument.getPage(page - 1).getMediaBox().getWidth();
            float pageHeight = pdfdocument.getPage(page - 1).getMediaBox().getHeight();

            GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
            double lineSpacing = getterWordLines.getLineSpacingBibliography(biographyPageStart);
            List<SingleLine> singleLines = getterWordLines.getSingleLinesWithoutPageNumeration(page);
            controlTittle(page, formatErrors, pageWidth, pageHeight, singleLines);

            List<WordLine> bibliographyLines = getterWordLines.getBibliographyLines(singleLines, lineSpacing);
            for (WordLine currentBibliography : bibliographyLines) {
                List<String> formatErrorscomments = bibliography.getFormatErrors(currentBibliography);
                reportFormatErrors(formatErrorscomments, currentBibliography, formatErrors, pageWidth, pageHeight, page);
            }
        }
        return formatErrors;
    }

    private void controlTittle(int page, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, List<SingleLine> singleLines) {
        Format biographyTitle = new TittleFormat(12,"Izquierdo",pageWidth,true,false,"BIBLIOGRAFÍA");
        if (biographyPageStart == page && !singleLines.isEmpty()){
            List<SingleLine> tittleLine = new ArrayList<>();
            tittleLine.add(singleLines.get(0));
            WordLine tittle = new WordLine(tittleLine);
            List<String> formatErrorscomments = biographyTitle.getFormatErrorComments(tittle);
            reportFormatErrors(formatErrorscomments,tittle, formatErrors, pageWidth, pageHeight, page);
            singleLines.remove(0);
        }
    }

    private void reportFormatErrors(List<String> comments, WordLine words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            ReportFormatError reporter = new ReportFormatError(idHighlights);
            formatErrors.add(reporter.reportFormatError(comments, words, pageWidth, pageHeight, page,"bibliografia"));
        }
    }

}
