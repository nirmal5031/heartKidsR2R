package com.tcs.survey.platform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tcs.survey.platform.model.SurveyBean;
import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionEntity;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.repository.RecordStatusRepository;
import com.tcs.survey.platform.repository.SurveyQueRepository;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SearchService {

	@Autowired
	SurveyRepository searchrepository;

	@Autowired
	RecordStatusRepository surveyrecords;

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	SurveyQueRepository surveyRepo;
	

	List<RegisterDtoEntity> searchvalue = new ArrayList<RegisterDtoEntity>();


	public JSONArray  exportheartkid(RegisterDtoEntity searchentity) throws JsonProcessingException, JSONException {
		
		return getProcessedJson(searchentity);
		}

	
public List<SurveyBean> getSearchRecords(RegisterDtoEntity searchentity) throws JsonProcessingException  {
		List<SurveyBean> surveysearchresult = null;
	
		if(searchentity.getRefno() =="") 
			searchentity.setRefno(null);
		String age;
		String agesplit[];
		String age1;
		String age2;
		List<SurveyBean> jsonsearchobject = null;
		age = searchentity.getAge();
		if (age != null) {
			if (age == " - ") {
				age1 = null;
				age2 = null;
			} else {
				agesplit = age.split("-", 0);
				String ageval1 = agesplit[0];
				String ageval2 = agesplit[1];
				if (ageval1.equalsIgnoreCase("empty")) {
					age1 = null;
					age2 = null;
		} else {
					age1 = ageval1;
					age2 = ageval2;
				}
			}
		} else {
			age1 = null;
			age2 = null;
		}
	
List<ProfileEntity> profileList  = profileRepo.getRecordStatus(searchentity.getUsertype(),searchentity.getStatus(),searchentity.getRefno(),searchentity.getSex(),searchentity.getState(),searchentity.getEthnicity(),searchentity.getCountrybirth(),age1,age2);

List<QuestionEntity> questionList = surveyRepo.getAllsurvey(searchentity.getConditioncalld(),searchentity.getHeartconds(),searchentity.getSurgerydelaycount(),searchentity.getSurgeryheld(),searchentity.getSurgerydelay(),searchentity.getTrvlsurg(),searchentity.getAnxietycond());
			
System.out.println("@@@@@@@@@@@@QProfile LIST COUNT@@@@@@@@@@"+profileList.size());

System.out.println("@@@@@@@@@@@@QUESTION LIST COUNT@@@@@@@@@@"+questionList.size());
surveysearchresult = 
				 profileList.parallelStream()	  			 
		  			 .collect(Collectors.toMap(p1 -> p1 , p -> getSurveyInfo(p.getRefno(), questionList)))
		  			 .entrySet()
		  			 .parallelStream()
					 .filter(f -> f.getValue() != null && !f.getValue().equals(""))
		  			 .map( m -> buildObject(m.getKey() , m.getValue()))
		  			 .filter(a -> a.getProfile() !=null && !a.getProfile().equals(""))
		  			 .collect(Collectors.toList());

			 return surveysearchresult;
			 
			 

	}
	
	
public List<SurveyBean> getPreviousRecords(String refno) throws JsonProcessingException {
		
		List<SurveyBean> surveysearchresult = null;
				
List<ProfileEntity> profileList  = profileRepo.getRecordStatus(null, null, refno, null, null, null, null, null, null);

List<QuestionEntity> questionList = surveyRepo.getAllsurvey(null,null,null, null, null, null, null);
			 surveysearchresult = 
				 profileList.parallelStream()	  			 
		  			 .collect(Collectors.toMap(p1 -> p1 , p -> getSurveyInfo(p.getRefno(), questionList)))
		  			 .entrySet()
		  			 .parallelStream()
					 .filter( f -> f.getValue() != null && !f.getValue().equals(""))
		  			 .map( m -> buildObject(m.getKey() , m.getValue()))
		  			 .collect(Collectors.toList());
	return surveysearchresult;
	}
	
	private SurveyBean buildObject(ProfileEntity profileEntity,  List<QuestionEntity> questionaire){
    	SurveyBean survey = new SurveyBean();
    	try
    	{
    	System.out.println("questionalkfdsf-------------"+questionaire.size());
    	if(questionaire.size() == 0)
    	{
    		System.out.println("INNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
    		survey.setProfile(null);
    		survey.setId(null);
    		survey.setQuestionaire(null);
    	}
    	else
    	{
    	survey.setId(profileEntity.getId());survey.setProfile(profileEntity);survey.setQuestionaire(questionaire);
    	}
    	}
    	catch(Exception io)
    	{
    		io.printStackTrace();
    	}
    	return survey;
    }

 
    private  List<QuestionEntity> getSurveyInfo(String refno, List<QuestionEntity> questionList) {
		
    		return questionList.parallelStream()
			    .filter(q -> q.getRefno().equals(refno))
			    .collect(Collectors.toList());
	}

 
    
    public JSONArray getProcessedJson(RegisterDtoEntity searchentity) throws JsonProcessingException, JSONException {
		return getflattenedjson(new JSONArray(new ObjectMapper().writeValueAsString(getSearchRecords(searchentity))));

		
	}
	
	
	private JSONArray getflattenedjson(JSONArray beanList)
	
	{
		
		try {
			for (int i = 0; i < beanList.length(); i++) {
				JSONObject bean = beanList.getJSONObject(i);
				
				JSONArray questionList =	bean.getJSONArray("questionaire");
				for(int j= 0; j < questionList.length(); j++)
				{
					JSONObject objects = questionList.getJSONObject(j);
					bean.put(objects.getString("section_id")+"_"+objects.getString("question_id"), objects.getString("answer"));
	           
			
				}
				
				JSONObject obj = bean.getJSONObject("profile").getJSONObject("recordStatus");
				
				 Iterator<String> keys = obj.sortedKeys();
				    while(keys.hasNext()){
				    	 String key = keys.next();
				    	bean.put("0_p_"+key, obj.get(key));
				    }
				    bean.getJSONObject("profile").remove("recordStatus");
				    bean.remove("questionaire");
				    bean.remove("id");
				    JSONObject profilebean = bean.getJSONObject("profile");
				    Iterator<String> profilebeankeys = profilebean.sortedKeys();
				    		  while(profilebeankeys.hasNext()){
							    	 String key = profilebeankeys.next();
							    	bean.put("0_p_"+key, profilebean.get(key));
							    }
				    		  bean.remove("profile");
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return beanList;
		
	}
    

}