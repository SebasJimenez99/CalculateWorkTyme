import { TypeHour } from './../interfaces/TypeHour.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CalculateService {

  apiUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  calculateTimeByTechnician(technician: any): Observable<TypeHour> {
    return this.http.get<TypeHour>(
      `${this.apiUrl}calculate?idTechnical=${technician.idTechnician}&weekNumber=${technician.weekNumber}`
    );
  }
}
