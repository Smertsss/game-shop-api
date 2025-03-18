import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game, GameCreateDto, GameUpdateDto } from '../models/game.model';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl = '/api/games';

  constructor(private http: HttpClient) {}

  createGame(game: GameCreateDto): Observable<Game> {
    return this.http.post<Game>(this.apiUrl, game);
  }

  getGameById(id: string): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/${id}`);
  }

  updateGame(id: string, game: GameUpdateDto): Observable<Game> {
    return this.http.patch<Game>(`${this.apiUrl}/${id}`, game);
  }

  deleteGame(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAllGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.apiUrl);
  }

  addGenresToGame(gameId: string, genreIds: string[]): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/${gameId}/genres`, { genreIds });
  }

  addCompaniesToGame(gameId: string, companyIds: string[]): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/${gameId}/companies`, { companyIds });
  }
}
