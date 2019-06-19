package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APABibliography implements Bibliography {
    @Override
    public List<String> getFormatErrors(WordLine bibliographyLines) {
        List<String> formatErrorscomments = new ArrayList<>();
        String bibliographyElementString = bibliographyLines.toString();
        PatternBibliographyReferences pattern = getPattern(bibliographyElementString);
        if (pattern!=null) {
            Matcher matcher = pattern.getMatcher(bibliographyElementString);
            if (!matcher.find()) {
                formatErrorscomments.add(pattern.getName()+" siga el patrón «"+pattern.getPatternString()+"»");
            }
        }else{
            formatErrorscomments.add("La referencia bibliográfica siga las normas APA 6ta edición");
        }
        return formatErrorscomments;
    }

    public PatternBibliographyReferences getPattern(String lineWord){
        Pattern pageWebBibliography = Pattern.compile("[^(]+\\([^)]+\\)\\..+\\.([^R]*Recuperado|[^O]*Obtenido|[^D]*Disponible)[^:]*:.+\\.", Pattern.CASE_INSENSITIVE);
        String pageWebString = "APELLIDO, Nombres (Año). Título. Recuperado : <dirección electrónica completa>.";
        PatternBibliographyReferences pageWeb = new PatternBibliographyReferences("Página web",pageWebBibliography,pageWebString);

        Pattern bookBibliography = Pattern.compile("[^(]+\\([^)]+\\)\\..+\\.[^:]+:.+\\.", Pattern.CASE_INSENSITIVE);
        String bookSring = "APELLIDO, Nombres (Año). Título. Lugar de edición: Editorial.";
        PatternBibliographyReferences book = new PatternBibliographyReferences("Libro",bookBibliography,bookSring);

        Pattern miscellaneousBibliography = Pattern.compile("[^(]+\\([^)]+\\)\\..+\\..+\\.", Pattern.CASE_INSENSITIVE);
        String miscellaneousSring = "APELLIDO, Nombres (Año). Título. Ciudad, País.";
        PatternBibliographyReferences miscellaneous = new PatternBibliographyReferences("Otros",miscellaneousBibliography,miscellaneousSring);

        if (lineWord.contains("http")){
            return pageWeb;
        }

        if (lineWord.contains(":")){
            return book;
        }

        Matcher matcher = miscellaneousBibliography.matcher(lineWord);
        if (matcher.find()){
            return miscellaneous;
        }

        return null;
    }
}
