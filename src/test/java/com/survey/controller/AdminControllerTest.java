package com.survey.controller;
/*package com.tcs.survey.platform.controller;

import com.tcs.survey.platform.RestControllerTest;
import com.tcs.survey.platform.model.entity.CreateAdminUser;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.service.AdminService;
import com.tcs.survey.platform.service.UserRegistrationService;
import com.tcs.survey.platform.util.AdminUserBuilder;
import com.tcs.survey.platform.util.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AdminControllerTest extends RestControllerTest {

    public static final String SEARCH_RECORD = "/survey/getrecord";
    public static final String DOWNLOAD_EXCEL = "/survey/downloadExcel";
    public static final String DELETE_BYREFERENCE_NUMBER = "/survey/deleterecord/{deleterecordref}";
    public static final String HEARTKID_CREATEADMINUSER = "/survey/createadminuser";
    public static final String HEARTKID_LISTADMINUSER = "/survey/listadminuser";
    public static final String DELETE_BY_USERNAME = "/survey/deleteadminuser/{delusername}";
    public static final String FETCH_ADMIN_USER = "/survey/fetchadminuser/{username}";
    public static final String REPORTCOUNT = "/survey/reportcount";
    public static final String REPORTBARCOUNT = "/survey/reportbarcount";
    public static final String USERDETAILS_BY_ID = "/survey/getuserdetails/{id}";

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRegistrationService heartKidRegistrationService;

    @Autowired
    AdminService adminService;

    @Before
    public void setUp() throws Exception {
        initializeMockMvc();
    }

    @Test
    public void returnSearchRecord() throws Exception {

        heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        final MvcResult result = mockMvc.perform(post(SEARCH_RECORD)
                .content(json(new RegisterDtoEntity()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals(result.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void returnNoRecordFoundForNullSearchRecord() throws Exception {

        final MvcResult result = mockMvc.perform(post(SEARCH_RECORD)
                .content(json(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void returnNoRecordFoundForInvalidSearchRecord() throws Exception {

        surveyRepository.deleteAll();
        final RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
        final MvcResult result = mockMvc.perform(post(SEARCH_RECORD)
                .content(json(registerDtoEntity))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void responseContainsValidExcel() throws Exception {

        heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        mockMvc.perform(post(DOWNLOAD_EXCEL)
                .content(json(new RegisterDtoEntity()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.ms-excel"))
                .andExpect(content().string(containsString("ABCD1234")));
    }

    @Test
    public void notFoundForNonExistingRecord() throws Exception {

        AdminController controller = new AdminController();
        ModelAndView modelAndView = controller.downloadExcel(new RegisterDtoEntity().setReferencenumber("XXXXXX"));

        final int size = ((List<RegisterDtoEntity>) modelAndView.getModel().get("listheartkidusers")).size();
        assertEquals(size, 0);
    }

    @Test
    public void deleteRecordByReferenceNumber() throws Exception {

        final RegisterDtoEntity registerDtoEntity = heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        mockMvc.perform(get(DELETE_BYREFERENCE_NUMBER, registerDtoEntity.getReferencenumber()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(registerDtoEntity.getReferencenumber())));

    }

    @Test
    public void returnDeletionErrorForInvalidReferenceNumber() throws Exception {

        mockMvc.perform(get(DELETE_BYREFERENCE_NUMBER, "notValidRefNumber"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Invalid Reference Number")));

    }

    @Test
    public void createAdminUser() throws Exception {

        adminRepository.deleteAll();
        final CreateAdminUser createAdminUser = new AdminUserBuilder().defaultValues();

        mockMvc.perform(post(HEARTKID_CREATEADMINUSER)
                .content(json(createAdminUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")))
                .andReturn();
    }

    @Test
    public void returnErrorForInvalidUser() throws Exception {

        final CreateAdminUser createAdminUser = new CreateAdminUser();
        createAdminUser.setUsername("INVALID");

        mockMvc.perform(post(HEARTKID_CREATEADMINUSER)
                .content(json(createAdminUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("fail")))
                .andReturn();
    }

    @Test
    public void returnErrorForAlreadyExistingUser() throws Exception {

        final CreateAdminUser createAdminUser = new AdminUserBuilder().defaultValues();

        mockMvc.perform(post(HEARTKID_CREATEADMINUSER)
                .content(json(createAdminUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post(HEARTKID_CREATEADMINUSER)
                .content(json(createAdminUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("useridexist")))
                .andReturn();

    }

    @Test
    public void listAllAdminUser() throws Exception {

        final CreateAdminUser createAdminUser = new AdminUserBuilder().defaultValues();

        mockMvc.perform(get(HEARTKID_LISTADMINUSER))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteRecordByUserName() throws Exception {

        adminService.saveAdminUser(new AdminUserBuilder().defaultValues());

        mockMvc.perform(get(DELETE_BY_USERNAME, "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));

    }

    @Test
    public void returnDeletionErrorForInvalidUserName() throws Exception {

        mockMvc.perform(get(DELETE_BY_USERNAME, "notValidUserName"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));

    }

    @Test
    public void fetchAdminUserByUserName() throws Exception {

        adminService.saveAdminUser(new AdminUserBuilder().defaultValues());

        mockMvc.perform(get(FETCH_ADMIN_USER, "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));

    }

    @Test
    public void returnNoRecordForInvalidAdminUserName() throws Exception {

        mockMvc.perform(get(FETCH_ADMIN_USER, "INVALID"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));

    }

    @Test
    public void returnCount() throws Exception {

        surveyRepository.save(new RequestBuilder().defaultValues());

        mockMvc.perform(get(REPORTCOUNT))
                .andExpect(status().isOk());

    }

    @Test
    public void returnZeroCount() throws Exception {

        surveyRepository.deleteAll();

        mockMvc.perform(get(REPORTCOUNT))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0,0,0")));

    }

    @Test
    public void returnBarCount() throws Exception {

        heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        mockMvc.perform(get(REPORTBARCOUNT))
                .andExpect(status().isOk());
    }

    @Test
    public void returnZeroBarCount() throws Exception {

        surveyRepository.deleteAll();

        mockMvc.perform(get(REPORTBARCOUNT))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[],[],[]")));

    }

    @Test
    public void fetchUserDetailsById() throws Exception {

        surveyRepository.deleteAll();
        final RegisterDtoEntity registerDtoEntity = heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        mockMvc.perform(get(USERDETAILS_BY_ID, registerDtoEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test")));

    }

    @Test
    public void returnNoRecordForInvalidId() throws Exception {

        mockMvc.perform(get(USERDETAILS_BY_ID, new Random().nextLong()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }
}
*/