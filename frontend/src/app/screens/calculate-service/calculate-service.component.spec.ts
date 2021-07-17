import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalculateServiceComponent } from './calculate-service.component';

describe('CalculateServiceComponent', () => {
  let component: CalculateServiceComponent;
  let fixture: ComponentFixture<CalculateServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalculateServiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculateServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
