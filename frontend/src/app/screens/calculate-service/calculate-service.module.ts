import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalculateServiceRoutingModule } from './calculate-service-routing.module';
import { CalculateServiceComponent } from './calculate-service.component';


@NgModule({
  declarations: [
    CalculateServiceComponent
  ],
  imports: [
    CommonModule,
    CalculateServiceRoutingModule
  ]
})
export class CalculateServiceModule { }
