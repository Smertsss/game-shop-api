import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, UserCreateDto, UserUpdateDto, AuthUser, AuthResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/clients';
  private authUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  createUser(user: UserCreateDto): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
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

  login(authUser: AuthUser): Observable<User> {
    const body = new URLSearchParams();
    body.set('username', authUser.login);
    body.set('password', authUser.password);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    return this.http.post<User>(`${this.authUrl}/login`, body.toString(), {
      headers,
      withCredentials: true // Важно для работы с сессиями и cookies
    });
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.authUrl}/logout`, {}, {
      withCredentials: true
    });
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.authUrl}/current`, {
      withCredentials: true
    });
  }
}
