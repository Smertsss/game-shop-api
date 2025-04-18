import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { BehaviorSubject, Observable, catchError, of, tap } from 'rxjs';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
    ) {}

  login(login: string, password: string): Observable<User | null> {
    return this.userService.login({ login, password }).pipe(
      tap((user: User) => {
        this.currentUserSubject.next(user);
        this.router.navigate(['/home']);
      }),
      catchError(error => {
        console.error('Login error', error);
        return of(null);
      })
    );
  }

  logout(): Observable<any> {
      return this.userService.logout().pipe(
          tap(() => {
              this.currentUserSubject.next(null);
              this.router.navigate(['/']);
              window.location.reload();
          }),
          catchError(error => {
              console.error('Logout error:', error);
              return of(null);
          })
      );
  }

  checkAuthStatus(): void {
      this.userService.getCurrentUser().subscribe({
        next: (user: User) => {
          this.currentUserSubject.next(user);
          if (user) {
            this.router.navigate(['/home']); // Перенаправляем авторизованных пользователей
          } else {
            this.router.navigate(['/']); // Перенаправляем неавторизованных на корень
          }
        },
        error: () => {
          this.currentUserSubject.next(null);
          this.router.navigate(['/']); // При ошибке тоже на корень
        }
      });
    }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value !== null;
  }
}
