import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Login } from 'src/app/core/model/login';
import { UserService } from 'src/app/core/service/user-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router, private snackbar: MatSnackBar, private http: HttpClient) { }

  login: Login = new Login();
  userService: UserService = new UserService(this.http, this.router);

  emailId = new FormControl('', [Validators.required,Validators.email]);
  password = new FormControl('', [Validators.required, Validators.maxLength(15)]);
  ngOnInit() {
  }

  onLogin() {

    this.userService.userLogin(this.login).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          console.log(response)
          localStorage.setItem('token', response.token);
          localStorage.setItem('name', response.statusMessage);
          localStorage.setItem('emailID', this.login.emailId);
          this.snackbar.open("login successfully...", "close", { duration: 2500 })
          console.log("Succcessfully logged in");
          this.router.navigateByUrl('/dashboard');

        }
        else {
          this.snackbar.open("Invalid username or password", "close", { duration: 2500 })
        }
      }
    )
  }

}