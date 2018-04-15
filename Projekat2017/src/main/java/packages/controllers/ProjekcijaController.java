package packages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import packages.services.ProjekcijaService;

@RestController
@RequestMapping(value="/app")
public class ProjekcijaController {

	@Autowired
	private ProjekcijaService ps;
	
	
	
}
