package com.example.start.Controllers;

import com.example.start.Entities.Offre;
import com.example.start.Entities.cordoffre;
import com.example.start.Entities.listoffre;
import com.example.start.Services.OffreService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offre")
@CrossOrigin(value = "*")
public class OffreController {
	@Autowired
	OffreService offreserv;
    @PostMapping("/addoffre")
    public Offre addoffre(@RequestBody cordoffre off) throws IOException {
    
    	
        return  offreserv.addoffre(off) ;
    }
    @GetMapping("/getoffre/{idemp}")
    public List<listoffre> getoffre(@PathVariable Long idemp) throws IOException {
    
        return  offreserv.getoffre(idemp);
    }


}
