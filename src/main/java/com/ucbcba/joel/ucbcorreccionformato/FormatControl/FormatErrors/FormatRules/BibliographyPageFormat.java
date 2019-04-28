package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.Others.Bibliographies.PatternBibliographyReferences;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse.*;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibliographyPageFormat implements  FormatRule {

    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private int biographyPageStart;

    public BibliographyPageFormat(PDDocument pdfdocument, AtomicLong idHighlights, int biographyPageStart){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        this.biographyPageStart = biographyPageStart;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int page) throws IOException {
        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        float pageWidth = pdfdocument.getPage(page-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(page-1).getMediaBox().getHeight();

        Format biographyTitle = new TittleFormat(12,"Izquierdo",pageWidth,true,"BIBLIOGRAFÍA");


        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<List<WordsProperties>> biography =   getterWordLines.getBiographyLines(page);
        if (biographyPageStart == page){
            if (!biography.isEmpty()){
                if(!biography.get(0).isEmpty()) {
                    List<String> formatErrorscomments = biographyTitle.getFormatErrorComments(biography.get(0).get(0));
                    reportFormatErrors(formatErrorscomments, biography.get(0).get(0), formatErrors, pageWidth, pageHeight, page);
                    biography.remove(0);
                }
            }
        }

        for(List<WordsProperties> bibliographyElement:biography){
            List<String> formatErrorscomments = new ArrayList<>();
            String bibliographyElementString = getString(bibliographyElement);
            PatternBibliographyReferences pattern = getPattern(bibliographyElementString);
            if (pattern!=null) {
                Matcher matcher = pattern.getMatcher(bibliographyElementString);
                if (!matcher.find()) {
                    formatErrorscomments.add("Revisar la guía de referencias en "+pattern.getName());
                }
            }else{
                formatErrorscomments.add("La referencia siga las normas de presentación según la Guía");
            }
            reportFormatErrors(formatErrorscomments, bibliographyElement, formatErrors, pageWidth, pageHeight, page);
        }


        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page));
        }
    }

    private String getString(List<WordsProperties> bibliography){
        StringBuilder bibliographyString = new StringBuilder();
        for(WordsProperties line:bibliography){
            bibliographyString.append(line.toString());
        }
        return bibliographyString.toString();
    }

    private void reportFormatErrors(List<String> comments, List<WordsProperties> ref_bibliografy, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) throws IOException {
        if (comments.size() != 0) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, ref_bibliografy, pageWidth, pageHeight, page));
        }
    }


    public PatternBibliographyReferences getPattern(String lineWord){

        Pattern discussion_list_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^<]*<[^>]+>[^<]*<[^>]+>[^(]*\\(fecha de consulta.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences discussion_list = new PatternBibliographyReferences("Listas de discusión",discussion_list_bibliography);

        Pattern page_web_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:[^<]*<[^>]+>[^,]*,[^(]*\\(fecha de consulta.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences page_web = new PatternBibliographyReferences("Página web",page_web_bibliography);

        Pattern email_bibliography = Pattern.compile("[^(]+\\([^)]+\\)[^(]*\\([^)]+\\)\\.[^“]*“[^”]+”\\.[^(]*\\([^)]+\\)[^(]*\\(fecha del mensaje.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences email = new PatternBibliographyReferences("Correo electrónico",email_bibliography);

        Pattern radio_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^(]*\\([^)]+\\).+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences radio = new PatternBibliographyReferences("Programa de radio ",radio_bibliography);

        Pattern cd_rom_dvd_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences cd_rom_dvd = new PatternBibliographyReferences("Libro en soporte CD-ROM/DVD",cd_rom_dvd_bibliography);

        Pattern thesis_bibliography = Pattern.compile("[^(]+\\([^)]+\\)\\..+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences thesis = new PatternBibliographyReferences("Tesis/Trabajo de titulación",thesis_bibliography);

        Pattern article_magazine_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:.+Año.+N.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences article_magazine = new PatternBibliographyReferences("Artículo de revista",article_magazine_bibliography);

        Pattern chapter_book_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences chapter_book = new PatternBibliographyReferences("Capítulo de libro",chapter_book_bibliography);

        Pattern article_newspaper_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:[^(]+\\([^)]+\\).+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences article_newspaper = new PatternBibliographyReferences("Artículo de periódico",article_newspaper_bibliography);

        Pattern conference_artworks_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences conference_artworks = new PatternBibliographyReferences("Congreso/Conferencia",conference_artworks_bibliography);

        Pattern movies_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences movies = new PatternBibliographyReferences("Película",movies_bibliography);

        Pattern book_bibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences book = new PatternBibliographyReferences("Libro",book_bibliography);


        if (lineWord.contains("http")){
            if( lineWord.contains("@")){
                return discussion_list;
            }else{
                return page_web;
            }
        }

        if (lineWord.contains("@")){
            return email;
        }

        if (lineWord.contains("FM,") || lineWord.contains("AM,")){
            return radio;
        }

        if (lineWord.contains("CD-ROM") || lineWord.contains("DVD")){
            return cd_rom_dvd;
        }

        if (lineWord.contains("licenciatura") || lineWord.contains("Licenciatura") || lineWord.contains("titulación") || lineWord.contains("Titulación")){
            return thesis;
        }

        if (lineWord.contains("En:")){
            if( lineWord.contains("N°") || lineWord.contains(", Año")){
                return article_magazine;
            }
            Matcher matcher = chapter_book_bibliography.matcher(lineWord);
            if (matcher.find()){
                return chapter_book;
            }
            matcher = article_newspaper_bibliography.matcher(lineWord);
            if (matcher.find()){
                return article_newspaper;
            }
            matcher = conference_artworks_bibliography.matcher(lineWord);
            if (matcher.find()){
                return conference_artworks;
            }
            return null;
        }

        if (lineWord.contains(":")){
            if( lineWord.contains("“")){
                return movies;
            }else{
                return book;
            }
        }

        return null;
    }
}
