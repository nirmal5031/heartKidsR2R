package com.tcs.survey.platform.util;
 
import java.util.List;
import java.util.Map;
 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 


import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.tcs.survey.platform.model.entity.RegisterDtoEntity;
/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
@Service
public class ExcelBuilder extends AbstractExcelView {
 
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
         List<RegisterDtoEntity> listHeartkidUser = (List<RegisterDtoEntity>)model.get("listheartkidusers");
        HSSFSheet sheet = workbook.createSheet("HeartKidReport");
        sheet.setDefaultColumnWidth(30);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        HSSFRow header = sheet.createRow(0);
         
        header.createCell(0).setCellValue("Reference Number");
        header.getCell(0).setCellStyle(style);
        
        header.createCell(1).setCellValue("User Type");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("Title");
        header.getCell(2).setCellStyle(style);
         
        header.createCell(3).setCellValue("First Name");
        header.getCell(3).setCellStyle(style);
         
      
         
        header.createCell(4).setCellValue("Last Name");
        header.getCell(4).setCellStyle(style);
        
        header.createCell(5).setCellValue("DoB");
        header.getCell(5).setCellStyle(style);
        
        header.createCell(6).setCellValue("Post Code");
        header.getCell(6).setCellStyle(style);
        
        header.createCell(7).setCellValue("State");
        header.getCell(7).setCellStyle(style);
                
        header.createCell(8).setCellValue("eMail");
        header.getCell(8).setCellStyle(style);
                
        header.createCell(9).setCellValue("Contact Agree");
        header.getCell(9).setCellStyle(style);
                
        header.createCell(10).setCellValue("Contact via Phone");
        header.getCell(10).setCellStyle(style);
        
        header.createCell(11).setCellValue("Contact via email");
        header.getCell(11).setCellStyle(style);
        
        header.createCell(12).setCellValue("Phone");
        header.getCell(12).setCellStyle(style);
        
        header.createCell(13).setCellValue("ethnicity");
        header.getCell(13).setCellStyle(style);
                
        header.createCell(14).setCellValue("Country");
        header.getCell(14).setCellStyle(style);
                
        header.createCell(15).setCellValue("Language");
        header.getCell(15).setCellStyle(style);
        
        header.createCell(16).setCellValue("Sex");
        header.getCell(16).setCellStyle(style);
        
        header.createCell(17).setCellValue("Heart Condition");
        header.getCell(17).setCellStyle(style);
        
        header.createCell(18).setCellValue("Surgery Held");
        header.getCell(18).setCellStyle(style);
        
        header.createCell(19).setCellValue("Surgery Held");
        header.getCell(19).setCellStyle(style);
        
        header.createCell(20).setCellValue("Surgery Delay count");
        header.getCell(20).setCellStyle(style);
        
        header.createCell(21).setCellValue("Travel Distance");
        header.getCell(21).setCellStyle(style);
        
        header.createCell(22).setCellValue("Heart Doctor Visit");
        header.getCell(22).setCellStyle(style);
        
        header.createCell(23).setCellValue("Local Doctor Visit");
        header.getCell(23).setCellStyle(style);
        
        header.createCell(24).setCellValue("Emergency visit");
        header.getCell(24).setCellStyle(style);
        
        header.createCell(25).setCellValue("Care Age");
        header.getCell(25).setCellStyle(style);
        
        header.createCell(26).setCellValue("Condition Called");
        header.getCell(26).setCellStyle(style);
        
        header.createCell(27).setCellValue("CHD work impact");
        header.getCell(27).setCellStyle(style);
        
       
        header.createCell(28).setCellValue("Child to Adult doc");
        header.getCell(28).setCellStyle(style);
        
        header.createCell(29).setCellValue("Anxiety Cond");
        header.getCell(29).setCellStyle(style);
                
        header.createCell(30).setCellValue("Cond Impact");
        header.getCell(30).setCellStyle(style);
        
        header.createCell(31).setCellValue("Current Work");
        header.getCell(31).setCellStyle(style);
         
        header.createCell(32).setCellValue("Work Time");
        header.getCell(32).setCellStyle(style);
        
        header.createCell(33).setCellValue("Disability benefits");
        header.getCell(33).setCellStyle(style);
        
        header.createCell(34).setCellValue("CHD work impact");
        header.getCell(34).setCellStyle(style);
        
        header.createCell(35).setCellValue("CHD change impact");
        header.getCell(35).setCellStyle(style);
        
        header.createCell(36).setCellValue("Missed School");
        header.getCell(36).setCellStyle(style);
        
        header.createCell(37).setCellValue("Edu Challng");
        header.getCell(37).setCellStyle(style);
        
        header.createCell(38).setCellValue("SchoolGrade");
        header.getCell(38).setCellStyle(style);
        
        header.createCell(39).setCellValue("FormalAssess");
        header.getCell(39).setCellStyle(style);
        
