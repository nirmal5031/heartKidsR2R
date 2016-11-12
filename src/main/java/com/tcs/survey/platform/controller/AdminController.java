package com.tcs.survey.platform.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.Part;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.survey.platform.model.SurveyBean;
import com.tcs.survey.platform.model.entity.CreateAdminUser;
import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionEntity;
import com.tcs.survey.platform.model.entity.QuestionarieEntity;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.service.AdminService;
import com.tcs.survey.platform.service.SearchService;
import com.tcs.survey.platform.util.EncrptDecryptPassword;

@RestController
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	List<RegisterDtoEntity> searchdto = new ArrayList<RegisterDtoEntity>();
	List<CreateAdminUser> listadminuser = new ArrayList<CreateAdminUser>();
	List<CreateAdminUser> fetchadminuser = new ArrayList<CreateAdminUser>();
	RegisterDtoEntity getuserdetails = new RegisterDtoEntity();

	@Autowired
	private SearchService searchservice;

	@Autowired
	private ProfileRepository profilerepo;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepo;

	@RequestMapping(value = "survey/getrecord", method = RequestMethod.POST, headers = "Accept=application/json")
	public String getRecordHeartkid(@RequestBody RegisterDtoEntity searchentity) throws JsonProcessingException {
		List<SurveyBean> jsonHeartkidobj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonHeartkidobj = searchservice.getSearchRecords(searchentity);
		} catch (Exception ex) {
			LOGGER.info("ERROR in SEARCH" + ex.toString());
		}

		return mapper.writeValueAsString(jsonHeartkidobj);
	}

	@RequestMapping(value = "survey/getprevrecord/{refno}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getPreviousRecords(@PathVariable(value = "refno") String refno) throws JsonProcessingException {
		List<SurveyBean> jsonHeartkidobj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonHeartkidobj = searchservice.getPreviousRecords(refno);
		} catch (Exception ex) {
			LOGGER.info("ERROR in SEARCH" + ex.toString());
		}

		System.out.println("jsonHeartkidobj-------" + mapper.writeValueAsString(jsonHeartkidobj));
		return mapper.writeValueAsString(jsonHeartkidobj);
	}

	@RequestMapping(value = "survey/downloadExcelBySearch", method = RequestMethod.POST)
	public String downloadExcel(@RequestBody RegisterDtoEntity searchentity) {
		JSONArray exportjsonarray = null;
		try {
			exportjsonarray = searchservice.exportheartkid(searchentity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exportjsonarray.toString();
	}

	@RequestMapping(value = "survey/deleterecord/{deleterecordref}", method = RequestMethod.GET)
	public String deleteUsersByRefNumber(@PathVariable(value = "deleterecordref") String deleterecordref) {
		int result = 0;
		try {
			LOGGER.info("delete record---" + deleterecordref);
			result = adminService.deleteUserByReferenceNumber(deleterecordref);
		} catch (Exception ex) {
			return "Error creating the entry: " + ex.toString();
		}
		if (result == 1) {
			return "User record Deleted successfully ! (Reference Id is = " + deleterecordref + ")";
		} else {
			return "Invalid Reference Number - " + deleterecordref;
		}
	}

	@RequestMapping(value = "survey/createadminuser", method = RequestMethod.POST)
	public String createAdminUser(@RequestBody CreateAdminUser createuser) {

		String status = null;
		int userexist = 0;
		try {
			if (createuser != null) {
				userexist = adminService.doesUserExist(createuser.getUsername());
				LOGGER.info("=====userexist====" + userexist);
			}
			if (userexist == 0) {
				String encpass = EncrptDecryptPassword.encrypt(createuser.getPassword());
				String decrptpass = EncrptDecryptPassword.decrypt(encpass);

				if (encpass != null)
					createuser.setPassword(encpass);
				adminService.saveAdminUser(createuser);
				String AdminName = createuser.getFirstname() + " " + createuser.getLastname();
				adminService.sendMailtoAdmin(AdminName, decrptpass, createuser.getEmailid(), createuser.getUsername());
				status = "success";

			} else {
				status = "useridexist";
			}
		} catch (Exception ex) {
			status = "fail";
			LOGGER.info("ERROR in Creating user" + ex.toString());
		}

		return status;
	}

	@RequestMapping(value = "survey/listadminuser", method = RequestMethod.GET)
	public List<CreateAdminUser> listAdminUser() {

		return adminService.findAllUser();

	}

	@RequestMapping(value = "survey/deleteadminuser/{delusername}", method = RequestMethod.GET)
	public int deleteAdminUser(@PathVariable(value = "delusername") String delusername) {
		int response = 0;
		try {
			response = adminService.deleteUseradmin(delusername);
			LOGGER.info("Delete response ----" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "survey/resetuserpass/{username}", method = RequestMethod.GET)
	public int resetUserPass(@PathVariable(value = "username") String username) {
		int response = 0;
		CreateAdminUser adminUser = new CreateAdminUser();
		try {
			response = adminService.resetUseradmin(username);
			fetchadminuser = adminRepo.findOne(username);
			String decrptpass = EncrptDecryptPassword.decrypt(fetchadminuser.get(0).getPassword());
			adminService.sendMailtoReset(fetchadminuser.get(0).getUsername(), decrptpass,
					fetchadminuser.get(0).getEmailid());
			LOGGER.info("Delete response ----" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "survey/fetchadminuser/{username}", method = RequestMethod.GET)
	public List<CreateAdminUser> fetchAdminUser(@PathVariable(value = "username") String username) {
		try {
			LOGGER.info("FETCH ADMIN USER" + username);

			fetchadminuser = adminService.findAdminUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fetchadminuser;
	}

	@RequestMapping(value = "survey/reportcount", method = RequestMethod.GET)
	public List<Object> registrationCount1() {
		List<Object> getrecordcount = null;
		try {
			getrecordcount = adminService.getRecordCount();
		} catch (Exception ex) {
			ex.printStackTrace();
			;
		}

		return getrecordcount;
	}

	@RequestMapping(value = "survey/reportbarcount", method = RequestMethod.GET)
	public List patientcount() {
		LOGGER.info("bar Report Screen for the request-----> ::");
		List reportcount = new ArrayList();
		List reportccount = new ArrayList();
		List reportLovedcount = new ArrayList();
		try {
			reportcount = adminService.getPatientbarcount();
			reportccount = adminService.getCarerbarcount();
			reportLovedcount = adminService.getLovedbarcount();
		} catch (Exception ex) {
			// return "Error creating the entry;
		}

		ArrayList bargraph = new ArrayList();
		bargraph.add(reportcount);
		bargraph.add(reportccount);
		bargraph.add(reportLovedcount);
		return bargraph;
	}

	@RequestMapping(value = "survey/reportweekbarcount", method = RequestMethod.GET)
	public List weekpatientcount() {
		LOGGER.info("bar weekly Report Screen for the request-----> ::");
		List reportweekcount = new ArrayList();
		List carerreportccount = new ArrayList();
		List lovedcarercount = new ArrayList();
		try {

			reportweekcount = adminService.getweeklybarcount();
			carerreportccount = adminService.getCarerweeklybarcount();
			lovedcarercount = adminService.getLovedweeklybarcount();
		} catch (Exception ex) {
			// return "Error creating the entry;
		}

		ArrayList weekbargraph = new ArrayList();
		weekbargraph.add(reportweekcount);
		weekbargraph.add(carerreportccount);
		weekbargraph.add(lovedcarercount);
		return weekbargraph;
	}

	@RequestMapping(value = "survey/getuserdetails/{refno}", method = RequestMethod.GET)
	public String getUserDetails(@PathVariable(value = "refno") String refno) throws JsonProcessingException {

		List<SurveyBean> jsonHeartkidobj = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (refno != "" || refno != null) {
				getuserdetails.setRefno(refno);
			}
			jsonHeartkidobj = searchservice.getSearchRecords(getuserdetails);
		} catch (Exception ex) {
			LOGGER.info("ERROR in SEARCH" + ex.toString());
		}
		return mapper.writeValueAsString(jsonHeartkidobj);

	}

	@RequestMapping(value = "survey/downloadcmsfile/{page}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadjsonfiles(@PathVariable(value = "page") String page)
			throws IOException {
		ClassPathResource jsonFile;
		if (page.equalsIgnoreCase("questionarietem") || (page == "questionarietem"))
			jsonFile = new ClassPathResource("cmscontents/cms_content.json");
		else if (page.equalsIgnoreCase("thankyou") || (page == "thankyou"))
			jsonFile = new ClassPathResource("cmscontents/confirmationpage.json");
		else if (page.equalsIgnoreCase("disclaimerpatient") || (page == "disclaimerpatient"))
			jsonFile = new ClassPathResource("cmscontents/disclaimerpage.json");
		else if (page.equalsIgnoreCase("disclaimercarer") || (page == "disclaimercarer"))
			jsonFile = new ClassPathResource("cmscontents/disclaimercarer.json");
		else if (page.equalsIgnoreCase("emailtemp") || (page == "emailtemp"))
			jsonFile = new ClassPathResource("cmscontents/emailtemplate.json");

		else {
			jsonFile = new ClassPathResource("cmscontents/cms_homepage_content.json");
		}
		return ResponseEntity.ok().contentLength(jsonFile.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(jsonFile.getInputStream()));
	}

	@RequestMapping(value = "survey/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) {
		String name = "filetest";

		ClassLoader classLoader = getClass().getClassLoader();
		File imgfile = new File(classLoader.getResource("test.png").getFile());
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				/*
				 * String filename = getFilename(file);
				 */ ClassPathResource jsonFile = new ClassPathResource("heartkids.png");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imgfile.getAbsolutePath()));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded ";

			} catch (Exception e) {
				return "You failed to upload " + e.getMessage();
			}
		} else {
			return "You failed to upload  because the file was empty.";
		}
	}

	public String getFilename(MultipartFile file) {
		for (String cd : ((Part) file).getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("name")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;

	}

	@RequestMapping(value = "survey/updatejson/{pageid}", method = RequestMethod.POST)
	public String updatejsonfile(InputStream incomingData, @PathVariable(value = "pageid") String pageid)
			throws FileNotFoundException, IOException {
		StringBuilder jsonstringdata = null;
		OutputStream outputStream = null;
		BufferedReader config = null;
		Properties props = new Properties();
		String value = props.getProperty("name");
		String sesssionID = "dasdsadsa";
		File file = null;
		File config_file = null;
		ClassLoader classLoader = getClass().getClassLoader();
		if (pageid == "question" || pageid.equalsIgnoreCase("question"))
			file = new File(classLoader.getResource("dev/cms_content.json").getFile());
		else if (pageid == "thankyou" || pageid.equalsIgnoreCase("thankyou"))
			file = new File(classLoader.getResource("dev/confirmationpage.json").getFile());
		else if (pageid == "disclaimerpatient" || pageid.equalsIgnoreCase("disclaimerpatient"))
			file = new File(classLoader.getResource("dev/disclaimerpage.json").getFile());
		else if (pageid == "disclaimercarer" || pageid.equalsIgnoreCase("disclaimercarer"))
			file = new File(classLoader.getResource("dev/disclaimercarer.json").getFile());
		else if (pageid == "emailtemp" || pageid.equalsIgnoreCase("emailtemp"))
			file = new File(classLoader.getResource("dev/emailtemplate.json").getFile());
		else
			file = new File(classLoader.getResource("dev/cms_homepage_content.json").getFile());

		try {

			String applicationID = props.getProperty("CHD_version");
			outputStream = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()), 1024);

			BufferedReader reader = new BufferedReader(new InputStreamReader(incomingData));

			StringBuilder out = new StringBuilder();

			String line;

			int read = 0;

			byte[] bytes = new byte[2048];

			while ((read = incomingData.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.flush();
			outputStream.close();

			System.out.println("Successfully written---->" + file.getName());
			return "You successfully uploaded ";
		} catch (Exception e) {
			return "You failed to upload " + e.getMessage();
		}
	}

	@RequestMapping(value = "survey/updatePersonalInfo", method = RequestMethod.POST)
	public String savePersonalInfo(@RequestBody ProfileEntity profiledetails) {

		java.util.Date date = new java.util.Date();
		String updatedOn = new Timestamp(date.getTime()).toString();
		try {
			profiledetails.setUpdatedOn(updatedOn);
			profilerepo.save(profiledetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";

	}

	@RequestMapping(value = "survey/updatequestionarierecords/{adminuser}", method = RequestMethod.POST)
	public String savePersonalInfo(@PathVariable(value = "adminuser") String adminuser,
			@RequestBody QuestionarieEntity surveyrecord) {
		String refno = surveyrecord.getRefno();
		java.util.Date date = new java.util.Date();
		String updatedOn = new Timestamp(date.getTime()).toString();
		for (QuestionEntity ques : surveyrecord.getQuestionformat())
			try {
				ques.setUpdatedOn(updatedOn);
				ques.setUpdatedBy(adminuser);
				adminService.updateQuestionarierecrds(ques.getAnswer(), ques.getQuestion_id(), refno,
						ques.getUpdatedOn(), ques.getUpdatedBy());

			} catch (Exception e) {
				e.printStackTrace();
			}
		return "Success";

	}

	@RequestMapping(value = "survey/publish", method = RequestMethod.GET)
	public void publishSite() {

		try {
			adminService.publishSite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
