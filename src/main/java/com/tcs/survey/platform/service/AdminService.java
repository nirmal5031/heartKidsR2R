package com.tcs.survey.platform.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcs.survey.platform.model.entity.CreateAdminUser;
import com.tcs.survey.platform.repository.AdminRepository;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.repository.RecordStatusRepository;
import com.tcs.survey.platform.repository.SurveyQueRepository;
import com.tcs.survey.platform.repository.SurveyRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository createadminrepository;

	@Autowired
	RecordStatusRepository surveyrecords;

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	SurveyQueRepository surveyRepo;

	@Autowired
	MailingService mailingService;

	@Value("${emailContent}")
	String emailContent;

	public int deleteUserByReferenceNumber(final String referenceNumber) {
		int questiondel = 0;
		int profiledel = 0;
		try {
			questiondel = surveyRepo.deleteQuestionByRefNumber(referenceNumber);
		} catch (Exception io) {
			io.printStackTrace();
		}
		try {
			profiledel = profileRepo.deleteProfileByRefNumber(referenceNumber);
		} catch (Exception io) {
			io.printStackTrace();
		}

		return profiledel;
	}

	public int doesUserExist(final String userName) {
		return createadminrepository.adminUserExist(userName);
	}

	public void saveAdminUser(final @RequestBody CreateAdminUser createuser) {
		createadminrepository.save(createuser);
	}

	public List<CreateAdminUser> findAllUser() {
		return (List<CreateAdminUser>) createadminrepository.findAll();
	}

	public int deleteUseradmin(final String userName) {
		return createadminrepository.deleteUsersadmin(userName);
	}

	public int resetUseradmin(final String userName) {
		return createadminrepository.resetUsersadmin(userName);
	}

	public List<CreateAdminUser> findAdminUser(final String userName) {
		return (List<CreateAdminUser>) createadminrepository.findOne(userName);
	}

	public List<Object> getRecordCount() {
		List<Object> recordsList = surveyrecords.getRecordCount();
		return recordsList;

	}

	public List getLovedbarcount() {
		return surveyrecords.lovedbarcount();
	}

	public List getCarerbarcount() {
		return surveyrecords.carerbarcount();
	}

	public List getPatientbarcount() {
		return surveyrecords.patientbarcount();
	}

	public List getweeklybarcount() {
		return surveyrecords.patientweeklybarcount();
	}

	public List getCarerweeklybarcount() {
		return surveyrecords.carerweeklybarcount();
	}

	public List getLovedweeklybarcount() {
		return surveyrecords.lovedweeklybarcount();
	}

	public void updateQuestionarierecrds(String answer, String question_id, String refno, String updatedOn,
			String updatedBy) {
		// TODO Auto-generated method stub
		System.out.println("ADMIN SERVICE___" + answer + question_id + refno);
		surveyRepo.updateQuestionRecords(answer, question_id, refno, updatedOn, updatedBy);

	}

	public void publishSite() throws IOException {
		// TODO Auto-generated method stub

		ClassPathResource jsonFile;
		ClassLoader classLoader = getClass().getClassLoader();
		// jsonFile = new ClassPathResource("dev/");

		File srcFolder = new File(classLoader.getResource("dev").getPath());

		File destFolder = new File(classLoader.getResource("cmscontents").getPath());

		if (!srcFolder.exists()) {
			System.out.println("Directory does not exist.");
			// just exit
			System.exit(0);
		} else {

			try {
				copyFolder(srcFolder, destFolder);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	public void sendMailtoAdmin(String name, String password, String toemail, String username) throws IOException {
		// TODO Auto-generated method stub
		String subjectMess = "Your new login, " + username + ", has been created";
		StringBuilder contentBuilder = new StringBuilder();

		File file = null;

		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("NewAdminEmail.html").getFile());

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		while ((str = in.readLine()) != null) {
			contentBuilder.append(str);
		}
		in.close();
		String content = contentBuilder.toString();
		String result = MessageFormat.format(content, name, username, password);

		mailingService.mailingservice(toemail, name, subjectMess, result);

	}

	public void sendMailtoReset(String username, String decrptpass, String emailid) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String subjectMess = "HeartKids R2R login reset successful for, " + username;
		StringBuilder contentBuilder = new StringBuilder();

		File file = null;
try
{
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("ResetEmail.html").getFile());

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
		String result = MessageFormat.format(content, username, decrptpass);

		mailingService.mailingservice(emailid, username, subjectMess, result);

		
	}
}
