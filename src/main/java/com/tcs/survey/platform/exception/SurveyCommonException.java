package com.tcs.survey.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SurveyCommonException extends RuntimeException {

	    private static final long serialVersionUID = 2930512070636071126L;

	    public SurveyCommonException(final String message) {
	        super(message);
	    }
		
	    public SurveyCommonException(final String message, final Throwable rootCause) {
	        super(message, rootCause);
	    }
	}
