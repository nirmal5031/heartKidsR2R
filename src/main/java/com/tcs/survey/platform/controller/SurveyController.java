package com.tcs.survey.platform.controller;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionarieEntity;
import com.tcs.survey.platform.model.entity.RecordStatusEntity;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.service.AdminService;
import com.tcs.survey.platform.service.MailingService;
import com.tcs.survey.platform.service.UserRegistrationService;
import com.tcs.survey.platform.util.Dategenerator;

@RestController
public class SurveyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	private Dategenerator dategenerator;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MailingService mailingclass;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private ProfileRepository profilerepo;
	
	@Autowired
	private AdminRepository createadminrepository;

	
	@RequestMapping(value = "survey/regcount", method = RequestMethod.GET)
	public String registrationCount() {
		String regcount = null;
		try {
			regcount = userRegistrationService.retrieveRegistrationCount();
		} catch (Exception ex) {
			return "Error creating the entry: " + ex.toString();
		}
		return regcount;
	}

	@RequestMapping(value = "survey/referencegen", method = RequestMethod.GET)
	public String generateReference() {
		return userRegistrationService.generateReferenceNumber();
	}

	@RequestMapping(value = "survey/personalinfo/{status}/{usertype}", method = RequestMethod.POST)
	public String savePersonalInfo(@RequestBody ProfileEntity personalinfo,
			@PathVariable(value = "status") String status, @PathVariable(value = "usertype") String usertype) {
		String personalinfoJSON = null;
		RecordStatusEntity recordstatusEntity = new RecordStatusEntity();
		try {
			personalinfo.setCreatedBy("User");
			recordstatusEntity.setCompletion_level("1");
			recordstatusEntity.setStatus(status);
			recordstatusEntity.setUsertype(usertype);
			recordstatusEntity.setRefno(personalinfo.getRefno());
			recordstatusEntity.setRegistrationdate(dategenerator.dategenerator());
			recordstatusEntity.setCreatedBy("User");
			userRegistrationService.saveUserrecordStatus(recordstatusEntity);

			if (personalinfo != null) {
				userRegistrationService.saveRegistrationInformation(personalinfo);
				personalinfoJSON = mapper.writeValueAsString(personalinfo);
			}

		} catch (NullPointerException exception) {
			return "{'value':'failure'}";
		} catch (Exception ex) {
			return "Error creating the entry: " + ex.toString();
		}
		return personalinfoJSON;
	}

	@RequestMapping(value = "survey/outhospital/{usertype}", method = RequestMethod.POST)
	public String finalConfirmation(@RequestBody QuestionarieEntity question,
			@PathVariable(value = "usertype") String usertype) throws MessagingException {
		String response = null;
		String status = null;
		String createdBy = "User";
		ProfileEntity profiledetails = new ProfileEntity();
		try {
			RecordStatusEntity recordstatusEntity = new RecordStatusEntity();
			status = "success";
			question.getQuestionformat().get(0).setCreatedBy(createdBy);
			userRegistrationService.savedatajson(question.getQuestionformat(), question.getRefno(), createdBy);
			recordstatusEntity.setStatus(status);
			recordstatusEntity.setUsertype(usertype);
			recordstatusEntity.setRefno(question.getRefno());
			int levelcompletion = Integer.parseInt(question.getQuestionformat().get(0).getSection_id()) + 1;
			String completionLevel = levelcompletion + "";

			userRegistrationService.updateUserrecordStatus(question.getRefno(), completionLevel, status);
			
			profiledetails = profilerepo.getAllProfilesDetails(question.getRefno());
			System.out.println("dsfsdf"+profiledetails.getEmail());
			String userName = profiledetails.getFirstname()+" "+profiledetails.getLastname();
			userRegistrationService.sendMailToUser(profiledetails.getEmail(),userName, question.getRefno());
			
			//mailingclass.mailingservice(profiledetails.getEmail(),userName, question.getRefno());

		} catch (Exception ex) {
			response = "failure";
			LOGGER.info(ex.toString());
		}

		return response;
	}

	@RequestMapping(value = "survey/gethkpagecontent/{pagecontent}", method = RequestMethod.GET)
	public String getPageContent(@PathVariable(value = "pagecontent") String pagecontent) {
		return userRegistrationService.retrievepagecontent(pagecontent);
	}

	@RequestMapping(value = "survey/newjsonrecord", method = RequestMethod.POST)
	public @ResponseBody void newrecordjson(@RequestBody QuestionarieEntity question) {
		String createdBy = "User";

		System.out.println("question ID @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+question.getId());
		userRegistrationService.savedatajson(question.getQuestionformat(), question.getRefno(), createdBy);
		int levelcompletion = Integer.parseInt(question.getQuestionformat().get(0).getSection_id()) + 1;
		String completionLevel = levelcompletion + "";
		String status = "incomplete";
		userRegistrationService.updateUserrecordStatus(question.getRefno(), completionLevel, status);
	}
	
	@RequestMapping(value = "survey/gethkpagecontentdev/{pagecontent}", method = RequestMethod.GET)
	public String getPageContentDev(@PathVariable(value = "pagecontent") String pagecontent) {
		return userRegistrationService.retrievepagecontentDev(pagecontent);
	}

	@RequestMapping(value = "survey/unsubscribe/{unsubsReferenceNo}", method = RequestMethod.GET)
	public String  unSubscribeUserData(@PathVariable(value = "unsubsReferenceNo") String unsubsReferenceNo) {
		String MailResponse = userRegistrationService.sendMailToAdminUnsubscription(unsubsReferenceNo);
		
		return MailResponse;
	}

	
	
}