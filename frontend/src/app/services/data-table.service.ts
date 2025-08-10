import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class DataTableService {
  public apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getGames(): Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/games`).pipe(
          tap(data => console.log('Games data fetched successfully:', data)),
          catchError(error => {
              console.error('Error fetching games:', error);
              return throwError(() => error);
          })
      );
  }

  getGenres(): Observable<any[]> {
    console.log('Fetching genres data from API...');
    return this.http.get<any[]>(`${this.apiUrl}/genres`).pipe(
      tap(() => console.log('Genres data fetched successfully'))
    );
  }

  getUsers(): Observable<any[]> {
    console.log('Fetching users data from API...');
    return this.http.get<any[]>(`${this.apiUrl}/clients`).pipe(
      tap(() => console.log('Users data fetched successfully'))
    );
  }

  getCompanies(): Observable<any[]> {
    console.log('Fetching companies data from API...');
    return this.http.get<any[]>(`${this.apiUrl}/companies`).pipe(
      tap(() => console.log('Companies data fetched successfully'))
    );
  }
}
