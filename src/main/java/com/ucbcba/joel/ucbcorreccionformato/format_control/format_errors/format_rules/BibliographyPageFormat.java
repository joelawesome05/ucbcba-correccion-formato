package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.others.bibliographies.PatternBibliographyReferences;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.Format;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control.TittleFormat;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
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

        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        double lineSpacing = getterWordLines.getLineSpacingBibliography(biographyPageStart);
        List<SingleLine> singleLines = getterWordLines.getSingleLinesWithoutAnyNumeration(page);
        controlTittle(page, formatErrors, pageWidth, pageHeight, singleLines);

        List<WordLine> bibliographyLines = getterWordLines.getBibliographyLines(singleLines,lineSpacing);

        for(WordLine currentBibliography: bibliographyLines){
            List<String> formatErrorscomments = new ArrayList<>();
            String bibliographyElementString = currentBibliography.toString();
            PatternBibliographyReferences pattern = getPattern(bibliographyElementString);
            if (pattern!=null) {
                Matcher matcher = pattern.getMatcher(bibliographyElementString);
                if (!matcher.find()) {
                    formatErrorscomments.add(pattern.getName()+" siga el patrón «"+pattern.getPatternString()+"»");
                }
            }else{
                formatErrorscomments.add("La referencia bibliográfica siga las normas de presentación según la Guía");
            }
            reportFormatErrors(formatErrorscomments, currentBibliography, formatErrors, pageWidth, pageHeight, page);
        }
        return formatErrors;
    }

    private void controlTittle(int page, List<FormatErrorResponse> formatErrors, float pageWidth, float pageHeight, List<SingleLine> singleLines) {
        Format biographyTitle = new TittleFormat(12,"Izquierdo",pageWidth,true,"BIBLIOGRAFÍA");
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

    public PatternBibliographyReferences getPattern(String lineWord){
        Pattern discussionListBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^(]*\\([^f]*fecha[^)]+\\).*\\.", Pattern.CASE_INSENSITIVE);
        String discussionString = "APELLIDO, Nombres (Año). “Título del mensaje”. Nombre de la lista de discusión-dirección de la lista o protocolo del grupo de interés-correo electrónico disponible (fecha de consulta dd/mm/aaaa).";
        PatternBibliographyReferences discussionList = new PatternBibliographyReferences("Listas de discusión",discussionListBibliography,discussionString);

        Pattern pageWebBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^E]*En:[^<]*<[^>]+>[^,]*,[^(]*\\([^f]*fecha[^)]+\\).*\\.", Pattern.CASE_INSENSITIVE);
        String pageWebString = "APELLIDO, Nombres (Año). “Título. Subtítulo”. En: <dirección electrónica completa>, (fecha de consulta dd/mm/aaaa).";
        PatternBibliographyReferences pageWeb = new PatternBibliographyReferences("Página web",pageWebBibliography,pageWebString);

        Pattern emailBibliography = Pattern.compile("[^(]+\\([^)]+\\)[^(]*\\([^)]+\\)\\.([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^(]*\\([^)]+\\)[^(]*\\([^f]*fecha[^)]+\\).*\\.", Pattern.CASE_INSENSITIVE);
        String emailString = "APELLIDO, Nombres (+ dirección electrónica del remitente) (Año). “Título del mensaje”. Nombre del destinatario (+ dirección electrónica del destinatario) (fecha del mensaje dd/mm/aaaa).";
        PatternBibliographyReferences email = new PatternBibliographyReferences("Correo electrónico",emailBibliography,emailString);

        Pattern radioBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^,]+,[^(]*\\([^f]*fecha[^)]+\\).*\\.", Pattern.CASE_INSENSITIVE);
        String radioString = "APELLIDO, Nombres (Año). “Nombre del programa”. Frecuencia o canal, Ciudad desde la que se efectúa la emisión (fecha de la emisión dd/mm/aaaa).";
        PatternBibliographyReferences radio = new PatternBibliographyReferences("Programa de radio o televisión",radioBibliography,radioString);

        Pattern cdRomDvdBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+\\.", Pattern.CASE_INSENSITIVE);
        String cdRomDvdString = "APELLIDO, Nombres (Año). Título. Subtítulo. CD-ROM, Lugar de edición: Editorial.";
        PatternBibliographyReferences cdRomDvd = new PatternBibliographyReferences("Libro en soporte CD-ROM/DVD",cdRomDvdBibliography,cdRomDvdString);

        Pattern thesisBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\))\\.[^,]+,.+\\.", Pattern.CASE_INSENSITIVE);
        String thesisString = "APELLIDO, Nombres (Año). Título. Subtítulo. Especificación del grado académico y de la disciplina de la tesis, Universidad en la que fue presentada, Ciudad.";
        PatternBibliographyReferences thesis = new PatternBibliographyReferences("Tesis/Trabajo de titulación",thesisBibliography,thesisString);

        Pattern articleMagazineBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^E]*En:.+Año.+N.+\\.", Pattern.CASE_INSENSITIVE);
        String articleMagazineString = "APELLIDO, Nombres (Año). “Título del artículo”. En: Nombre de la revista, Institución editora, Año de la revista..., No..., Ciudad.";
        PatternBibliographyReferences articleMagazine = new PatternBibliographyReferences("Artículo de revista",articleMagazineBibliography,articleMagazineString);

        Pattern chapterBookBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^E]*En:[^:]+:.+\\.", Pattern.CASE_INSENSITIVE);
        String chaperBookString = "APELLIDO, Nombres (Año). “Título del capítulo”. En: Autor, Título. Subtítulo del libro. Lugar de edición: Editorial.";
        PatternBibliographyReferences chapterBook = new PatternBibliographyReferences("Capítulo de libro",chapterBookBibliography,chaperBookString);

        Pattern articleNewspaperBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^E]*En:[^,]+,[^(]+\\([^f]*fecha[^)]+\\).*\\.", Pattern.CASE_INSENSITIVE);
        String articleNewspaperString = "APELLIDO, Nombres (Año). “Título del artículo”. En: Nombre del periódico, Ciudad, sección o páginas en las que se encuentra el artículo (fecha dd/mm/aaaa).";
        PatternBibliographyReferences articleNewspaper = new PatternBibliographyReferences("Artículo de periódico",articleNewspaperBibliography,articleNewspaperString);

        Pattern conferenceArtworksBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^E]*En:[^,]+,.+\\.", Pattern.CASE_INSENSITIVE);
        String conferenceString = "APELLIDO, Nombres (Año). “Título. Subtítulo”. En: Nombre del congreso o de la conferencia, lugar de publicación.";
        PatternBibliographyReferences conferenceArtworks = new PatternBibliographyReferences("Congreso/Conferencia",conferenceArtworksBibliography,conferenceString);

        Pattern moviesBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)([^“]*“[^”]+”|[^\"]*\"[^\"]+\")\\.[^:]+:.+\\.", Pattern.CASE_INSENSITIVE);
        String moviesString = "APELLIDO, Nombres (Año). “Título de la película”. Ciudad en la que opera la productora: Nombre de la productora.";
        PatternBibliographyReferences movies = new PatternBibliographyReferences("Película",moviesBibliography,moviesString);

        Pattern bookBibliography = Pattern.compile("([^(]+\\([^)]+\\)\\.|[^(]+\\([dir.compe]+\\)[^(]*\\([^)]+\\)\\.)[^:]+:.+\\.", Pattern.CASE_INSENSITIVE);
        String bookSring = "APELLIDO, Nombres (Año). Título. Subtítulo. Lugar de edición: Editorial.";
        PatternBibliographyReferences book = new PatternBibliographyReferences("Libro",bookBibliography,bookSring);

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
            return chapterBook;
        }
        return null;
    }
}
