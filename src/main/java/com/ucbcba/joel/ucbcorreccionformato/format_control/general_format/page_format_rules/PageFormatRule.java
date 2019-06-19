package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.FormatErrorResponse;

import java.io.IOException;
import java.util.List;

public interface PageFormatRule {
    List<FormatErrorResponse> getFormatErrors(int page) throws IOException;
}
