import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private apiUrl = 'http://localhost:8080/api/images';

  constructor(private http: HttpClient) { }

  uploadImages(entityType: string, files: File[]): Observable<string[]> {
    const formData = new FormData();
    files.forEach(file => formData.append('files', file));

    return this.http.post<string[]>(`${this.apiUrl}/upload/${entityType}`, formData);
  }

  getImageUrl(entityType: string, imageName: string): string {
    return `${this.apiUrl}/${entityType}/${imageName}`;
  }
}