        header.createCell(40).setCellValue("ImpactQol");
        header.getCell(40).setCellStyle(style);
                
        header.createCell(41).setCellValue("ConditionImpactSchl");
        header.getCell(41).setCellStyle(style);
                
        header.createCell(42).setCellValue("ImpactSchool Desc");
        header.getCell(42).setCellStyle(style);
        
        header.createCell(43).setCellValue("MoneySpent(year)");
        header.getCell(43).setCellStyle(style);
        
        header.createCell(44).setCellValue("FirstSurgery");
        header.getCell(44).setCellStyle(style);
        
        header.createCell(45).setCellValue("HospitalSurgery");
        header.getCell(45).setCellStyle(style);
        
        header.createCell(46).setCellValue("EduSupportHosp");
        header.getCell(46).setCellStyle(style);
        
        header.createCell(47).setCellValue("TranspaedoAdult");
        header.getCell(47).setCellStyle(style);
        
        header.createCell(48).setCellValue("RateTransistion");
        header.getCell(48).setCellStyle(style);
        
        header.createCell(49).setCellValue("FeelSupport");
        header.getCell(49).setCellStyle(style);
        
        header.createCell(50).setCellValue("HeartKidSupport");
        header.getCell(50).setCellStyle(style);
        
        header.createCell(51).setCellValue("SocialWorker");
        header.getCell(51).setCellStyle(style);
        
        header.createCell(52).setCellValue("Pshycologist");
        header.getCell(52).setCellStyle(style);
        
        header.createCell(53).setCellValue("FamilySupport");
        header.getCell(53).setCellStyle(style);
        
        header.createCell(54).setCellValue("DedicatedNurse");
        header.getCell(54).setCellStyle(style);
        
        header.createCell(55).setCellValue("DedicatedNurseCHD");
        header.getCell(55).setCellStyle(style);
        
        header.createCell(56).setCellValue("DoctorCount");
        header.getCell(56).setCellStyle(style);
        
        header.createCell(57).setCellValue("TravelDistance");
        header.getCell(57).setCellStyle(style);
        
        header.createCell(58).setCellValue("AfterSurgeryFeel");
        header.getCell(58).setCellStyle(style);
        
        header.createCell(59).setCellValue("Heard SurveyBean");
        header.getCell(59).setCellStyle(style);
        
        header.createCell(60).setCellValue("Member Heartkid");
        header.getCell(60).setCellStyle(style);
        
        header.createCell(61).setCellValue("Support SurveyBean");
        header.getCell(61).setCellStyle(style);
        
        header.createCell(62).setCellValue("Use SurveyBean");
        header.getCell(62).setCellStyle(style);
        
        header.createCell(63).setCellValue("Desc Comment");
        header.getCell(63).setCellStyle(style);
        
        header.createCell(64).setCellValue("Registration Date");
        header.getCell(64).setCellStyle(style);
        
        header.createCell(65).setCellValue("Surgery Status");
        header.getCell(65).setCellStyle(style);
        
        header.createCell(66).setCellValue("Age");
        header.getCell(66).setCellStyle(style);
        
        header.createCell(67).setCellValue("Heart Doctor Visit");
        header.getCell(67).setCellStyle(style);
        
        header.createCell(68).setCellValue("Carer FirstName");
        header.getCell(68).setCellStyle(style);
        
        header.createCell(69).setCellValue("Carer LastName");
        header.getCell(69).setCellStyle(style);
        
        
        header.createCell(70).setCellValue("Carer LastName");
        header.getCell(70).setCellStyle(style);
        
        header.createCell(71).setCellValue("Carer Birthdate");
        header.getCell(71).setCellStyle(style);
        
        header.createCell(72).setCellValue("Carer Phone");
        header.getCell(72).setCellStyle(style);
        
        header.createCell(73).setCellValue("Carer Email");
        header.getCell(73).setCellStyle(style);
        // create data rows
        int rowCount = 1;
         
