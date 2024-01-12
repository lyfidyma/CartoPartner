package com.carto.sn.export;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.carto.sn.entities.Localisation;
import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProjetExport extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.addHeader("Content-Disposition", "attachment; filename=Projets.xlsx");
		@SuppressWarnings("unchecked")
		List <Projet> list = (List<Projet>) model.get("list");
		List <Localisation> listLoc = (List<Localisation>) model.get("listLoc");
		List <Partenaire> listPart = (List<Partenaire>) model.get("listPart");
		Sheet sheet = workbook.createSheet("Projets");
		CreationHelper createHelper = workbook.getCreationHelper();
		short format = createHelper.createDataFormat().getFormat("dd-mm-yyyy");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format);
			
		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("");
		row0.createCell(1).setCellValue("");
		int rowNumero = 2;
		for(Localisation loc:listLoc) {
		row0.createCell(rowNumero++).setCellValue(loc.getLibelleLocalisation());
		
		}
		int rowNum = 1;
		int n=2;
		for(Projet proj:list){
			
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(proj.getNomProjet());
			if(proj.getPartenaire()!=null)
				row.createCell(1).setCellValue(proj.getPartenaire().getNomPartenaire());
			for(Localisation loc:listLoc) {
				
				if(proj.getLocalisation()!=null) {
					
					if(proj.getLocalisation().getLibelleLocalisation()==loc.getLibelleLocalisation()) {
						
						row.createCell(n++).setCellValue(proj.getType());
						
					}
				}
				
			}	
		
	}
	}

}
