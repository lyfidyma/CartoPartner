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
import com.carto.sn.service.PartenaireService;
import com.carto.sn.service.ProjetService;
import com.carto.sn.service.RegionService;
import com.carto.sn.service.TypeService;

@Controller
public class ExcelController {
	
	@Autowired
	private PartenaireService partenaireService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private TypeService typeService;
	
	@GetMapping("/excelExport")
	public ModelAndView exportVersExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new ProjetExport());
		List<Projet> list = projetService.tousLesProjets();
		List <Region> listLoc = regionService.toutesLesRegions();
		List <Partenaire> listPart = partenaireService.tousLesPartenaires();
		List <Type> listType = typeService.tousLesTypes();
		mav.addObject("list", list);
		mav.addObject("listLoc", listLoc);
		mav.addObject("listPart", listPart);
		mav.addObject("listType", listType);
		
		return mav;
		
	}
	
}
