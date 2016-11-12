package com.tcs.survey.platform.util;

import com.tcs.survey.platform.exception.SurveyBadRequestException;

public class UserValidation {
	
	    private UserValidation() {
	    }

	    public static void validateUser(String userId) {
	        try {
	            Integer.parseInt(userId);
	        } catch (NumberFormatException e) {
	         
	            throw new SurveyBadRequestException(
	                    Constants.LOGIN_INVALID_CREDENTIALS);
	        }
	    }
	}

