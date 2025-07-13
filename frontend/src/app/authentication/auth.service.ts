import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { BehaviorSubject, Observable, catchError, of, tap } from 'rxjs';
import { User, UserPreview } from '../models/user.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<UserPreview | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
    ) {}

  login(login: string, password: string): Observable<UserPreview | null> {
    return this.userService.login({ login, password }).pipe(
      tap((user: UserPreview) => {
        console.log('Roles after login:', user.roles);
        this.currentUserSubject.next(user);
        this.router.navigate(['/main-games']);
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
              this.router.navigate(['/main-games']);
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
      next: (user: UserPreview) => {
        this.currentUserSubject.next(user);
      },
      error: () => {
        this.currentUserSubject.next(null);
      }
    });
  }

  getCurrentUser(): UserPreview | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value !== null;
  }

  hasRole(roleName: string): boolean {
    const user = this.currentUserSubject.value;
    return user?.roles?.includes(roleName) || false;
  }

  getRoles(): string[] {
    return this.currentUserSubject.value?.roles || [];
  }
}
