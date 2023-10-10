package com.example.start.Controllers;

import com.example.start.Entities.AuthentifiactionModel;
import com.example.start.Entities.Employee;
import com.example.start.Entities.File;
import com.example.start.Entities.listemps;
import com.example.start.Entities.patronemp;
import com.example.start.Entities.rechercheemp;
import com.example.start.Repositories.EmployeeRepo;
import com.example.start.SecurityService.CustomUserDetails;
import com.example.start.SecurityService.CustomUserDetailsService;
import com.example.start.SecurityService.JwtUtil;

import com.example.start.Services.EmployeeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class EmployeeController {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private EmployeeRepo personneRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private EmployeeService personneService;
 


/*    @GetMapping("/{id}")
    public ResponseEntity<Employee> getpersonnedetails(@PathVariable("id") int id){
        Employee p = new Employee();
        p.setId(1);
       // p.setNom("mojtabah");
       // p.setPrenom("mehri");
        p.setMail("mehri.mojtabah@yahoo.com");
        p.setPassword("22222");
        p.setRole("client");
       // p.setRib("cb444");
       // return new ResponseEntity<personne>(p, HttpStatus.OK);
    }*/
    @PostMapping("/savefichier/{mail}")
    public String savefichier(@RequestParam("file") MultipartFile file,@PathVariable String mail) throws IOException {
        return personneService.addEmployeefile(mail, file);
    }
    @GetMapping("/sendemail/{mail}")
    public String sendemail(@PathVariable String mail) throws IOException, MessagingException {
        return personneService.sendemail(mail);
    }
    @GetMapping("/sendemailoffre/{mail}/{qui}")
    public String sendemail(@PathVariable String mail,@PathVariable String qui) throws IOException, MessagingException {
        return personneService.sendemailoffre(mail, qui);
    }
    @PostMapping("/addcv/{mail}")
    public String addcv(@RequestParam("file") MultipartFile file,@PathVariable String mail) throws IOException {
       
    	
        return personneService.addcv(mail, file);
    }

    @PostMapping("/save")
    public Employee savepersonne(@RequestBody Employee personne) throws IOException, MessagingException {
    	   personne.setPassword(encoder.encode(personne.getPassword()));
        return  personneService.addemployee(personne) ;
    }
    @PostMapping("/update")
    public Employee updateemployee(@RequestBody Employee e){
      
        return   personneService.updateemployee(e);
    }
    @PostMapping("/recherche")
    public List<patronemp> rechercheemployee(@RequestBody rechercheemp rech){
      return   personneService.getpatronemp(rech);
    }
    @GetMapping("/recherchegouver/{gouv}")
    public List<patronemp> rechercheemployeegouv(@PathVariable String gouv){
      return   personneService.getlistemployeegouv(gouv);
    }
    @GetMapping("/getemployeebyemail/{mail}")
    public Employee getemployee(@PathVariable String mail){

      return   personneService.getemployeebyemail(mail);
    }
    @GetMapping("/getemployeebyid/{id}")
    public Employee getemployee(@PathVariable Long id){
    
      return   personneService.getemployeebyid(id);
    }
    @GetMapping("/getemployeebycin/{cin}")
    public Employee getemployeebycin(@PathVariable String cin){
    
      return   personneService.getemployeebycin(cin);
    }
    @GetMapping("/motpasseoubliee/{mail}")
    public String motpasseoublie(@PathVariable String mail) throws MessagingException{
    
      return   personneService.motpasseoublie(mail);
    }
    @GetMapping("/getallemployee")
    public List<listemps> getallemployee() throws MessagingException{
    
      return   personneService.getallemployee();
    }
  

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody AuthentifiactionModel authModel) throws Exception {
    	System.out.println(authModel.getMail());
        String message="invalid username";
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        if(!personneRepo.findByMail(authModel.getMail()).isEmpty()){
            Employee user = personneRepo.findByMail(authModel.getMail()).get(0);
            System.out.println(user.getPassword());
            try {
            	   System.out.println("eeeeeee"+authModel.getMail());
                authManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getMail(), authModel.getPassword()));
                System.out.println("3");
                CustomUserDetails mud = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authModel.getMail());
                System.out.println("2");
                response.remove("message");
                response.put("personneid",user.getId());
               // response.put("personne name", user.getNom());
                response.put("role",user.getRole());
               // response.put("personne rib",user.getRib());
                response.put("personnemail",user.getMail());
                
            
               
                //response.put("personne password",user.getPassword());
                response.put("token", jwtUtil.generateToken(mud));
               
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            } catch (Exception ex) {
            	 System.out.println(ex.getMessage());
                message="invalid password";
                response.put("message", message);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/loginemp")
    public ResponseEntity<?> login(@RequestBody AuthentifiactionModel authModel) throws Exception {
    	System.out.println(authModel.getMail());
        String message="invalid username";
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        if(!personneRepo.findByMail(authModel.getMail()).isEmpty()){
            Employee user = personneRepo.findByMail(authModel.getMail()).get(0);
            try {
              //  authManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getMail(), authModel.getPassword()));
                CustomUserDetails mud = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authModel.getMail());
                response.remove("message");
                response.put("personneid",user.getId());
                response.put("role",user.getRole());
                response.put("personnemail",user.getMail());
             
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            } catch (Exception ex) {
            	  System.out.println(ex);
                message="invalid password";
                response.put("message", message);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}