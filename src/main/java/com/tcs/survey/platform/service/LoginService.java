package com.tcs.survey.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.survey.platform.model.entity.LoginEntity;
import com.tcs.survey.platform.repository.AuthRepository;
import com.tcs.survey.platform.util.EncrptDecryptPassword;

@Service
public class LoginService {

    LoginEntity loginentity = new LoginEntity();
    @Autowired
    private AuthRepository repository;

    public LoginEntity validateUser(String heartkidUsername, String password) {

        String errorMessage;
        String newpassword = null;
        int useridexist = 0;
        try {

            if (heartkidUsername != null) {
                useridexist = repository.checkuserID(heartkidUsername);
            }
            if (useridexist == 1) {
                loginentity = repository.findOne(heartkidUsername);
                newpassword = EncrptDecryptPassword.decrypt(loginentity.getPassword());
                System.out.println("New pass------->>>>" + newpassword);

                if ((loginentity.getUsername().equalsIgnoreCase(heartkidUsername)) & (newpassword.equals(password))) {

                    loginentity.setStatus("success");
                    // loginentity.setErrorMessage(errorMessage);
                } else if ((loginentity.getUsername().equalsIgnoreCase(heartkidUsername)) & (newpassword != password))

                {
                    loginentity.setStatus("INVALIDCREDENTIALS");
                    // errorMessage = "INVALID CREDENTIALS";
                    // loginentity.setErrorMessage(errorMessage);

                }

            } else {
                loginentity.setStatus("NOUSER");
            }
        } catch (NullPointerException nullPointer) {
            errorMessage = "USER NOT REGISTERED";
            loginentity.setStatus("NOUSER");
            System.out.println("Empty value-- not found" + errorMessage);
            /*	loginentity.setHeartkidNumber(heartkidUsername);*/

        } catch (Exception e) {
            e.printStackTrace();


        }

        return loginentity;
    }


    public LoginEntity resetpassword(String username, String password, String newpassword) {
        int useridexist = 0;
        String encryppass = null;
        try {
            if (username != null) {
                useridexist = repository.checkuserID(username);
            }
            if (useridexist == 1) {
                loginentity = repository.findOne(username);
                encryppass = EncrptDecryptPassword.decrypt(loginentity.getPassword());
                System.out.println("=========encryppass========" + encryppass);
                if ((loginentity.getUsername().equalsIgnoreCase(username)) & (password.equals(encryppass))) {

                    String response = updatenewpassword(username, newpassword);

                    loginentity.setStatus(response);
                    // loginentity.setErrorMessage(errorMessage);
                } else if ((loginentity.getUsername().equalsIgnoreCase(username)) & (password != encryppass))

                {
                    loginentity.setStatus("INVALIDCREDENTIALS");
                }
            } else {
                loginentity.setStatus("NOUSER");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginentity;
    }

    public String updatenewpassword(String username, String password) {
        int response = 0;
        String newpassword = null;
        String responsevalue = null;
        int flag = 1;
        try {

            newpassword = EncrptDecryptPassword.encrypt(password);
            response = repository.updateresetpassword(username, newpassword, flag);
            System.out.println("reset aseetotf ----------------->" + response);
            if (response == 1) {
                responsevalue = "RESETSUCCESS";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responsevalue;

    }

}
