import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre, GenreCreateDto, GenreUpdateDto } from '../models/genre.model';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private apiUrl = '/api/genres';

  constructor(private http: HttpClient) {}

  createGenre(genre: GenreCreateDto): Observable<Genre> {
    return this.http.post<Genre>(this.apiUrl, genre);
  }

  getGenreById(id: string): Observable<Genre> {
    return this.http.get<Genre>(`${this.apiUrl}/${id}`);
  }

  updateGenre(id: string, genre: GenreUpdateDto): Observable<Genre> {
    return this.http.patch<Genre>(`${this.apiUrl}/${id}`, genre);
  }

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiUrl);
  }

  addGamesToGenre(genreId: string, gameIds: string[]): Observable<Genre> {
    return this.http.post<Genre>(`${this.apiUrl}/${genreId}/games`, { gameIds });
  }
}
