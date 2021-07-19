import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportServiceRoutingModule } from './report-service-routing.module';
import { ReportServiceComponent } from './report-service.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    ReportServiceComponent
  ],
  imports: [
    CommonModule,
    ReportServiceRoutingModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    HttpClientModule,
  ]
})
export class ReportServiceModule { }
