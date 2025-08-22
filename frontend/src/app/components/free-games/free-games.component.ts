import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ListOfGamesComponent } from '../list-of-games/list-of-games.component';
import { ListOfGameService } from '../../services/list-of-game.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { GamePreview } from '../../models/game.model';

@Component({
  selector: 'app-free-games',
  templateUrl: './free-games.component.html',
  styleUrl: './free-games.component.css',
  standalone: true,
  imports: [
    FormsModule,
    ListOfGamesComponent,
    CommonModule
  ]
})
export class FreeGamesComponent {
  isLoading = true;
  error: string | null = null;
  games: GamePreview[] = [];

  constructor(private gameService: ListOfGameService) {}

  ngOnInit(): void {
    this.loadFreeGames();
  }

  loadFreeGames(): void {
    this.isLoading = true;
    this.error = null;

    this.gameService.getFreeGames().pipe(
      tap(games => {
        this.games = games;
        this.isLoading = false;
      }),
      catchError(error => {
        this.error = 'Ошибка загрузки бесплатных игр';
        this.isLoading = false;
        console.error('Error loading free games:', error);
        return of([]);
      })
    ).subscribe();
  }
}
