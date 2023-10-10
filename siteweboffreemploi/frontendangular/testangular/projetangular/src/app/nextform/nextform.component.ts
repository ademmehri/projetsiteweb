import { Component } from '@angular/core';
import { employee } from '../models/employee.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { employeur } from '../models/employeur.model';
import { UserService } from '../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nextform',
  templateUrl: './nextform.component.html',
  styleUrls: ['./nextform.component.css']
})
export class NextformComponent {
  result1="";
  result2="";
  result3="";
  result4="";
  result5="";
  result6="";
  timg=""
d="";
  url="";
  email="";
  s="";
  sp=""
  file!:File;
  cv!:File
 mtp=false
 empl!:employee
num!:string
result!:employee
cin=""
mp!:string
reg=""
  formsignin!:FormGroup;
  constructor(private fb:FormBuilder,private route:Router,private userserv:UserService,private router:ActivatedRoute){
    this.router.params.subscribe(
      (param)=>{
        this.email=param['cin']
      }
    )
    this.router.params.subscribe(
      (param)=>{
        this.mp=param['mp']
      }
    )
    this.formsignin=this.fb.group(
      {
        "date":["",Validators.required],
        "exp":["",[Validators.required]],
        "rad1":["",Validators.required],
        "numero":["",Validators.required],
        "region":["",[Validators.required]],
        
      }
    )
  }
  ngOnInit(): void {
    this.userserv.getemployeecin(this.email).subscribe(
      res=>{
this.empl=res
      }
    )

   
  }

  onsubmit(){
    console.log(this.formsignin);
 
    if(this.s==""){
      this.result5="Spécialité vide!!!!"
    }
    else{
      this.result5="";
    }
   
    if(this.formsignin.controls['region'].errors?.['required']){
      this.result2="région vide!!!!"
    }
    else{
      this.result2="";
    }
    this.num=JSON.stringify( this.formsignin.controls['numero'].value);
    if( this.num.length!=8 ){
      this.result3=" numero incorrect!!!!"
    }
    else{
      this.result3="";
    }
    if(this.formsignin.controls['exp'].errors?.['required']){
      this.result4="experience vide!!!!"
    }
    else{
      this.result4="";
    }
    if(this.d==""){
      this.result6="date vide!!!!"
    }
    else{
      this.result6="";
    }
    if(this.url==""){
      this.timg="Choisie une image"
    }
    else{
      this.timg="";
    }
    if(this.sp==""){
    this.result1="gouvernerat required"
    }
    else{
  this.result1="";
    }
  
    if(this.formsignin.valid && this.url!="" && this.sp!="" && this.s!="" && this.d!=""){
      console.log("okkkk")
    let emp:employee=new employee();
     emp.date=this.d
emp.gouvernerat=this.sp
      emp.cin=this.email
      emp.num=this.num;
      console.log(emp.num)
      emp.specialite=this.s

      emp.city= this.formsignin.controls['region'].value
      emp.sexe=this.formsignin.controls['rad1'].value;
      emp.exp=this.formsignin.controls['exp'].value;
  emp.role="user"
  emp.password=this.mp
  emp.cp=emp.password
  emp.mail=this.empl.mail
  emp.nom=this.empl.nom
  emp.id=this.empl.id
  console.log(emp)
  this.userserv.updatemployee(emp).subscribe(
    res=>{
      console.log(res)
      this.result=res
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Employee enrigistrer',
        showConfirmButton: false,
        timer: 1500
      })
      this.userserv.addfile(this.file,this.result.mail).subscribe(
        res=>{
          console.log("res")
        
     
        }
      )
      console.log("cv"+this.cv)
      if(this.cv!=undefined){
        this.userserv.addcv(this.cv,this.result.mail).subscribe(
          res=>{
            console.log("res")
          }
        )
      }
      this.userserv.sendemail(emp.mail).subscribe(
        res=>{
          console.log(res)
        }
      )
      this.route.navigate(["login"]);
    }
  )





    
 }
    


  }
  onselectfile(e: any){
    if(e.target.files){
      var reader=new FileReader();
      
      reader.readAsDataURL(e.target.files[0]);
      this.file=e.target.files[0];
      console.log(this.file.name);
      reader.onload=(event:any)=>{
        this.url=event.target.result;
      }
    }
  
  }
  onselectcv(e: any){
    if(e.target.files){
      var reader=new FileReader();
      
      reader.readAsDataURL(e.target.files[0]);
      this.cv=e.target.files[0];
      console.log(this.file.name);
    
    }
  
  }
  onselect(e:any){
    console.log(this.s);
    this.s=e.target.value;
    console.log(this.s);
  }
  onselecte(e:any){
  
    this.d=e.target.value;
 
  }
  onselectee(e:any){
    this.sp=e.target.value;
  }




}
