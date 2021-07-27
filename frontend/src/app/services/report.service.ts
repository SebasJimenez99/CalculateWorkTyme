import { Report } from './../interfaces/Report.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as moment from 'moment/moment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  apiUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  createReport(report: Report): Observable<Report> {
    const myFormat= 'yyyy-MM-DD HH:mm:ss';
    const finalDate = moment(report.finalDate).format(myFormat);
    const initialDate = moment(report.initialDate).format(myFormat);
    report.finalDate = finalDate;
    report.initialDate = initialDate;
    return this.http.post<Report>(this.apiUrl + 'reports', report);
  }

  updateReport(report: Report): Observable<Report> {
    const idReport = report.id;
    return this.http.put<Report>(this.apiUrl + 'reports/' + idReport, report);
  }

  getAllReports(): Observable<Report[]> {
    return this.http.get<Report[]>(this.apiUrl + 'reports');
  }

  deleteReport(report: Report): Observable<Boolean> {
    const idReport = report.id;
    return this.http.delete<Boolean>(this.apiUrl + 'reports/' + idReport);
  }
}
