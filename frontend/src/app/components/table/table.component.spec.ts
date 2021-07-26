import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { ReportMock } from 'src/app/helpers/mocks/report.mock';
import { ReportService } from 'src/app/services/report.service';

import { TableComponent } from './table.component';

fdescribe('TableComponent', () => {
  let service: ReportService;
  let component: TableComponent;
  let fixture: ComponentFixture<TableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableComponent ],
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ReportService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should do the delete of a report', () => {
    spyOn(service, 'deleteReport').and.returnValue(of(true));
    component.report = ReportMock;
    component.deleteReport(ReportMock);
    fixture.detectChanges();

    service.deleteReport(ReportMock).subscribe((res) => {
      expect(res).toBeTruthy();
    });
  });

  it('Should do edit a report', () => {
    const spy = spyOn(component, 'editReport').and.callThrough();
    component.report = ReportMock;
    component.editReport(ReportMock);
    fixture.detectChanges();

    expect(spy).toHaveBeenCalled();
  });

  it('Should do close a dialog', () => {
    const spy = spyOn(component, 'hideDialog').and.callThrough();
    component.report = ReportMock;
    component.hideDialog();
    fixture.detectChanges();
  });

  it('Should do update a report', () => {
    spyOn(service, 'updateReport').and.returnValue(of(ReportMock));
    component.report = ReportMock;
    component.saveReport();
    fixture.detectChanges();

    service.updateReport(ReportMock).subscribe((res) => {
      expect(res).toEqual(ReportMock);
    });
  });
});
