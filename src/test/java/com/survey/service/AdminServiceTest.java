package com.survey.service;

import com.survey.ServiceTest;
import com.survey.util.AdminUserBuilder;
import com.survey.util.RequestBuilder;
import com.tcs.survey.platform.model.entity.CreateAdminUser;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.service.AdminService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;


public class AdminServiceTest extends ServiceTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AdminService adminService;

    @Test
    public void deleteUserByReferenceNumber() throws Exception {

        RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
        surveyRepository.save(registerDtoEntity);

        assertEquals(adminService.deleteUserByReferenceNumber("ABCD1234"), 1);
    }

    @Test
    public void nodeleteUserForInvalidReferenceNumber() throws Exception {

        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
        surveyRepository.save(registerDtoEntity);

        assertEquals(adminService.deleteUserByReferenceNumber("XXXXXXXXXX"), 0);
    }

   

    @Test
    public void returnNoRecordsForInvalidUser() throws Exception {

        final List<CreateAdminUser> allUser = (List<CreateAdminUser>) adminService.findAdminUser("XXXXXXXXXXXXXXXXX");
        assertThat(allUser, hasSize(0));
    }

//    @Test
//    public void getLovedCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        assertEquals(adminService.getLovedcount(), "0");
//    }
//
//    @Test
//    public void getCarerCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        assertEquals(adminService.getCarercount(), "0");
//    }
//
//    @Test
//    public void getPatientCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        registerDtoEntity.setUsertype("patient");
//        surveyRepository.save(registerDtoEntity);
//
//        assertEquals(adminService.getPatientcount(), "1");
//    }
//
//    @Test
//    public void getLovedBarCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        final List<String> count = (List<String>) adminService.getLovedbarcount();
//        assertThat(count, hasSize(0));
//    }
//
//    @Test
//    public void getCarerBarCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        final List<String> count = (List<String>) adminService.getCarerbarcount();
//        assertThat(count, hasSize(0));
//    }
//
//    @Test
//    public void getPatientBarCount() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        final List<String> count = (List<String>) adminService.getPatientbarcount();
//        assertThat(count, hasSize(1));
//    }
//
//    @Test
//    public void findUserById() throws Exception {
//
//        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
//        surveyRepository.save(registerDtoEntity);
//
//        final RegisterDtoEntity newRegisterDtoEntity = adminService.findUser(1);
//        assertEquals(newRegisterDtoEntity.getFirstname(), "Test");
//
//    }

}
