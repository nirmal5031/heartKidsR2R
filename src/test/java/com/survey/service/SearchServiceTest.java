package com.survey.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.bouncycastle.util.Strings;
import org.h2.expression.Function;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.survey.ServiceTest;
import com.tcs.survey.platform.model.SurveyBean;
import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionEntity;
import com.tcs.survey.platform.model.entity.RecordStatusEntity;
import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
import com.tcs.survey.platform.repository.SurveyRepository;
import com.tcs.survey.platform.repository.ProfileRepository;
import com.tcs.survey.platform.repository.RecordStatusRepository;
import com.tcs.survey.platform.repository.SurveyQueRepository;
import com.tcs.survey.platform.service.SearchService;

public class SearchServiceTest extends ServiceTest {

	@Autowired
	SurveyRepository surveyRepository;

	@Autowired
	RecordStatusRepository surveyrecords;

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	SurveyQueRepository surveyRepo;

	@Autowired
	SearchService searchService;


	
	
	 @Test public void testJSON2CSV() {
		 
		 ProfileEntity profile = new ProfileEntity();
		 
		 profile.setId(116);
		 profile.setFirstname("TESTTTTTTTTTTTTTTTTTTTTTTTTT");
		 profileRepo.save(profile);
	 }}