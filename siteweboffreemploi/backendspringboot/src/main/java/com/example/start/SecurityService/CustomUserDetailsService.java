package com.example.start.SecurityService;

import com.example.start.Entities.Employee;
import com.example.start.Repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepo personneRepo;
    @Override

   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee personne=personneRepo.findByMail(username).get(0);
        CustomUserDetails userDetails = null;
        if(personne!=null){
            userDetails=new CustomUserDetails();
            userDetails.setPersonne(personne);

        }else {
        	 throw new UsernameNotFoundException("Bad hhhhh credentials");
        }
        return userDetails;
    }

}
