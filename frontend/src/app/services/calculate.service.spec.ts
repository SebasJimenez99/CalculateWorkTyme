import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CalculateComponent } from '../screens/calculate/calculate.component';
import { CalculateService } from './calculate.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { CalendarModule } from 'primeng/calendar';
import { ReportRoutingModule } from '../screens/report/report-routing.module';

describe('CalculateService', () => {
  let service: CalculateService;
  let component: CalculateComponent;
  let fixture: ComponentFixture<CalculateComponent>;

  beforeEach(() => {
    service = TestBed.inject(CalculateService);
    TestBed.configureTestingModule({
      declarations: [ CalculateComponent ],
      imports: [
        CommonModule,
        ReportRoutingModule,
        MatToolbarModule,
        MatCardModule,
        MatInputModule,
        CalendarModule,
        ReactiveFormsModule,
        MatButtonModule,
        HttpClientModule,
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(CalculateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Should do the calculation of hours', () => {
    const calculateForm = new FormGroup({
      idTechnical: new FormControl('asdf31'),
      weekNumber: new FormControl(28),
    });
    component.calculateForm = calculateForm;
    component.initializeForms();
    expect(component.getReportByTechnicianIdAndWeekNumber()).toBeNull();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
