import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/core/service/user-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.scss']
})
export class ForgotpasswordComponent implements OnInit {

  constructor(private router: Router,
    private http: HttpClient,
    private snackbar: MatSnackBar) { }


  userService: UserService = new UserService(this.http, this.router);
  emailID: string;
  password: string;
  confirmPassword: string

  ngOnInit() {
  }

  onReset() {
    if (this.password === this.confirmPassword) {
      this.userService.userLogin('/resetpassword?password=' + this.confirmPassword).subscribe(
        (response: any): any => {
          if (response.statusCode == 200) {
            localStorage.setItem('token', response.token);
            this.snackbar.open("Password reset successfully..", "close", { duration: 2500 })
          }
        }
      )
    }
    else {
      this.snackbar.open("passwords miss match..", "close", { duration: 2500 })
    }
  }
}
