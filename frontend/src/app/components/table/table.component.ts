import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { ConfirmationService } from 'primeng/api';
import { Report } from 'src/app/interfaces/Report.model';
import { ReportService } from 'src/app/services/report.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  providers: [ConfirmationService, ReportService]
})
export class TableComponent implements OnInit {

  reportDialog!: boolean;
  listReport: Report[] = [];
  report!: Report;
  reportEdit!: Report;
  submitted!: boolean;
  initialDate!: Date;
  finalDate!: Date;

  constructor(private confirmationService: ConfirmationService,
    private reportService: ReportService) { }

  ngOnInit(): void {
    this.getAllReports();
  }

  editReport(report: Report): void {
    this.report = { ...report }
    this.reportEdit = report;
    this.reportDialog = true;
    this.initialDate = new Date(report.initialDate);
    this.finalDate = new Date(report.finalDate);
  }

  deleteReport(report: Report): void {
    this.confirmationService.confirm({
      message: '¿Estás seguro de que quieres borrar el reporte #' + report.id + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.reportService.deleteReport(report).subscribe(success => {
          if (success == true) {
            Swal.fire({
              icon: 'success',
              title: 'El reporte ha sido eliminado',
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'El reporte ha no podido ser eliminado',
            });
          }
        });
        this.listReport = this.listReport.filter(val => val.id !== report.id);
        this.report = { id: 0, idTechnician: '', idService: '', initialDate: '', finalDate: '' };
      }
    });
  }

  hideDialog(): void {
    this.reportDialog = false;
    this.submitted = false;
  }

  getAllReports(): void {
    this.reportService.getAllReports().subscribe(success => {
      this.listReport = success;
    });
  }

  saveReport(): void {
    this.submitted = true;
    if (this.report.idTechnician.trim()) {
      const myFormat= 'yyyy-MM-DD HH:mm:ss';
      const finalDate = moment(this.finalDate).format(myFormat);
      const initialDate = moment(this.initialDate).format(myFormat);
      this.report.initialDate = initialDate;
      this.report.finalDate = finalDate;
      if(initialDate > finalDate){
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'La fecha u hora inicial son mayores que la fecha u hora final',
        });
        this.reportDialog = false;
      } else {
        this.reportService.updateReport(this.report).subscribe(success => {
          console.log(success);
          Swal.fire({
            icon: 'success',
            title: 'El reporte ha sido actualizado',
          });
          this.getAllReports();
        });
        this.reportDialog = false;
      }
    }
  }


}
