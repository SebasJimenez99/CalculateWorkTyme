import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ReportEmptyMock, ReportFormMock, ReportMock } from './../../helpers/mocks/report.mock';
import { CalendarModule } from 'primeng/calendar';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { ReportComponent } from './report.component';
import { ReportService } from './../../services/report.service';

describe('ReportServiceComponent', () => {
  let service: ReportService;
  let component: ReportComponent;
  let fixture: ComponentFixture<ReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportComponent ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        CalendarModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ReportService);
    fixture.detectChanges();
  });

  it('Should not calculate, form empty', () => {
    const spy = spyOn(component, 'createReport').and.callThrough();
    component.reportForm.setValue(ReportEmptyMock);
    fixture.detectChanges();
    const button: HTMLElement = fixture.debugElement.nativeElement.querySelector('#btn-submit');
    button.click();
    expect(spy).not.toHaveBeenCalled();
  });

  it('Should do the calculation of hours', () => {
    spyOn(service, 'createReport').and.returnValue(of(ReportMock));
    const form = component.reportForm;
    form.setValue(ReportFormMock);
    fixture.detectChanges();

    const button: HTMLElement = fixture.debugElement.nativeElement.querySelector('#btn-submit');
    button.click();

    service.createReport(ReportMock).subscribe((res) => {
      expect(res).toEqual(ReportMock);
    });
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
