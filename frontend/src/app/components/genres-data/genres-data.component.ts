import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-genres-data',
  templateUrl: './genres-data.component.html',
  styleUrl: './genres-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
    ]
})
export class GenresDataComponent {
    genresData: any[] = [];
    columns: string[] = ['id', 'name'];
    isLoading = true;
    error: string | null = null;

    constructor(private dataService: DataTableService) {}

    ngOnInit() {
        console.log('Starting to load games data from:', `${this.dataService.apiUrl}/games`);

        this.dataService.getGenres().pipe(
            tap(data => {
              this.genresData = data.map(genre => ({
                ...genre
              }));
              this.isLoading = false;
            }),
            catchError(error => {
                console.error('Full error loading users data:', error);
                this.error = error.message || 'Failed to load users data';
                this.isLoading = false;
                return of([]);
            })
        ).subscribe();
      }
}
