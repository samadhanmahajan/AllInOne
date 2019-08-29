import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Register } from 'src/app/core/model/register';
import { UserService } from 'src/app/core/service/user-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private http: HttpClient, private snackbar: MatSnackBar, private router: Router) { }

  ngOnInit() { }

  register: Register = new Register();
  userService: UserService = new UserService(this.http, this.router);
  firstName =  new FormControl('', [Validators.required, Validators.maxLength(15),Validators.minLength(3)]);
  lastName = new FormControl('',[Validators.required,Validators.maxLength(15)]);
  emailId = new FormControl('',[Validators.required,Validators.maxLength(25),Validators.email]);
  mobileNum = new FormControl('',[Validators.required,Validators.maxLength(10)]);
  password = new FormControl('',[Validators.required,Validators.minLength(8)]);

  onRegister() {
    this.userService.registerUser(this.register).subscribe(

      (response: any): any => {
        if (response.statusCode == 201) {
          console.log(response);
          this.snackbar.open(
            "registered successfully..", "undo", { duration: 2500 }
          )
        } else {
          console.log(response);
          this.snackbar.open(
            "Registration Failed",
            "undo",
            {
              duration: 2500
            }

          )
        }
      }
    );


  }

}
