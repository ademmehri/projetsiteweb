import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { entreprise } from '../models/entrprise.model';
import { EntrepriseService } from '../services/entreprise.service';
import { employeur } from '../models/employeur.model';
import { ActivatedRoute, Router } from '@angular/router';
import { employee } from '../models/employee.model';
import { UserService } from '../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signupp',
  templateUrl: './signupp.component.html',
  styleUrls: ['./signupp.component.css']
})
export class SignuppComponent {
  result1="";
  result2="";
  result3="";
  result4="";
  result5=""
  result6=""
  s=""
  timg=""
 mp!:string
  file!:File
  cin!:string
  num!:string
  result!:employee
  url=""
  formsignin!:FormGroup;
  sp="";
  constructor(private fb:FormBuilder,private userserv:UserService,private route:Router,private router:ActivatedRoute){
    this.formsignin=this.fb.group(
      {
        "nom":["",Validators.required],
        "numero":["",Validators.required],
         "email":["",[Validators.required,Validators.email]],
        "mp":["",Validators.required],
    
        
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


    if(this.sp==""){
      this.result2="Spécialité vide!!!!";
     
    }
    else{
      this.result2="";
    }
    this.num=JSON.stringify(this.formsignin.controls['numero'].value)
    if(this.num.length!=8){
      this.result5="numero incorrect!!!!";

    }
    else{
      this.result5="";
    }
    if(this.s==""){
      this.result6="gouvernerat vide!!!!";

    }
    else{
      this.result6="";
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
    if(this.url==""){
      this.timg="Choisie une image";
    }
    else{
      this.timg=""
    }


  
if(this.formsignin.valid && this.url!=""){
  console.log("ok1");

  let emp:employee=new employee();


  emp.nom=this.formsignin.controls['nom'].value.replace(/ /g,'').toLowerCase();
emp.role="entreprise";
  emp.mail=this.formsignin.controls['email'].value
  emp.password=this.formsignin.controls['mp'].value;
  emp.cp=emp.password
  emp.gouvernerat=this.s
  emp.num=this.formsignin.controls['numero'].value
  emp.specialite=this.sp
  console.log(emp);


  
  this.userserv.addemployee(emp).subscribe(
  (ch)=>{
 this.result=ch
 this.userserv.addfile(this.file,this.result.mail).subscribe(
  res=>{
    Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Employeur enrigistrer',
      showConfirmButton: false,
      timer: 1500
    })
    this.userserv.sendemail(this.result.mail).subscribe(
      res=>{
        console.log(res)
      }
    )
    this.route.navigate(["packpayment/"+this.result.id]);
  }
 )

   }
  )

 



}


  }
  onselectfile(e: any){
    if(e.target.files){
      var reader=new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      this.file=e.target.files[0]
      reader.onload=(event:any)=>{
        this.url=event.target.result;
      }
    }
  
  }
      onselect(e:any){
      console.log(this.s);
      this.s=e.target.value;
      console.log(this.s);
    }
    onselecte(e:any){
      console.log(this.s);
      this.sp=e.target.value;
      console.log(this.s);
    }
  
  

  




}
