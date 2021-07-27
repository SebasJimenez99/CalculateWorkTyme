import { ReportService } from '../../services/report.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Report } from 'src/app/interfaces/Report.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
  providers: [ReportService]
})
export class ReportComponent implements OnInit {

  reportForm!: FormGroup;
  es: any;
  newReport!: Report;
  minDate!: Date;
  maxDate!: Date;

  constructor(private fb: FormBuilder, private reportService: ReportService) { }

  ngOnInit(): void {
    this.initializeForms();
  }

  initializeForms(): void {
    this.reportForm = this.fb.group({
      id: new FormControl(null),
      idTechnician: new FormControl(null, [Validators.required]),
      idService: new FormControl(null, [Validators.required]),
      initialDate: new FormControl(null, [Validators.required]),
      finalDate: new FormControl(null, [Validators.required])
    });
  }

  createReport(): void {
    this.newReport = this.reportForm.value;
    console.log(this.reportForm.value);
    let initialDate = new Date(this.reportForm.value.initialDate).getTime();
    let finalDate = new Date(this.reportForm.value.finalDate).getTime();
    if (initialDate > finalDate) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'La fecha u hora inicial son mayores que la fecha u hora final',
      });
    } else {
      this.reportService.createReport(this.newReport).subscribe((success) => {
        Swal.fire({
          icon: 'success',
          title: 'El reporte ha sido creado',
        });
      }, error => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error,
        });
      });
    }
  }

}
