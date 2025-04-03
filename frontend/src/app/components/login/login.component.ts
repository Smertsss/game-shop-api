import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { of, throwError } from 'rxjs';

@Component({
    standalone: true,
    imports: [FormsModule],
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
            'Content-Type': 'application/x-www-form-urlencoded'
        });

        console.log('/api/login: ', body.toString());

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
                } else {
                    this.errorMessage = 'Произошла ошибка при входе. Попробуйте позже.';
                }

                console.log(this.errorMessage, ' Status: ', error.status);
                //return of(null);
                return throwError(() => error);
            })
        )
        .subscribe((response) => {
            this.isLoading = false;
            console.log(response);

            if (response) {
                console.log('Успешный вход - перенаправляем');
                this.router.navigateByUrl(response.redirectUrl || '/home');
            }
        });
    }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  goBack() {
    this.location.back();
  }
}
