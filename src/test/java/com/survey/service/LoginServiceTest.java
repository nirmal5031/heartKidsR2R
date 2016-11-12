package com.survey.service;

import com.survey.ServiceTest;
import com.survey.util.AdminUserBuilder;
import com.tcs.survey.platform.model.entity.CreateAdminUser;
import com.tcs.survey.platform.model.entity.LoginEntity;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.AuthRepository;
import com.tcs.survey.platform.service.LoginService;
import com.tcs.survey.platform.util.EncrptDecryptPassword;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class LoginServiceTest extends ServiceTest {

    LoginEntity loginentity = new LoginEntity();

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LoginService loginService;


  

    @Test
    public void validateUser() throws Exception {

        final LoginEntity loginEntity = loginService.validateUser("Test", "Test");

        assertEquals(loginEntity.getStatus(), "success");
    }

    @Test
    public void returnErrorInvalidUserName() throws Exception {

        final LoginEntity loginEntity = loginService.validateUser("Testing", "Test");

        assertEquals(loginEntity.getStatus(), "NOUSER");
    }


    @Test
    public void returnErrorInvalidPassword() throws Exception {

        final LoginEntity loginEntity = loginService.validateUser("Test", "Testing");

        assertEquals(loginEntity.getStatus(), "INVALIDCREDENTIALS");
    }

    @Test
    public void resetPassword() throws Exception {

        final LoginEntity loginEntity = loginService.resetpassword("Test", "Test", "New");

        assertEquals(loginEntity.getStatus(), "RESETSUCCESS");
    }

    @Test
    public void returnErrorForEmptyPassword() throws Exception {

        final LoginEntity loginEntity = loginService.resetpassword("Test", "Test", "");

        assertEquals(loginEntity.getStatus(), "RESETSUCCESS");
    }


}
