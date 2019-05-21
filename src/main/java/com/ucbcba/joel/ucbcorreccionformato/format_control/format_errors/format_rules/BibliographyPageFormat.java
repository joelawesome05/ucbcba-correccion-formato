package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.others.bibliographies.PatternBibliographyReferences;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;
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

        Format biographyTitle = new TittleFormat(12,"Izquierdo",pageWidth,true,"BIBLIOGRAFIA");


        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        double lineSpacing = getterWordLines.getLineSpacingBibliography(biographyPageStart);
        List<List<WordsProperties>> biography =   getterWordLines.getBibliographyLines(page,lineSpacing);
        if (biographyPageStart == page && !biography.isEmpty() && !biography.get(0).isEmpty()){
            List<String> formatErrorscomments = biographyTitle.getFormatErrorComments(biography.get(0).get(0));
            reportFormatErrors(formatErrorscomments, biography.get(0).get(0), formatErrors, pageWidth, pageHeight, page);
            biography.remove(0);
        }
        for(List<WordsProperties> bibliographyElement:biography){
            List<String> formatErrorscomments = new ArrayList<>();
            String bibliographyElementString = getString(bibliographyElement);
            PatternBibliographyReferences pattern = getPattern(bibliographyElementString);
            if (pattern!=null) {
                Matcher matcher = pattern.getMatcher(bibliographyElementString);
                if (!matcher.find()) {
                    formatErrorscomments.add(pattern.getName()+" siga las normas de presentación según la Guía");
                }
            }else{
                formatErrorscomments.add("La referencia bibliográfica siga las normas de presentación según la Guía");
            }
            reportFormatErrors(formatErrorscomments, bibliographyElement, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void reportFormatErrors(List<String> comments, WordsProperties words, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, words, pageWidth, pageHeight, page,"bibliografia"));
        }
    }

    private String getString(List<WordsProperties> bibliography){
        StringBuilder bibliographyString = new StringBuilder();
        for(WordsProperties line:bibliography){
            bibliographyString.append(line.toString());
        }
        return bibliographyString.toString();
    }

    private void reportFormatErrors(List<String> comments, List<WordsProperties> refBibliografy, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, int page) {
        if (!comments.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFormatError(comments, refBibliografy, pageWidth, pageHeight, page,"bibliografia"));
        }
    }


    public PatternBibliographyReferences getPattern(String lineWord){
        Pattern discussionListBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^<]*<[^>]+>[^<]*<[^>]+>[^(]*\\([^f]*fecha.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences discussionList = new PatternBibliographyReferences("Listas de discusión",discussionListBibliography);
        Pattern pageWebBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”\\.[^E]*En:[^<]*<[^>]+>[^,]*,[^(]*\\([^f]*fecha.+|[^\"]*\"[^\"]+\"\\.[^E]*En:[^<]*<[^>]+>[^,]*,[^(]*\\([^f]*fecha.+)", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences pageWeb = new PatternBibliographyReferences("Página web",pageWebBibliography);
        Pattern emailBibliography = Pattern.compile("[^(]+\\([^)]+\\)[^(]*\\([^)]+\\)\\.[^“]*“[^”]+”\\.[^(]*\\([^)]+\\)[^(]*\\([^f]*fecha.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences email = new PatternBibliographyReferences("Correo electrónico",emailBibliography);
        Pattern radioBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^(]*\\([^)]+\\).+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences radio = new PatternBibliographyReferences("Programa de radio ",radioBibliography);
        Pattern cdRomDvdBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences cdRomDvd = new PatternBibliographyReferences("Libro en soporte CD-ROM/DVD",cdRomDvdBibliography);
        Pattern thesisBibliography = Pattern.compile("[^(]+\\([^)]+\\)\\..+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences thesis = new PatternBibliographyReferences("Tesis/Trabajo de titulación",thesisBibliography);
        Pattern articleMagazineBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:.+Año.+N.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences articleMagazine = new PatternBibliographyReferences("Artículo de revista",articleMagazineBibliography);
        Pattern chapterBookBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences chapterBook = new PatternBibliographyReferences("Capítulo de libro",chapterBookBibliography);
        Pattern articleNewspaperBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:[^(]+\\([^)]+\\).+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences articleNewspaper = new PatternBibliographyReferences("Artículo de periódico",articleNewspaperBibliography);
        Pattern conferenceArtworksBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^E]*En:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences conferenceArtworks = new PatternBibliographyReferences("Congreso/Conferencia",conferenceArtworksBibliography);
        Pattern moviesBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^“]*“[^”]+”\\.[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences movies = new PatternBibliographyReferences("Película",moviesBibliography);
        Pattern bookBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+", Pattern.CASE_INSENSITIVE);
        PatternBibliographyReferences book = new PatternBibliographyReferences("Libro",bookBibliography);

        if (lineWord.contains("http")){
            if( lineWord.contains("@")){
                return discussionList;
            }else{
                return pageWeb;
            }
        }

        if (lineWord.contains("@")){
            return email;
        }

        if (lineWord.contains(":")){
            Matcher matcher = bookBibliography.matcher(lineWord);
            if (matcher.find()){
                return book;
            }
            matcher = moviesBibliography.matcher(lineWord);
            if (matcher.find()){
                return movies;
            }
            return book;
        }

        if (lineWord.contains("FM,") || lineWord.contains("AM,")){
            return radio;
        }

        if (lineWord.contains("CD-ROM") || lineWord.contains("DVD")){
            return cdRomDvd;
        }

        if (lineWord.contains("licenciatura") || lineWord.contains("Licenciatura") || lineWord.contains("titulación") || lineWord.contains("Titulación")){
            return thesis;
        }

        if (lineWord.contains("En:")){
            if( lineWord.contains("N°") || lineWord.contains(", Año")){
                return articleMagazine;
            }
            Matcher matcher = chapterBookBibliography.matcher(lineWord);
            if (matcher.find()){
                return chapterBook;
            }
            matcher = articleNewspaperBibliography.matcher(lineWord);
            if (matcher.find()){
                return articleNewspaper;
            }
            matcher = conferenceArtworksBibliography.matcher(lineWord);
            if (matcher.find()){
                return conferenceArtworks;
            }
            return null;
        }

        return null;
    }
}
