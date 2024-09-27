package com.carto.sn.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carto.sn.entities.Partenaire;
import com.carto.sn.entities.Projet;
import com.carto.sn.entities.Region;
import com.carto.sn.entities.Type;
import com.carto.sn.export.ProjetExport;
import com.carto.sn.service.ICarto;
import com.carto.sn.service.PartenaireService;

@Controller
public class ExcelController {
	@Autowired
	private ICarto iCarto;
	@Autowired
	private PartenaireService partenaireService;
	
	@GetMapping("/excelExport")
	public ModelAndView exportVersExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new ProjetExport());
		List<Projet> list = iCarto.tousLesProjets();
		List <Region> listLoc = iCarto.toutesLesRegions();
		List <Partenaire> listPart = partenaireService.tousLesPartenaires();
		List <Type> listType = iCarto.tousLesTypes();
		mav.addObject("list", list);
		mav.addObject("listLoc", listLoc);
		mav.addObject("listPart", listPart);
		mav.addObject("listType", listType);
		
		return mav;
		
	}
	
}
