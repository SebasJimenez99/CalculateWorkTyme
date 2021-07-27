import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { CalculateEmptyMock, CalculateMock, TypeHourMock } from './../../helpers/mocks/calculate.mock';
import { CalculateComponent } from './calculate.component';
import { CalculateService } from './../../services/calculate.service';

describe('CalculateComponent', () => {
  let service: CalculateService;
  let component: CalculateComponent;
  let fixture: ComponentFixture<CalculateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalculateComponent ],
      imports: [ReactiveFormsModule, HttpClientTestingModule, MatDialogModule],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculateComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CalculateService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should not calculate, form empty', () => {
    const spy = spyOn(component, 'getReportByTechnicianIdAndWeekNumber').and.callThrough();
    component.calculateForm.setValue(CalculateEmptyMock);
    fixture.detectChanges();
    const button: HTMLElement = fixture.debugElement.nativeElement.querySelector('#btn-submit');
    button.click();
    expect(spy).not.toHaveBeenCalled();
  });

  it('Should do the calculation of hours', () => {
    spyOn(service, 'calculateTimeByTechnician').and.returnValue(of(TypeHourMock));
    const form = component.calculateForm;
    form.setValue(CalculateMock);
    fixture.detectChanges();

    const button: HTMLElement = fixture.debugElement.nativeElement.querySelector('#btn-submit');
    button.click();

    service.calculateTimeByTechnician(CalculateMock).subscribe((res) => {
      expect(res).toEqual(TypeHourMock);
    });
  });
});
