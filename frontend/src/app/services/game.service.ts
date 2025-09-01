import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Game, GameCreateDto, GameUpdateDto } from '../models/game.model';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl = 'http://localhost:8080/api/games';

  constructor(private http: HttpClient) {}

  createGame(game: GameCreateDto): Observable<Game> {
    return this.http.post<Game>(this.apiUrl, game);
  }

  getGameById(id: string): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => {
        console.error('Error fetching game:', error);
        return throwError(() => error);
      })
    );
  }

  updateGame(id: string, game: GameUpdateDto): Observable<Game> {
    return this.http.patch<Game>(`${this.apiUrl}/${id}`, game).pipe(
      catchError(error => {
        console.error('Error updating game:', error);
        return throwError(() => error);
      })
    );
  }

  deleteGame(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => {
        console.error('Error deleting game:', error);
        return throwError(() => error);
      })
    );
  }

  getAllGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.apiUrl);
  }

  addGenresToGame(gameId: string, genreIds: string[]): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/${gameId}/genres`, { genreIds });
  }

  removeGenreFromGame(gameId: string, genreId: string): Observable<Game> {
    return this.http.delete<Game>(`${this.apiUrl}/${gameId}/genres/${genreId}`);
  }

  uploadGameImage(gameId: string, file: File): Observable<string> {
    console.log('Uploading image for game:', gameId);
    console.log('File:', file.name, file.size, file.type);

    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiUrl}/${gameId}/images`, formData, {
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
    return this.http.delete<void>(`${this.apiUrl}/${gameId}/images/${imageName}`);
  }

  getAllGenres(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/genres');
  }
}
