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
  selector: 'app-main-games',
  templateUrl: './main-games.component.html',
  styleUrl: './main-games.component.css',
  standalone: true,
  imports: [
    FormsModule,
    ListOfGamesComponent,
    CommonModule
  ]
})
export class MainGamesComponent {
  isLoading = true;
  error: string | null = null;
  games: GamePreview[] = [];

  constructor(private gameService: ListOfGameService) {}

  ngOnInit(): void {
    console.log('Starting to load games data from:', this.gameService.apiUrl);

    this.gameService.getAllGames().pipe(
      catchError(error => {
        this.error = 'Failed to load games';
        console.error(error);
        return of([] as GamePreview[]);
      })
    ).subscribe({
      next: (games) => {
        this.games = games;
        this.isLoading = false;
      },
      error: (error) => {
        this.error = 'Failed to load games';
        console.error(error);
        this.isLoading = false;
      }
    });
  }
}
