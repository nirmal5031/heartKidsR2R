package com.tcs.survey.platform.util;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ReferenceNumGenerator {

	
	public  long createRandomInteger(int aStart, long aEnd, Random aRandom){
	    if ( aStart > aEnd ) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    long randomNumber =  fraction + (long)aStart;    
		return randomNumber;

	  }
	
	public  synchronized  long generateRandomPin(){
		  long randomNumber;
     int start =1000000000;
     long end = 9999999999L;

    Random random = new Random();

  
    randomNumber = createRandomInteger(start, end, random);
  
  
	return randomNumber;
}
	
	
}
