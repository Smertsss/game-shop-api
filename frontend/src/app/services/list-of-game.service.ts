import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { GameData, GamePreview } from '../models/game.model';

@Injectable({
  providedIn: 'root'
})
export class ListOfGameService {
  public apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  private convertToPreview(gameData: GameData): GamePreview {
    return {
      id: gameData.id,
      title: gameData.name,
      genre: []
    };
  }

   getAllGames(): Observable<GamePreview[]> {
    return this.http.get<GameData[]>(`${this.apiUrl}/games`).pipe(
      map(gamesData => gamesData.map(this.convertToPreview)),
      tap(data => console.log('All games fetched:', data)),
      catchError(error => {
        console.error('Error fetching all games:', error);
        return throwError(() => error);
      })
    );
  }

  getNewGames(): Observable<GamePreview[]> {
    return this.http.get<GameData[]>(`${this.apiUrl}/games`).pipe(
      map(gamesData => gamesData
        .sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime())
        .map(this.convertToPreview)
      ),
      tap(data => console.log('New games fetched:', data)),
      catchError(error => {
        console.error('Error fetching new games:', error);
        return throwError(() => error);
      })
    );
  }

  getUpdatedGames(): Observable<GamePreview[]> {
    return this.http.get<GameData[]>(`${this.apiUrl}/games`).pipe(
      map(gamesData => gamesData
        .sort((a, b) => new Date(b.updateDate).getTime() - new Date(a.updateDate).getTime())
        .map(this.convertToPreview)
      ),
      tap(data => console.log('Updated games fetched:', data)),
      catchError(error => {
        console.error('Error fetching updated games:', error);
        return throwError(() => error);
      })
    );
  }

  private parseDate(dateString: string): number {
    const [day, month, year] = dateString.split('.');
    return new Date(`${year}-${month}-${day}`).getTime();
  }
}
