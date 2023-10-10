import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { employee } from '../models/employee.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  result1="";
  result2="";
  result3="";
  result4="";
 mp!:string
  file!:File
  cin!:string
  result!:employee
  formsignin!:FormGroup;
  constructor(private fb:FormBuilder,private userserv:UserService,private route:Router,private router:ActivatedRoute){
    this.formsignin=this.fb.group(
      {
        "nom":["",Validators.required],
        
      
         "email":["",[Validators.required,Validators.email]],
        "mp":["",Validators.required],
        "cin":["",[Validators.required]],
        
      }
    )
  }
  ngOnInit(): void {
   
  }
  onsubmit(){
    console.log(this.formsignin);
    if(this.formsignin.controls['nom'].errors?.['required']){
      this.result1="nom incorrect!!!!";
      console.log("ok");
    }
    else{
      this.result1="";
    }
    this.cin=JSON.stringify(this.formsignin.controls['cin'].value)
    if(this.cin.length!=8){
     this.result2="cin incorrect!!!!";
     
   }
   else{
     this.result2="";
   }
    if(this.formsignin.controls['email'].errors?.['email'] || this.formsignin.controls['email'].errors?.['required']){
      this.result3="email incorrect!!!!"
    }
    else{
      this.result3="";
    }
    this.mp=this.formsignin.controls['mp'].value
    if(this.mp.length<6){
      this.result4=" mot passe incorrect!!!!"
    }
    else{
      this.result4="";
    }

    //if(this.d==""){
     // this.result6="Sélectionner votre naissance!!!!"
   // }
    //else{
     // this.result6="";
   // }
  console.log(this.cin)
if(this.formsignin.valid && this.cin.length==8){
  console.log("ok1");

  let emp:employee=new employee();


 
  emp.cin=this.formsignin.controls['cin'].value;

  emp.nom=this.formsignin.controls['nom'].value;
emp.role="user";
  emp.mail=this.formsignin.controls['email'].value
  emp.password=this.formsignin.controls['mp'].value;
  emp.cp=this.formsignin.controls['mp'].value;
  console.log(emp);


  
  this.userserv.addemployee(emp).subscribe(
    (ch)=>{
 this.result=ch
 this.route.navigate(["nextform/"+this.result.cin+"/"+this.formsignin.controls['mp'].value]);
    }
  )
  this.route.navigate(["nextform/"+this.result.cin]);
 



}


  }
  onselectfile(e: any){
    if(e.target.files){
      var reader=new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      this.file=e.target.files[0]
      reader.onload=(event:any)=>{
        //this.url=event.target.result;
      }
    }
  
  }
     /* onselect(e:any){
      console.log(this.s);
      this.s=e.target.value;
      console.log(this.s);
    }
    onselecte(e:any){
      console.log(e.target.value);
      this.d=e.target.value;
    
    }*/

  


}
