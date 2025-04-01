// src/app/auth/login/login.component.ts
import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [FormsModule],
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {
    identifier: '',
    password: ''
  };

  constructor(
    private router: Router,
    private location: Location
  ) {}

  onLogin() {
    console.log('Login attempt with:', this.credentials);
    // Здесь будет логика входа
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  goBack() {
    this.location.back();
  }
}
