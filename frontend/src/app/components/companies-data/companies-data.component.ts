import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-companies-data',
  templateUrl: './companies-data.component.html',
  styleUrl: './companies-data.component.css',
  standalone: true,
  imports: [
    FormsModule,
    DataTableComponent,
    CommonModule
    ]
})
export class CompaniesDataComponent {
  companyData: any[] = [];
  columns: string[] = ['id', 'name', 'context', 'creationDate'];
  isLoading = true;
  error: string | null = null;

  constructor(private dataService: DataTableService) {}

  ngOnInit() {
    console.log('Starting to load companies data from:', `${this.dataService.apiUrl}/companies`);

    this.dataService.getCompanies().pipe(
        tap(data => {
          this.companyData = data.map(company => ({
            ...company,
            creationDate: new Date(company.creationDate).toLocaleDateString()
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
