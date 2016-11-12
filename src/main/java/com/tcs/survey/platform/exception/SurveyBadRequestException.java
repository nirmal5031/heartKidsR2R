package com.tcs.survey.platform.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SurveyBadRequestException extends RuntimeException {
    private static final long serialVersionUID = -229518320737989810L;

    
    public SurveyBadRequestException(final String message) {
        super(message);
    }

    public SurveyBadRequestException(final String message,
            final Throwable rootCause) {
        super(message, rootCause);
    }
}
