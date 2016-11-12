package com.tcs.survey.platform.util;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class Dategenerator {
	
		
	public String dategenerator() {
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   //get current date time with Date()
	   Date date = new Date();
	   String dateval = dateFormat.format(date);
	   		return dateval;
		
	}
	
	}

	   

