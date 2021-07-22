import { TypeHour } from '../../interfaces/TypeHour.model';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { CalculateService } from '../../services/calculate.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-calculate',
  templateUrl: './calculate.component.html',
  styleUrls: ['./calculate.component.scss'],
  providers: [CalculateService]
})
export class CalculateComponent implements OnInit {

  calculateForm!: FormGroup;

  constructor(private fb: FormBuilder, private calculateService: CalculateService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.initializeForms();
  }

  initializeForms(): void {
    this.calculateForm = this.fb.group({
      idTechnical: new FormControl(null, [Validators.required]),
      weekNumber: new FormControl(null, [Validators.required]),
    });
  }

  openDialog(hour: TypeHour) {
    this.dialog.open(DialogComponent, {
      data: hour
    });
  }

  getReportByTechnicianIdAndWeekNumber(): void {
    this.calculateService.calculateTimeByTechnician(this.calculateForm.value.idTechnical,
      this.calculateForm.value.weekNumber).subscribe(success => {
        this.openDialog(success);
        console.log(success);
      }, error => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error,
        });
      });
  }

}
