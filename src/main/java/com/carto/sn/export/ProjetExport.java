package com.carto.sn.export;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProjetExport extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.addHeader("Content-Disposition", "attachment; filename=Projets.xlsx");
		@SuppressWarnings("unchecked")
		List<Projet> list = (List<Projet>) model.get("list");
		List<Region> listLoc = (List<Region>) model.get("listLoc");
		List<Partenaire> listPart = (List<Partenaire>) model.get("listPart");
		List<Type> listType = (List<Type>) model.get("listType");
		Sheet sheet = workbook.createSheet("Projets");
		CreationHelper createHelper = workbook.getCreationHelper();
		short format = createHelper.createDataFormat().getFormat("dd-mm-yyyy");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format);

		Row row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("");
		row0.createCell(1).setCellValue("");
		int rowNumero = 2;
		for (Region loc : listLoc) {
			row0.createCell(rowNumero++).setCellValue(loc.getNomRegion());

		}
		int rowNum = 1;
		int n = 2;
		for (Projet proj : list) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(proj.getNomProjet());
			if (proj.getPartenaire() != null) {
				for (Partenaire p : proj.getPartenaire())
					row.createCell(1).setCellValue(p.getNomPartenaire());
			}
			for (Region r : listLoc) {
				for (Region re : proj.getRegion())
					if (r.getNomRegion() == re.getNomRegion()) {

						row.createCell(n++).setCellValue("x");

					}
			}

		}

	}

}