        for (RegisterDtoEntity searchreport : listHeartkidUser) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(searchreport.getRefno() );
            aRow.createCell(1).setCellValue(searchreport.getUsertype());
            aRow.createCell(2).setCellValue(searchreport.getTitle());
            aRow.createCell(3).setCellValue(searchreport.getFirstname());
            aRow.createCell(4).setCellValue(searchreport.getLastname());
            aRow.createCell(5).setCellValue(searchreport.getBirthdate());
            aRow.createCell(6).setCellValue(searchreport.getPostcode());
            aRow.createCell(7).setCellValue(searchreport.getState());
            aRow.createCell(8).setCellValue(searchreport.getEmail());
            aRow.createCell(9).setCellValue(searchreport.getContctviaphone());
           aRow.createCell(10).setCellValue(searchreport.getContctviaphone());
           aRow.createCell(11).setCellValue(searchreport.getPhone());
           aRow.createCell(12).setCellValue(searchreport.getPhone());
           aRow.createCell(13).setCellValue(searchreport.getEthnicity());
           aRow.createCell(14).setCellValue(searchreport.getCountrybirth());
           aRow.createCell(15).setCellValue(searchreport.getLanguage());
           aRow.createCell(16).setCellValue(searchreport.getSex());
           aRow.createCell(17).setCellValue(searchreport.getHeartconds());
           aRow.createCell(18).setCellValue(searchreport.getSurgeryheld());
           aRow.createCell(19).setCellValue(searchreport.getSurgerydelay());
           aRow.createCell(20).setCellValue(searchreport.getSurgerydelaycount());
           aRow.createCell(21).setCellValue(searchreport.getTrvlsurg());
           aRow.createCell(22).setCellValue(searchreport.getHeartdoc());
           aRow.createCell(23).setCellValue(searchreport.getLocaldoctorvisit());
           aRow.createCell(24).setCellValue(searchreport.getEmergdeptvisit());
           aRow.createCell(25).setCellValue(searchreport.getEmergdeptvisit());
           aRow.createCell(26).setCellValue(searchreport.getConditioncalld());
           aRow.createCell(27).setCellValue(searchreport.getChdimpactwork());
           aRow.createCell(28).setCellValue(searchreport.getChildtoadultdoc());
           aRow.createCell(29).setCellValue(searchreport.getAnxietycond());
           aRow.createCell(30).setCellValue(searchreport.getAnxietycondimpact());
           aRow.createCell(31).setCellValue(searchreport.getCurntwork());
           aRow.createCell(32).setCellValue(searchreport.getWorktime());
           aRow.createCell(33).setCellValue(searchreport.getDisabilityben());
           aRow.createCell(34).setCellValue(searchreport.getChdimpactwork());
           aRow.createCell(35).setCellValue(searchreport.getChangeimpactchd());
           aRow.createCell(36).setCellValue(searchreport.getMissschooldays());
           aRow.createCell(37).setCellValue(searchreport.getEductnchallng());
           aRow.createCell(38).setCellValue(searchreport.getSchoolgrd());
           aRow.createCell(39).setCellValue(searchreport.getFormalassess());
           aRow.createCell(40).setCellValue(searchreport.getImpactqol());
           aRow.createCell(41).setCellValue(searchreport.getCondimpactschl());
           aRow.createCell(42).setCellValue(searchreport.getCondimpactschooldesc());
           aRow.createCell(43).setCellValue(searchreport.getMoneyspentinyear());
           aRow.createCell(44).setCellValue(searchreport.getFrstsurgerysel());
           aRow.createCell(45).setCellValue(searchreport.getHosptlsurgery());
           aRow.createCell(46).setCellValue(searchreport.getEducsupporthosp());
           aRow.createCell(47).setCellValue(searchreport.getTranspaedtoadult());
           aRow.createCell(48).setCellValue(searchreport.getRatetransition());
           aRow.createCell(49).setCellValue(searchreport.getFeelsupport());
           aRow.createCell(50).setCellValue(searchreport.getHeartkidsupport());
           aRow.createCell(51).setCellValue(searchreport.getSocworker());
           aRow.createCell(52).setCellValue(searchreport.getPstcologist());
           aRow.createCell(53).setCellValue(searchreport.getFamilysuprt());
           aRow.createCell(54).setCellValue(searchreport.getDedicatedCHDnurse());
           aRow.createCell(55).setCellValue(searchreport.getDedicatedCHDnurse());
           aRow.createCell(56).setCellValue(searchreport.getDoctorcountsee());
           aRow.createCell(57).setCellValue(searchreport.getTraveldistdoc());
           aRow.createCell(58).setCellValue(searchreport.getAftrsurgfeel());
           aRow.createCell(59).setCellValue(searchreport.getHeardheartkid());
           aRow.createCell(60).setCellValue(searchreport.getMemberheartkid());
           aRow.createCell(61).setCellValue(searchreport.getSupportheartkid());
           aRow.createCell(62).setCellValue(searchreport.getUseheartkid());
           aRow.createCell(63).setCellValue(searchreport.getDesccommentsany());
           aRow.createCell(64).setCellValue(searchreport.getRegistrationdate());
           aRow.createCell(65).setCellValue(searchreport.getStatus());
           aRow.createCell(66).setCellValue(searchreport.getAge());
           aRow.createCell(67).setCellValue(searchreport.getAge());
           aRow.createCell(68).setCellValue(searchreport.getCarerfirstname());
           aRow.createCell(69).setCellValue(searchreport.getCarerlastname());
           aRow.createCell(71).setCellValue(searchreport.getCarerbirthdate());
           aRow.createCell(72).setCellValue(searchreport.getCarerphone());
           aRow.createCell(73).setCellValue(searchreport.getCareremail());
        
        
       }
   }

}
