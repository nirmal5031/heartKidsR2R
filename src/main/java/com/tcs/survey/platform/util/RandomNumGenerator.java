package com.tcs.survey.platform.util;



import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class RandomNumGenerator {

  private  Random random = new Random((new Date()).getTime());
  
    /**
     * generates an alphanumeric string based on specified length.
     * @param length # of characters to generate
     * @return random string
     */
    public String generateRandomString(int length) {
      char[] values = {'a','b','c','d','e','f','g','h','i','j',
               'k','l','m','n','o','p','q','r','s','t',
               'u','v','w','x','y','z','0','1','2','3',
               '4','5','6','7','8','9'};
      String out = "";
      for (int i=0;i<length;i++) {
          int idx=random.nextInt(values.length);
        out += values[idx];
      }
      return out;
    }
    
    public int randomcountgenerator()
    {
    Random r = new Random();
    int low = 4;
    int high = 5;
    int R = r.nextInt(high-low) + high;
	return R;
    }
}

   