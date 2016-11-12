package com.survey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class TestClass {

	public static void main(String[] args) {
		try

		{

			FileInputStream file = new FileInputStream("C:/Users/237251/Downloads/AppRefresh_1.xlsx");

			// Create Workbook instance holding reference to .xlsx file

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(4);

			HashMap<String, Set<String>> totallist = new HashMap<String, Set<String>>();

			for (int i = 0; i < sheet.getRow(1).getLastCellNum(); i++) {

			

					Set<String> valuesadd = new TreeSet<String>();

					for (Row r : sheet) {

						if (r.getRowNum() > 2) {

							Cell c = r.getCell(i);

							if (c != null) {

								String answerval = "";

								switch (c.getCellType())

								{

								case Cell.CELL_TYPE_NUMERIC:

									answerval = Double.toString(c.getNumericCellValue()).trim();

									break;

								case Cell.CELL_TYPE_STRING:

									answerval = c.getStringCellValue().toLowerCase().trim();

									break;

								}

								valuesadd.add(answerval);

							}

						}

					totallist.put(sheet.getRow(2).getCell(i).getStringCellValue()+"--"+sheet.getRow(0).getCell(i).getStringCellValue()+"--"+sheet.getRow(1).getCell(i).getStringCellValue(), valuesadd);
				}
			}		
		  JSONArray prnt = new JSONArray();
		  JSONObject obj = null;
			Gson jsonval = new Gson();

		  for (Entry<String, Set<String>> entry : totallist.entrySet())
		  {
			  String key = entry.getKey();
			 Object value = entry.getValue();
			    
			    System.out.println("key-------"+jsonval.toJson(key.split("--")[0])+"---------vlue-----------"+value);
			  obj = new JSONObject();
			  obj.put("question_id",jsonval.toJson(key.split("--")[0]));
			  obj.put("options",value);
			  obj.put("type",key.split("--")[2]);
			  obj.put("Section",jsonval.toJson(key.split("--")[1]));
			  prnt.put(obj);
		  }
		  
		  /*for each key in hashmap
		  * {
		  * obj = new JSONObject();
		  * obj.put(key.split(0){get question_id},value)
		  * obj.put("type","DropDown")
		  * obj.put("Section",key.split(1){get section_name});
		  * }
		  * 
		  * prnt.put(obj);
		  *
		  */
		//prnt.put(obj);
 //System.out.println(prnt.put(obj));

			// convert java object to JSON format,
			// and returned as JSON formatted string
			//String json = jsonval.toJson(obj);
			FileWriter jsonfile = new FileWriter("D:\\clusterdropdownio.json");
jsonfile.write(prnt.toString());
jsonfile.flush();
jsonfile.close();
			file.close();

		}

		catch (Exception e)

		{

			e.printStackTrace();

		}

	}
}