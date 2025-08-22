import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { GamePreview } from '../../models/game.model';

@Component({
  selector: 'app-list-of-games',
  templateUrl: './list-of-games.component.html',
  styleUrl: './list-of-games.component.css',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class ListOfGamesComponent {
  @Input() data: GamePreview[] = [];

  constructor(public router: Router) {}

  getFirstImageUrl(game: GamePreview): string {
    if (game.images && game.images.length > 0) {
      return `http://localhost:8080/api/images/Game/${game.images[0]}`;
    }
    return '';
  }

  hasImages(game: GamePreview): boolean {
    return !!(game.images && game.images.length > 0);
  }

  navigateToGame(game: GamePreview): void {
    this.router.navigate(['/']);
  }
}
