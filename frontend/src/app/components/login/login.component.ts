import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../authentication/auth.service';
import { User } from '../../models/user.model';

@Component({
    standalone: true,
    imports: [
        FormsModule,
        CommonModule
        ],
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    credentials = {
    username: '',
    password: ''
    };

    errorMessage: string | null = null;
    isLoading = false;

    constructor(
    private router: Router,
    private location: Location,
    private authService: AuthService
    ) {}

    onLogin() {
        this.isLoading = true;
        this.errorMessage = null;

        if (!this.credentials.username || !this.credentials.password) {
            this.errorMessage = 'Пожалуйста, заполните все поля';
            this.isLoading = false;
            return;
        }

        this.authService.login(this.credentials.username, this.credentials.password)
            .subscribe({
                next: (user: User | null) => {
                    this.isLoading = false;
                    if (!user) {
                        this.errorMessage = 'Неверный логин или пароль';
                    }
                },
                error: (error: any) => {
                    this.isLoading = false;
                    if (error.status === 401) {
                      this.errorMessage = 'Неверный логин или пароль';
                    } else if (error.status === 0) {
                      this.errorMessage = 'Произошла ошибка на сервере. Попробуйте позже';
                    } else {
                      this.errorMessage = 'Произошла ошибка при входе. Попробуйте позже.';
                    }
                }
            });
    }

    getCsrfToken(): string {
        const cookieValue = document.cookie
            .split('; ')
            .find(row => row.startsWith('XSRF-TOKEN='))
            ?.split('=')[1];
        return cookieValue || '';
    }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  goBack() {
    this.location.back();
  }
}
