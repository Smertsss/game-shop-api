import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { UserCreateDto } from '../../models/user.model';

@Component({
  selector: 'app-register',
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  userData: UserCreateDto = {
    firstName: '',
    secondName: '',
    username: '',
    email: '',
    login: '',
    password: '',
    roleIds: ['USER']
  };

  confirmPassword: string = '';
  errorMessage: string | null = null;
  isLoading = false;

  constructor(
    private router: Router,
    private userService: UserService
  ) {}

  onRegister() {
    this.isLoading = true;
    this.errorMessage = null;

    if (!this.validateForm()) {
      this.isLoading = false;
      return;
    }

    this.userService.createUser(this.userData).subscribe({
      next: (response) => {
        this.isLoading = false;
        console.log('Registration successful:', response);
        this.router.navigate(['/login'], {
          queryParams: { registered: 'true' }
        });
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Registration error:', error);

        if (error.status === 400 || error.status === 409) {
          this.errorMessage = error.error?.message || 'Пользователь с такими данными уже существует';
        } else {
          this.errorMessage = 'Произошла ошибка при регистрации. Попробуйте позже.';
        }
      }
    });
  }

  private validateForm(): boolean {
    if (!this.userData.firstName || !this.userData.secondName ||
        !this.userData.username || !this.userData.email ||
        !this.userData.login || !this.userData.password) {
      this.errorMessage = 'Пожалуйста, заполните все обязательные поля';
      return false;
    }

    if (this.userData.password !== this.confirmPassword) {
      this.errorMessage = 'Пароли не совпадают';
      return false;
    }

    if (this.userData.password.length < 6) {
      this.errorMessage = 'Пароль должен содержать не менее 6 символов';
      return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.userData.email)) {
      this.errorMessage = 'Введите корректный email адрес';
      return false;
    }

    return true;
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  goBack() {
    this.router.navigate(['/main-games']);
  }
}
