package com.survey.controller;
/*package com.tcs.survey.platform.controller;

import com.tcs.survey.platform.RestControllerTest;
import com.tcs.survey.platform.service.UserRegistrationService;
import com.tcs.survey.platform.util.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HeartkidControllerTest extends RestControllerTest {

    public static final String REGISTRATION_COUNT_URL = "/survey/regcount";
    public static final String REFERENCE_NO_GENERATOR_URL = "/survey/referencegen";
    public static final String PERSONAL_INFO_URL = "/survey/personalinfo";
    public static final String OUT_HOSPITAL_URL = "/survey/outhospital";
    public static final String UPDATE_RECORD = "/survey/updaterecord";

    @Autowired
    UserRegistrationService heartKidRegistrationService;

    @Before
    public void setUp() throws Exception {
        initializeMockMvc();
    }

    @Test
    public void returnRegistrationCount() throws Exception {

        heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        mockMvc.perform(get(REGISTRATION_COUNT_URL)).andExpect(status().isOk());
    }

    @Test
    public void returnRegistrationCountNotNull() throws Exception {

        heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        MvcResult result = mockMvc.perform(get(REGISTRATION_COUNT_URL))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals(result.getResponse().getContentAsString(), "0");
    }

    @Test
    public void returnBadRequestForPOST() throws Exception {
        mockMvc.perform(post(REGISTRATION_COUNT_URL))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void returnReferenceNumber() throws Exception {
    	for(int i=0;i<2000;i++)
    	{
        mockMvc.perform(get(REFERENCE_NO_GENERATOR_URL))
                .andExpect(status().isOk())
                .andReturn();
    	}
    }

    @Test
    public void returnReferenceNumberNotNull() throws Exception {
        MvcResult result = mockMvc.perform(get(REFERENCE_NO_GENERATOR_URL))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void returnBadRequestForReferenceNoGeneratorForPOST() throws Exception {
        mockMvc.perform(post(REFERENCE_NO_GENERATOR_URL))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    public void savePersonalInformation() throws Exception {

        final MvcResult result = mockMvc.perform(post(PERSONAL_INFO_URL)
                .content(json(new RequestBuilder().defaultValues()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals(result.getResponse().getContentAsString(), "[]");
    }

    @Test
    public void returnBadRequestSavePersonalInformationForEmpty() throws Exception {

        mockMvc.perform(post(PERSONAL_INFO_URL)
                .content(json(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnBadRequestSavePersonalInformationViolation() throws Exception {

        mockMvc.perform(post(PERSONAL_INFO_URL)
                .content(json("-1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnErrorSavePersonalInformationForGET() throws Exception {

        mockMvc.perform(get(PERSONAL_INFO_URL)
                .content(json(new RequestBuilder().defaultValues()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void saveOutHospitalInformation() throws Exception {

        final MvcResult result = mockMvc.perform(post(OUT_HOSPITAL_URL)
                .content(json(new RequestBuilder().defaultValues()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals(result.getResponse().getContentAsString(), "[]");

    }

    @Test
    public void returnBadRequestSaveOutHospitalInformationForEmpty() throws Exception {

        mockMvc.perform(post(OUT_HOSPITAL_URL)
                .content(json(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnBadRequestSaveOutHospitalInformationViolation() throws Exception {

        mockMvc.perform(post(OUT_HOSPITAL_URL)
                .content(json("-1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnErrorSaveOutHospitalInformationForGET() throws Exception {

        mockMvc.perform(get(OUT_HOSPITAL_URL)
                .content(json(new RequestBuilder().defaultValues()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void returnBadRequestUpdateInformationForEmpty() throws Exception {

        mockMvc.perform(post(UPDATE_RECORD)
                .content(json(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnBadRequestUpdateInformationViolation() throws Exception {

        mockMvc.perform(post(UPDATE_RECORD)
                .content(json("-1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void returnErrorUpdateInformationForGET() throws Exception {

        mockMvc.perform(get(UPDATE_RECORD)
                .content(json(new RequestBuilder().updateValues()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }
}
*/