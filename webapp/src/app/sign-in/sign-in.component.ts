import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent {
  user: User;

  constructor(
    private router: Router,
    private userService: UserService,
    private cookieService: CookieService
  ) {
    this.user = new User();
  }

  onSubmit() {
    console.log('Attempting to Login User:', this.user);
    this.userService.login(this.user).subscribe((res) => {
      console.log('Response:', res);
      switch (res.code) {
        case 0:
          if (res.token)
            this.cookieService.set('token', res.token);
          if (res.data) {
            this.cookieService.set('username', res.data.username);
            this.cookieService.set('fname', res.data.fName);
            this.cookieService.set('lname', res.data.lName);
            this.cookieService.set('id', String(res.data.id));
            this.cookieService.set('regDate', res.data.regDate);
          }
          this.router.navigate(['/home']);
          break;
        default:
          // do something
      }
    });
  }
}
