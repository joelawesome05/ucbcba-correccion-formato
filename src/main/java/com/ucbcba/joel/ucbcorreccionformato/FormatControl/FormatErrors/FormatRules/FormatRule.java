package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatRules;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse.FormatErrorResponse;

import java.io.IOException;
import java.util.List;

public interface FormatRule {
    List<FormatErrorResponse> getFormatErrors(int page) throws IOException;
}
