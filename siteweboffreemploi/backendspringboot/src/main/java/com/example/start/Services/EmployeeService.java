package com.example.start.Services;

import com.example.start.Entities.Employee;

import com.example.start.Entities.File;
import com.example.start.Entities.listemps;
import com.example.start.Entities.patronemp;
import com.example.start.Entities.rechercheemp;
import com.example.start.Repositories.EmployeeRepo;
import com.example.start.Repositories.FileRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
     private FileService fileService;
    @Autowired
    FileRepo fileemprep;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    Emailservice emailserv;
   

  public Employee addemployee(Employee emp) throws MessagingException{
	 if(employeeRepo.existsBymail(emp.getCin())) {
		 return null; 
	 }
        return employeeRepo.save(emp);
   }
  public String addEmployeefile(String mail, MultipartFile file) throws IOException {
		
		if(employeeRepo.existsBymail(mail)) {
			File f=fileService.addfile(file);
			Employee e=employeeRepo.findBymail(mail);
			
			e.getListemp().add(f);
			f.setEmp(e);
			employeeRepo.save(e);
			return "Fichier inserer";
		}
		
		return "personne non existe";
	}
  public String addcv(String mail, MultipartFile file) throws IOException {
		if(employeeRepo.existsBymail(mail)) {
			File f=fileService.addcv(file);
			Employee e=employeeRepo.findBymail(mail);
			
			e.getListemp().add(f);
			f.setEmp(e);
			employeeRepo.save(e);
			return "CV inserer";
		}
		
		return "personne non existe";
	}

    public Employee updateemployee(Employee employee) {
        Employee e;
        e=  employeeRepo.findById(employee.getId()).get();
        e.setCin(employee.getCin());
        e.setMail(employee.getMail());
        e.setDate(employee.getDate());
        e.setNum(employee.getNum());
        e.setDescription(employee.getDescription());
        e.setNom(employee.getNom());
        e.setPassword(encoder.encode(employee.getPassword()));
     e.setCity(employee.getCity());
        e.setSexe(employee.getSexe());
        e.setSpecialite(employee.getSpecialite());
        e.setExp(employee.getExp());
        e.setGouvernerat(employee.getGouvernerat());
        e.setDescription(employee.getDescription());
        e.setCp(employee.getCp());
        return employeeRepo.save(e) ;
    }

    public Employee getemployeebyid(Long id) {
    	return employeeRepo.findById(id).get();
    }
    public Employee getemployeebyemail(String email) {
    	return employeeRepo.findBymail(email);
    }
    public Employee getemployeebycin(String cin) {
    	return employeeRepo.findBycin(cin);
    }
    public List<patronemp> getpatronemp(rechercheemp rech){
    	System.out.println(rech.getSexe());
    	if(rech.getSexe().equals("tous")) {
    	
    	List<Employee> listspemp=employeeRepo.getemployees(rech.getGouvernerat(), rech.getSpe());
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    	pe.setCity(emp.getCity());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setDpe(rech.getSpe());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);
    		
    	}
    	return patemp;
    	}
    	List<Employee> listspemp=employeeRepo.getemployeessexe(rech.getGouvernerat(),rech.getSpe(), rech.getSexe());
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		pe.setCity(emp.getCity());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setDpe(rech.getSpe());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);}
    	
    	return patemp;
    }
    public List<patronemp> getlistemployeegouv(String gouver){
    	List<Employee> listspemp=employeeRepo.getemployeegouv(gouver);
    	
    	List<patronemp> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		patronemp pe=new patronemp();
    		System.out.println(emp.getGouvernerat());
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		pe.setRegion(emp.getGouvernerat());
    		pe.setCity(emp.getCity());
    		pe.setDpe(emp.getSpecialite());
    		pe.setFilee(file);
    		pe.setNum(emp.getNum());
    		pe.setId(emp.getId());
    		patemp.add(pe);
    		}
    	
    	return patemp;
    	
    }
    public String motpasseoublie(String mail) throws MessagingException {
    	Employee e=employeeRepo.findBymail(mail);
    	if(e==null) {
    		return "personne non existe";
    	}
    
        String link="votre mot passe est "+e.getCp();
     emailserv.send(e.getMail(), link);
     System.out.println("fffffffff");
    	return "Message envoyee";
    }
    public String sendemail(String email) throws MessagingException {
    	 String link="Bienvenue chez RestoJob";
         emailserv.send(email, link);
    	return "ok";
    }
    public String sendemailoffre(String email,String qui) throws MessagingException {
   	 String link="Vous avez une offre par "+qui;
        emailserv.send(email, link);
   	return "ok";
   }
    public List<listemps> getallemployee(){
	List<Employee> listspemp=employeeRepo.findAll();
    	
    	List<listemps> patemp=new ArrayList<>();
    	for(Employee emp: listspemp) {
    		listemps pe=new listemps();
    		File file= fileService.getfileemp(emp.getId());
    		pe.setNom(emp.getNom());
    		
    	
    		pe.setSpe(emp.getSpecialite());
    		pe.setFilee(file);
    		pe.setRole(emp.getRole());
    		patemp.add(pe);
    		}
    	
    	return patemp;
    }
	


}
