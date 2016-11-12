package com.survey.service;
/*package com.tcs.survey.platform.service;

import com.tcs.survey.platform.ServiceTest;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.util.RandomNumGenerator;
import com.tcs.survey.platform.util.RequestBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HeartKidRegistrationServiceTest extends ServiceTest {

   
    @Autowired
    private RandomNumGenerator randomNumber;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRegistrationService heartKidRegistrationService;

    @Test
    public void returnRetrieveRegistrationCount() {
        RegisterDtoEntity registerDtoEntity = new RequestBuilder().defaultValues();
        surveyRepository.save(registerDtoEntity);

        assertNotNull(heartKidRegistrationService.retrieveRegistrationCount());
    }

    @Test
    public void returnZeroRetrieveRegistrationCount() {
    	surveyRepository.deleteAll();

        assertThat(heartKidRegistrationService.retrieveRegistrationCount(), is("0"));

    }

    @Test
    public void returnRandomReferenceNumber() {

        assertNotNull(heartKidRegistrationService.generateReferenceNumber());

    }

    @Test
    public void saveRegistrationInformation() {

        final RegisterDtoEntity registerDtoEntity = heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().defaultValues());

        assertEquals(registerDtoEntity.getReferencenumber(), "ABCD1234");
        assertEquals(registerDtoEntity.getFirstname(), "Test");
        assertEquals(registerDtoEntity.getLastname(), "Test");

    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void returnErrorForNullRegistrationInformation() {

        assertNull(heartKidRegistrationService.saveRegistrationInformation(null));

    }

    @Test
    public void updateRegistrationInformation() {

        final RegisterDtoEntity registerDtoEntity = heartKidRegistrationService.saveRegistrationInformation(new RequestBuilder().updateValues());
        assertEquals(registerDtoEntity.getUpdateddate(), "31/12/2015");
    }

}*/