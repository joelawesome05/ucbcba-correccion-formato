package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IEEEBibliography implements Bibliography {
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
            formatErrorscomments.add("La referencia bibliográfica siga las normas de referencias IEEE");
        }
        return formatErrorscomments;
    }

    public PatternBibliographyReferences getPattern(String lineWord){
        Pattern pageWebBibliography = Pattern.compile("[^,]+,[^\\[]+\\[[^]]+]([^R]*Recuperado|[^A]*Available|[^D]*Disponible)[^:]*:.+\\.", Pattern.CASE_INSENSITIVE);
        String pageWebString = "Iniciales del nombre y Apellido, “Título”. [En línea]. Disponible en: <dirección electrónica completa>.";
        PatternBibliographyReferences pageWeb = new PatternBibliographyReferences("Página web",pageWebBibliography,pageWebString);

        Pattern bookBibliography = Pattern.compile("[^,]+,[^:]+:[^,]+,.+\\.", Pattern.CASE_INSENSITIVE);
        String bookSring = "Iniciales del nombre y Apellido, Título. Lugar de edición: Editorial, Año de publicación.";
        PatternBibliographyReferences book = new PatternBibliographyReferences("Libro",bookBibliography,bookSring);

        Pattern miscellaneousBibliography = Pattern.compile("([^,]+,|[^.]+\\.)([^,]+,|[^.]+\\.).+\\.", Pattern.CASE_INSENSITIVE);
        String miscellaneousSring = "Iniciales del nombre y Apellido, Título, Año.";
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
