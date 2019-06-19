package com.ucbcba.joel.ucbcorreccionformato.page_calibration.finders;

import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PagesFinder {

    private PDDocument pdfdocument;
    private WordsFinder wordsFinder;

    public PagesFinder(PDDocument pdfdocument){
        this.pdfdocument = pdfdocument;
        this.wordsFinder = new WordsFinder(pdfdocument);
    }

    public boolean isTheCoverInThisPage(int page) throws IOException {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("BOLIVIANA");
        keyWords.add("REGIONAL");
        keyWords.add("Departamento");
        keyWords.add("Carrera");
        keyWords.add("– Bolivia");
        int keyWordsFound = 0;
        for(String keyWord: keyWords){
            if(wordsFinder.isTheWordInThePage(page,keyWord)){
                keyWordsFound++;
            }
        }
        return keyWordsFound>=3;
    }

    public int getCoverPage() throws IOException {
        int page=1;
        if( page <= pdfdocument.getNumberOfPages() && isTheCoverInThisPage(page)) {
            return page;
        }
        return 0;
    }


    public boolean isTheIndexSectionInThisPage(int page) throws IOException {
        List<String> keyWords = new ArrayList<>();
        keyWords.add(".....");
        keyWords.add("……");
        int keyWordsFound = 0;
        for(String keyWord: keyWords){
            if(wordsFinder.isTheWordInThePage(page,keyWord)){
                keyWordsFound++;
            }
        }
        return keyWordsFound>=1;
    }

    public int getGeneralIndexStartPage() throws IOException {
        for(int page=1;page<=pdfdocument.getNumberOfPages();page++){
            if ( isTheIndexSectionInThisPage(page) ){
                return page;
            }
        }
        return 0;
    }

    public int getLastIndexPage(int generalIndexPageStart) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        for (int page = generalIndexPageStart; page <= pdfdocument.getNumberOfPages(); page++) {
            if ( isTheIndexSectionInThisPage(page) ){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public boolean isTheFigureTableIndexInThisPage(int page) throws IOException {
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> lineWords = getterWordLines.getSingleLinesWithoutAnyNumeration(page);
        if(lineWords.size() >= 2){
            String line = lineWords.get(1).toString();
            if(line.contains("Figura") || line.contains("FIGURA") || line.contains("Tabla") || line.contains("TABLA")){
                return true;
            }

        }
        return false;
    }

    public boolean isTheFigureIndexInThisPage(int page) throws IOException {
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> lineWords = getterWordLines.getSingleLinesWithoutAnyNumeration(page);
        if(lineWords.size() >= 2){
            String line = lineWords.get(1).toString();
            if(line.contains("Figura") || line.contains("FIGURA")){
                return true;
            }

        }
        return false;
    }

    public boolean isTheTableIndexInThisPage(int page) throws IOException {
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> lineWords = getterWordLines.getSingleLinesWithoutAnyNumeration(page);
        if(lineWords.size() >= 2){
            String line = lineWords.get(1).toString();
            if(line.contains("Tabla") || line.contains("TABLA")){
                return true;
            }
        }
        return false;
    }


    public int getGeneralIndexEndPage(int generalIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        resp = generalIndexPageStart;
        for (int page = generalIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheIndexSectionInThisPage(page) && !isTheFigureTableIndexInThisPage(page)){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public int getFigureIndexStartPage(int generalIndexPageEnd, int lastIndexPage) throws IOException {
        int resp = 0;
        if(generalIndexPageEnd == 0){
            return resp;
        }
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheFigureIndexInThisPage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getFigureIndexEndPage(int figureIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(figureIndexPageStart == 0){
            return resp;
        }
        for (int page = figureIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheFigureIndexInThisPage(page) ){
                resp = page;
            }
        }
        return resp;
    }

    public int getTableIndexStartPage(int generalIndexPageEnd, int lastIndexPage) throws IOException {
        int resp = 0;
        if(generalIndexPageEnd == 0){
            return resp;
        }
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheTableIndexInThisPage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getTableIndexEndPage(int tableIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(tableIndexPageStart == 0){
            return resp;
        }
        for (int page = tableIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheTableIndexInThisPage(page) ){
                resp = page;
            }
        }
        return resp;
    }


    public boolean isTheBibliographyInThePage(int page) throws IOException {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("BIBLIOGRAFIA");
        keyWords.add("Bibliografia");
        int keyWordsFound = 0;
        for(String keyWord: keyWords){
            if(wordsFinder.isTheWordInThePageIgnoringAccentMark(page,keyWord)){
                keyWordsFound++;
            }
        }
        return keyWordsFound>=1;
    }

    public int getBibliographyStartPage() throws IOException {
        int resp = 0;
        for (int page = pdfdocument.getNumberOfPages(); page >= 1; page--) {
            if ( isTheBibliographyInThePage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getBibliographyEndPage(int biographyPageStart, int annexedPageStart){
        int resp = 0;
        if (biographyPageStart == resp){
            return resp;
        }
        if (annexedPageStart == 0){
            return pdfdocument.getNumberOfPages();
        }
        resp = annexedPageStart-1;
        return resp;
    }


    public boolean isTheAnnexesStartInThePage(int page) throws IOException {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("Anexo 1 ");
        keyWords.add("ANEXO 1 ");
        keyWords.add("Anexo 1:");
        keyWords.add("ANEXO 1:");
        keyWords.add("Anexo 1,");
        keyWords.add("ANEXO 1,");
        keyWords.add("Anexo 1.");
        keyWords.add("ANEXO 1.");
        keyWords.add("ANEXO A");
        keyWords.add("Anexo A");
        int keyWordsFound = 0;
        for(String keyWord: keyWords){
            if(wordsFinder.isTheWordInThePage(page,keyWord)){
                keyWordsFound++;
            }
        }
        return keyWordsFound>=1;
    }


    public int getAnnexesStartPage(int bibliographyStartPage) throws IOException {
        int resp = 0;
        for (int page = pdfdocument.getNumberOfPages(); page >= bibliographyStartPage+1; page--) {
            if ( isTheAnnexesStartInThePage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getAnnexesEndPage(int annexedPageStart){
        int resp = 0;
        if (annexedPageStart == resp){
            return resp;
        }
        return pdfdocument.getNumberOfPages();
    }
}
