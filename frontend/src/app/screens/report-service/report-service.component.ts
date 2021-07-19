import { ReportService } from './../../services/report.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Report } from 'src/app/interfaces/Report.model';
import * as moment from 'moment/moment';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-report-service',
  templateUrl: './report-service.component.html',
  styleUrls: ['./report-service.component.scss'],
  providers: [ReportService]
})
export class ReportServiceComponent implements OnInit {

  reportForm!: FormGroup;
  es: any;
  newReport!: Report;
  minDate!: Date;
  maxDate!: Date;

  constructor(private fb: FormBuilder, private reportService: ReportService) {}

  ngOnInit(): void {
    this.initializeForms();
  }

  initializeForms(): void {
    this.reportForm = this.fb.group({
      id: new FormControl(null),
      idTechnician: new FormControl(null, [Validators.required]),
      idService: new FormControl(null, [Validators.required]),
      initialDate: new FormControl(null, [Validators.required]),
      finalDate: new FormControl(null, [Validators.required]),
    });
  }

  datesSpanish(): void {
    this.es = {
      firstDayOfWeek: 1,
      dayNames: [ "domingo","lunes","martes","miércoles","jueves","viernes","sábado" ],
      dayNamesShort: [ "dom","lun","mar","mié","jue","vie","sáb" ],
      dayNamesMin: [ "D","L","M","X","J","V","S" ],
      monthNames: [ "enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre" ],
      monthNamesShort: [ "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic" ],
      today: 'Hoy',
      clear: 'Borrar'
    }
  }

  crearReport(): void {
    this.newReport = this.reportForm.value;
    let initialDate = new Date(this.reportForm.value.initialDate).getTime();
    let finalDate = new Date(this.reportForm.value.finalDate).getTime();
    console.log(initialDate + " " + finalDate);
    if(initialDate > finalDate){
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'La fecha inicial es mayor que la fecha final',
      })
    } else {
      this.reportService.createReport(this.newReport).subscribe((success) => {
        Swal.fire({
          icon: 'success',
          title: 'El reporte ha sido creado',
        })
      }, error => {
        console.log(error);
      });
    }
  }

}
