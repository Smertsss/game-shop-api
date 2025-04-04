import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';

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
    private http: HttpClient
    ) {}

    onLogin() {
        console.log('Login attempt with:', this.credentials);
        this.isLoading = true;
        this.errorMessage = null;

        if (!this.credentials.username || !this.credentials.password) {
            this.errorMessage = 'Пожалуйста, заполните все поля';
            return;
        }

        const body = new URLSearchParams();
        body.set('username', this.credentials.username);
        body.set('password', this.credentials.password);

        const headers = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded',
        });

        const cookieValue = this.getCsrfToken();
        console.log('/api/login: ', body.toString(), 'XSRF-TOKEN: ', cookieValue);

        this.http.post<{ redirectUrl: string }>('/api/login', body.toString(), {
            headers,
            withCredentials: true
        })
        .pipe(
            catchError((error) => {
                this.isLoading = false;
                if (error.status === 200) {
                    this.errorMessage = 'Сервер успешно обработал запрос';
                } else if (error.status === 401) {
                    this.errorMessage = 'Неверный логин или пароль';
                } else if (error.status === 500) {
                    this.errorMessage = 'Произошла на сервере. Попробуйте позже';
                } else {
                    this.errorMessage = 'Произошла ошибка при входе. Попробуйте позже.';
                }

                console.log(this.errorMessage, ' Status: ', error.status);
                this.errorMessage = this.errorMessage;
                return throwError(() => error);
            })
        )
        .subscribe((response) => {
            this.isLoading = false;
            console.log(response);

            if (response) {
                console.log('Успешный вход - перенаправляем');
                this.router.navigateByUrl(response.redirectUrl || '/');
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
