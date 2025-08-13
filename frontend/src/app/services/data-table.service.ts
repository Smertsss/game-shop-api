import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DataTableService {
  public apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

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
      tap(() => console.log('Genres data fetched successfully')),
      catchError(error => {
          console.error('Error fetching games:', error);
          return throwError(() => error);
      })
    );
  }

  getUsers(): Observable<any[]> {
    console.log('Fetching users data from API...');
    return this.http.get<any[]>(`${this.apiUrl}/clients`).pipe(
      tap(() => console.log('Users data fetched successfully')),
      catchError(error => {
         console.error('Error fetching games:', error);
         return throwError(() => error);
      })
    );
  }

  getCompanies(): Observable<any[]> {
    console.log('Fetching companies data from API...');
    return this.http.get<any[]>(`${this.apiUrl}/companies`).pipe(
      tap(() => console.log('Companies data fetched successfully')),
      catchError(error => {
         console.error('Error fetching games:', error);
         return throwError(() => error);
      })
    );
  }

  getGameById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/games/${id}`).pipe(
      catchError(error => {
        console.error('Error fetching game:', error);
        return throwError(() => error);
      })
    );
  }

  updateGame(id: string, gameData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/games/${id}`, gameData).pipe(
      catchError(error => {
        console.error('Error updating game:', error);
        return throwError(() => error);
      })
    );
  }

  deleteGame(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/games/${id}`).pipe(
      catchError(error => {
        console.error('Error deleting game:', error);
        return throwError(() => error);
      })
    );
  }

  uploadGameImage(gameId: string, file: File): Observable<string> {
    console.log('Uploading image for game:', gameId);
    console.log('File:', file.name, file.size, file.type);

    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiUrl}/games/${gameId}/images`, formData, {
      responseType: 'text'
    }).pipe(
      tap(response => console.log('Upload response:', response)),
      catchError(error => {
        console.error('Error uploading image:', error);
        console.error('Error status:', error.status);
        console.error('Error message:', error.message);
        return throwError(() => new Error('Failed to upload image: ' + error.message));
      })
    );
  }

  deleteGameImage(gameId: string, imageName: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/games/${gameId}/images/${imageName}`);
  }
}
