package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;

import java.io.IOException;
import java.util.List;

public interface FormatRule {
    List<FormatErrorResponse> getFormatErrors(int page) throws IOException;
}
