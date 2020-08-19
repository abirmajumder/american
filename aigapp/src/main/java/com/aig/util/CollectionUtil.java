package com.aig.util;

import java.util.HashMap;
import java.util.Map;

public class CollectionUtil {

	@SuppressWarnings("rawtypes")
	public static Object getVal( Map map, String keyChain ) {
		String [] keys = keyChain.trim().split("\\.");
		if( keys.length == 1 ) {
			return map.get(keys[0]);
		}
		Object val = null;
		Map temp = map;
		int i = 0;
		for(; i<keys.length; i++) {
			val = temp.get(keys[i]);
			if( val instanceof Map ) {
				temp = (Map)val;
			} else {
				break;
			}
		}
		if( ++i < keys.length ) {
			val = null;
		}
		return val;
	}
	
	public static void main(String[] args) {
		Map<String,Map<String,String>> superMap = new HashMap<>();
		Map<String,String> inner = new HashMap<String,String>();
		inner.put("name", "Honda CRV");
		superMap.put("model", inner);
		System.out.println( getVal(superMap, "model.name") );
		String str = "ballys%20las%20vegas$balls%20las%20vegas%20sully$balls%20las%20vegas%20tequila";
		String [] addrs = str.split("\\$");
		System.out.println( addrs[0] + " - " + addrs[1] + " - " + addrs[2] );
	}
	
}

/*package com.poitest;

import java.io.File;
import java.io.FileInputStream;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Readsheet {
   static XSSFRow row;
   public static void main(String[] args) throws Exception {
      FileInputStream fis = new FileInputStream(new File("C:\\\\Trading\\pi\\LinkExcel\\NiftyNext50 MW.xlsx"));
	  FileInputStream fis = new FileInputStream(new File("C:\\\\Users\\Abirlal\\Desktop\\Data\\Abir\\tcs\\address_list.xlsx"));
      
      XSSFWorkbook workbook = new XSSFWorkbook(fis);
      XSSFSheet spreadsheet = workbook.getSheetAt(0);
      Iterator < Row >  rowIterator = spreadsheet.iterator();
      
      while (rowIterator.hasNext()) {
         row = (XSSFRow) rowIterator.next();
         Iterator < Cell >  cellIterator = row.cellIterator();
         
         while ( cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            
            switch (cell.getCellType()) {
               case Cell.CELL_TYPE_NUMERIC:
                  System.out.print(cell.getNumericCellValue() + " \t\t ");
                  break;
               
               case Cell.CELL_TYPE_STRING:
                  System.out.print(
                  cell.getStringCellValue() + " \t\t ");
                  break;
               case Cell.CELL_TYPE_FORMULA:
            	   System.out.print(
                           cell.getCellFormula() + " \t\t ");
                           break;
            }
         }
         System.out.println();
      }
      fis.close();
   }
}*/

