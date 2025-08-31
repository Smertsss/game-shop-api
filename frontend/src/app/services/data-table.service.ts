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

  getGamesData() {
    return this.http.get<any[]>('/api/games/data');
  }

  getGenresData() {
    return this.http.get<any[]>('/api/genres/data');
  }

  getUsersData(): Observable<any[]> {
    return this.http.get<any[]>('/api/clients/data');
  }

  getCompaniesData(): Observable<any[]> {
    return this.http.get<any[]>('/api/companies/data');
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

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/genres/${id}`).pipe(
      catchError(error => {
        console.error('Error deleting genre:', error);
        return throwError(() => error);
      })
    );
  }
  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/clients/${id}`).pipe(
      catchError(error => {
        console.error('Error deleting user:', error);
        return throwError(() => error);
      })
    );
  }

  deleteCompany(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/companies/${id}`).pipe(
      catchError(error => {
        console.error('Error deleting company:', error);
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
