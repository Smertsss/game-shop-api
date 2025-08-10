import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ListOfGamesComponent } from '../list-of-games/list-of-games.component';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-update-games',
  templateUrl: './update-games.component.html',
  styleUrl: './update-games.component.css',
  standalone: true,
  imports: [
    FormsModule,
    ListOfGamesComponent,
    CommonModule
  ]
})
export class UpdateGamesComponent {
  isLoading = true;
  error: string | null = null;

}
