package com.tcs.survey.platform.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionEntity;
import com.tcs.survey.platform.model.entity.RecordStatusEntity;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.repository.RecordStatusRepository;
import com.tcs.survey.platform.repository.SurveyQueRepository;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.util.RandomNumGenerator;

@Service
public class UserRegistrationService {
	@Value("${REFERENCE_PREFIX}")
	public String REFERENCE_PREFIX;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${adminMailID}")
	public String adminMailID;
	
	@Value("${unSubscribeUserURL}")
	public String unSubscribeUserURL;

	@Value("${homecontFile}")
	public static final String homecontentfile = null;
	final static Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");

	RandomNumGenerator randomNumber = new RandomNumGenerator();

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private RecordStatusRepository recordRepository;

	@Autowired
	private SurveyQueRepository surveyquestionrepo;

	@Autowired
	private MailingService mailservice;
	DateFormat dateFormat = new SimpleDateFormat("YYMM");
	Date date = new Date();

	public String retrieveRegistrationCount() {
		int regCountNumber = recordRepository.registationcount();
		return Integer.toString(regCountNumber);
	}

	public String generateReferenceNumber() {
		String lstrefval = null;
		StringBuffer unquenum = new StringBuffer("00000");
		// String lstrefnum = "HKCHD1602DSERR000016";
		String dateval = dateFormat.format(date);
		int randomCount = randomNumber.randomcountgenerator();
		String randomnum = randomNumber.generateRandomString(randomCount).toUpperCase() + "Z";
		String lstrefnum = profileRepository.findbylastreferencenumber();
		String uniquekey = null;
		int uniquecounter = 0;
		if (lstrefnum == null) {
			lstrefval = "1";
			uniquecounter = Integer.parseInt(lstrefval);
			uniquekey = String.format("%06d", uniquecounter);

		} else {
			Matcher matcher = lastIntPattern.matcher(lstrefnum);
			if (matcher.find()) {
				String someNumberStr = matcher.group(1);
				uniquecounter = Integer.parseInt(someNumberStr);
				uniquecounter++;
				uniquekey = String.format("%06d", uniquecounter);
			}
		}
		String referencenumb = REFERENCE_PREFIX + dateval + randomnum + uniquekey;

		return referencenumb;

	}

	public ProfileEntity saveRegistrationInformation(ProfileEntity profileDtoEntity) {

		/*
		 * String refno = ValidateChdRecordExist(profileDtoEntity);
		 * 
		 * if(refno != null) { System.out.println("updating uer record");
		 * profileDtoEntity.setRefno(refno+"_1"); } System.out.println(
		 * "reference number is--->"+profileDtoEntity.getRefno());
		 */
		profileDtoEntity = profileRepository.save(profileDtoEntity);

		return profileDtoEntity;
	}

	public void saveUserrecordStatus(RecordStatusEntity recordEntity) {
		if (recordEntity != null) {
			recordRepository.save(recordEntity);
		}
	}

