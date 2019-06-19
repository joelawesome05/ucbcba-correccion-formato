package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public interface Bibliography {
    List<String> getFormatErrors(WordLine bibliographyLines);
}
