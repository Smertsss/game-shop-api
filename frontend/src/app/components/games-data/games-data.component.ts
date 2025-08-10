import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-games-data',
  templateUrl: './games-data.component.html',
  styleUrl: './games-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
    ]
})
export class GamesDataComponent {
  gamesData: any[] = [];
  //columns: string[] = ['ID', 'Название', 'Контекст', 'Цена', 'Дата создания', 'Дата обновления'];
  columns: string[] = ['id', 'name', 'context', 'cost', 'creationDate', 'updateDate'];
  isLoading = true;
  error: string | null = null;

  constructor(private dataService: DataTableService) {}

  ngOnInit() {
    console.log('Starting to load games data from:', `${this.dataService.apiUrl}/games`);

    this.dataService.getGames().pipe(
        tap(data => {
          this.gamesData = data.map(game => ({
            ...game,
            creationDate: new Date(game.creationDate).toLocaleDateString(),
            updateDate: new Date(game.updateDate).toLocaleDateString()
          }));
          this.isLoading = false;
        }),
        catchError(error => {
            console.error('Full error loading games data:', error);
            this.error = error.message || 'Failed to load games data';
            this.isLoading = false;
            return of([]);
        })
    ).subscribe();
  }
}
