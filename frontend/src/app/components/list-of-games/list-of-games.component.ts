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

  constructor(
    public router: Router
    ) {}
}
