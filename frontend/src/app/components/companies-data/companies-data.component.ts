import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../data-table/data-table.component';
import { DataTableService } from '../../services/data-table.service';
import { CommonModule } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';

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
  companiesData: any[] = [];
  columns: string[] = ['name', 'context', 'creationDate', 'users', 'games', 'images'];
  isLoading = true;
  error: string | null = null;

  constructor(
    private dataService: DataTableService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadCompanies();
  }

  loadCompanies() {
    this.dataService.getCompaniesData().pipe(
      tap(data => {
        this.companiesData = data.map(company => ({
          ...company,
          creationDate: new Date(company.creationDate).toLocaleDateString(),
          users: company.users?.toString() || '0',
          games: company.games?.toString() || '0',
          images: company.images?.toString() || '0'
        }));
        this.isLoading = false;
      }),
      catchError(error => {
        console.error('Full error loading companies data:', error);
        this.error = error.message || 'Failed to load companies data';
        this.isLoading = false;
        return of([]);
      })
    ).subscribe();
  }

  onEdit(companyID: string) {
    this.router.navigate(['/companies/edit', companyID]);
  }

  onDelete(companyID: string) {
    if (confirm('Вы уверены, что хотите удалить эту компанию?')) {
      this.dataService.deleteCompany(companyID).subscribe({
        next: () => {
          this.loadCompanies();
        },
        error: (error) => {
          console.error('Error deleting company:', error);
        }
      });
    }
  }
}
