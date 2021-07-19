import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CalculateService {

  apiUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  calculateTimeByTechnician(idTechnician: string, weekNumber: number): Observable<any> {
    return this.http.get(this.apiUrl + 'calculate?idTechnical=' + idTechnician + '&weekNumber='+ weekNumber);
  }
}
