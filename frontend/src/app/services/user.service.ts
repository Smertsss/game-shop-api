import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { User, UserPreview, UserCreateDto, UserUpdateDto, AuthUser, AuthResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/clients';
  private authUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  createUser(user: UserCreateDto): Observable<any> {
    return this.http.post(`${this.apiUrl}`, user).pipe(
      catchError((error: any) => {
        console.error('Create user error:', error);
        return throwError(() => error);
      })
    );
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  updateUser(id: string, user: UserUpdateDto): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  addRolesToUser(userId: string, roleIds: string[]): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/${userId}/roles`, { roleIds });
  }

  addGamesToUser(userId: string, gameIds: string[]): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/${userId}/games`, { gameIds });
  }

  login(authUser: AuthUser): Observable<UserPreview> {
    const body = new URLSearchParams();
    body.set('username', authUser.login);
    body.set('password', authUser.password);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    console.log('Sending login request');
    return this.http.post<UserPreview>(`${this.authUrl}/login`, body.toString(), {
      headers,
      withCredentials: true
    }).pipe(
      tap((response: UserPreview) => console.log('Login response:', response)),
      catchError((error: any) => {
        console.error('Login error:', error);
        return throwError(() => error);
      })
    );
  }

  register(userData: UserCreateDto): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.authUrl}/logout`, {}, {
      withCredentials: true
    });
  }

  getCurrentUser(): Observable<UserPreview> {
      return this.http.get<any>(`${this.authUrl}/current`, {
        withCredentials: true
      }).pipe(
        map((user: User) => ({
          id: user.id,
          username: user.username,
          email: user.email,
          online: user.online,
          roles: user.roles?.map(role => role.name)
        }))
      );
    }
}