	public String ValidateChdRecordExist(ProfileEntity registerDtoEntity) {
		String refno = "";
		try {
			String firstname = registerDtoEntity.getFirstname();
			String lastname = registerDtoEntity.getLastname();
			String email = registerDtoEntity.getEmail();
			String dob = registerDtoEntity.getBirthdate();
			refno = (String) profileRepository.userrecordExist(firstname, lastname, email, dob);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return refno;
	}

	public String retrievepagecontent(String pagecontent) {
		String question_filepath;
		if (pagecontent.equalsIgnoreCase("homepagecontent")) {
			question_filepath = "classpath:/cmscontents/cms_homepage_content.json";
		} else if (pagecontent.equalsIgnoreCase("personalcontent")) {
			question_filepath = "classpath:/cmscontents/cms_profilepage_content.json";
		} else if (pagecontent.equalsIgnoreCase("disclaimerpatient")) {
			question_filepath = "classpath:/cmscontents/disclaimerpage.json";
		} else if (pagecontent.equalsIgnoreCase("disclaimercarer")) {
			question_filepath = "classpath:/cmscontents/disclaimercarer.json";
		} else if (pagecontent.equalsIgnoreCase("thankyou")) {
			question_filepath = "classpath:/cmscontents/confirmationpage.json";
		} else {
			question_filepath = "classpath:/cmscontents/cms_content.json";

		}
		StringBuilder sb = new StringBuilder();
		ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {});
		Resource resource = appContext.getResource(question_filepath);

		try {
			String line;
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String retrievepagecontentDev(String pagecontent) {
		String question_filepath;
		if (pagecontent.equalsIgnoreCase("homepagecontent")) {
			question_filepath = "classpath:/dev/cms_homepage_content.json";
		} else if (pagecontent.equalsIgnoreCase("personalcontent")) {
			question_filepath = "classpath:/dev/cms_profilepage_content.json";
		} else if (pagecontent.equalsIgnoreCase("disclaimerpatient")) {
			question_filepath = "classpath:/dev/disclaimerpage.json";
		} else if (pagecontent.equalsIgnoreCase("disclaimercarer")) {
			question_filepath = "classpath:/dev/disclaimercarer.json";
		} else if (pagecontent.equalsIgnoreCase("thankyou")) {
			question_filepath = "classpath:/dev/confirmationpage.json";
		} else {
			question_filepath = "classpath:/dev/cms_content.json";

		}
		StringBuilder sb = new StringBuilder();
		ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {});
		Resource resource = appContext.getResource(question_filepath);

		try {
			String line;
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void savedatajson(List<QuestionEntity> list, String refno, String createdBy) {

		
		int sectionID = Integer.parseInt(checkIfSectionExists(refno));
		int sectonval = Integer.parseInt(list.get(0).getSection_id());
		System.out.println("sectonval----"+sectonval);

		if(sectionID > sectonval)
		{
			System.out.println("sectionID <= sectonval is not greater than ");
		}
		else
		{
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setRefno(refno);
			list.get(i).setCreatedBy(createdBy);
		}
		surveyquestionrepo.save(list);

	}
	}
	private String checkIfSectionExists(String refno) {
		
		String sectionidvalue = recordRepository.checkifsectioncomplete(refno);
		
		System.out.println("SECTION"+sectionidvalue);
		return sectionidvalue;
		// TODO Auto-generated method stub
		
}

	public void updateUserrecordStatus(String refno, String CompletionLevel, String status) {

		recordRepository.updateCompletionLevelBy(refno, status, CompletionLevel);

	}

	public void sendMailToUser(String email, String userName, String refno) {
		// TODO Auto-generated method stub
		String subjectMess = "Thank you for your help with our Survey. Ref no: "+refno;
		StringBuilder contentBuilder = new StringBuilder();
		File file = null;
try
{
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("heartKidEmail.html").getFile());

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		while ((str = in.readLine()) != null) {
			contentBuilder.append(str);
		}
		in.close();
}
catch(Exception io)
{
	io.printStackTrace();
	
}
		String content = contentBuilder.toString();
		String result = MessageFormat.format(content, userName, refno);
		mailservice.mailingservice(email, userName, subjectMess, result);
	}

	public String sendMailToAdminUnsubscription(String unsubsReferenceNo) {
		// TODO Auto-generated method stub
		int ifRefExist = profileRepository.refNumberExist(unsubsReferenceNo);
		System.out.println("ifRefExist ---->"+ifRefExist);
		String subjectMess = "Request for unsubscription. Ref no: "+unsubsReferenceNo;
		String content = "User had requested to remove all his data from the database.</br> </br> Refno : "+unsubsReferenceNo;
		
		if(ifRefExist == 0)
		{
			return "RECORDNOTFOUND";
		}
		else
		{
		mailservice.mailingservice(adminMailID, unsubsReferenceNo, subjectMess, content);
		}
		return "SUCCESS";

	}
}
