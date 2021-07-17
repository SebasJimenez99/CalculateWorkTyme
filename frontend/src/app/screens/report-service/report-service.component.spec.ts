import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportServiceComponent } from './report-service.component';

describe('ReportServiceComponent', () => {
  let component: ReportServiceComponent;
  let fixture: ComponentFixture<ReportServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportServiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
