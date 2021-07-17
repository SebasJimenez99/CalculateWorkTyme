import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportServiceRoutingModule } from './report-service-routing.module';
import { ReportServiceComponent } from './report-service.component';


@NgModule({
  declarations: [
    ReportServiceComponent
  ],
  imports: [
    CommonModule,
    ReportServiceRoutingModule
  ]
})
export class ReportServiceModule { }
