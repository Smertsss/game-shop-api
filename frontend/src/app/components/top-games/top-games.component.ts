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
  selector: 'app-top-games',
  templateUrl: './top-games.component.html',
  styleUrl: './top-games.component.css',
  standalone: true,
  imports: [
    FormsModule,
    ListOfGamesComponent,
    CommonModule
  ]
})
export class TopGamesComponent {
  isLoading = true;
  error: string | null = null;
  games: GamePreview[] = [];

}
