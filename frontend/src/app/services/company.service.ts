import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company, CompanyCreateDto, CompanyUpdateDto } from '../models/company.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) {}

  createCompany(company: CompanyCreateDto): Observable<Company> {
    return this.http.post<Company>(this.apiUrl, company);
  }

  getCompanyById(id: string): Observable<Company> {
    return this.http.get<Company>(`${this.apiUrl}/${id}`);
  }

  updateCompany(id: string, company: CompanyUpdateDto): Observable<Company> {
    return this.http.patch<Company>(`${this.apiUrl}/${id}`, company);
  }

  deleteCompany(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.apiUrl);
  }

  addUsersToCompany(companyId: string, userIds: string[]): Observable<Company> {
    return this.http.post<Company>(`${this.apiUrl}/${companyId}/users`, { userIds });
  }

  addGamesToCompany(companyId: string, gameIds: string[]): Observable<Company> {
    return this.http.post<Company>(`${this.apiUrl}/${companyId}/games`, { gameIds });
  }
}
