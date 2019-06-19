package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format;

import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.FormatErrorResponse;
import com.ucbcba.joel.ucbcorreccionformato.upload_download_file.service.FileStorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FormatErrorController {

    private final AtomicLong idHighlights = new AtomicLong();
    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/api/hightlight/errors/{fileName:.+}")
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
            , @RequestParam(value="annexesEndPage") Integer annexesEndPage
            , @RequestParam(value="bibliograhyType") String bibliograhyType)  {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        Logger logger = Logger.getLogger("com.ucbcba.joel.ucbcorreccionformato.control_format_rules.general_format.FormatErrorController");
        try {
            String dirPdfFile = resource.getFile().getAbsolutePath();
            PDDocument pdfdocument = PDDocument.load(new File(dirPdfFile));
            int indexEndPage = getIndexEndPage(generalIndexEndPage, figureIndexEndPage, tableIndexEndPage);
            FormatErrorDetector formatErrorDetector = new FormatErrorDetector(pdfdocument,idHighlights);
            formatErrors.addAll(formatErrorDetector.getCoverPageFormatErrors(coverPage));
            formatErrors.addAll(formatErrorDetector.getGeneralIndexFormatErrors(generalIndexStartPage,generalIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getFigureIndexFormatErrors(figureIndexStartPage,figureIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getTableIndexFormatErrors(tableIndexStartPage,tableIndexEndPage));
            formatErrors.addAll(formatErrorDetector.getPageNumerationFormatErrors(indexEndPage,annexesStartPage,annexesEndPage));
            formatErrors.addAll(formatErrorDetector.getFigureTableFormatErrors(indexEndPage,bibliographyStartPage));
            formatErrors.addAll(formatErrorDetector.getEnglishWordsFormatErrors(indexEndPage,bibliographyStartPage));
            formatErrors.addAll(formatErrorDetector.getBibliographyFormatErrors(bibliographyStartPage,bibliographyEndPage,bibliograhyType));
            pdfdocument.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"No se pudo analziar el archivo PDF", e);
        }
        return formatErrors;
    }
    
    private int getIndexEndPage(Integer generalIndexPageEnd, Integer figureIndexPageEnd, Integer tableIndexPageEnd) {
        int indexPageEnd;
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
