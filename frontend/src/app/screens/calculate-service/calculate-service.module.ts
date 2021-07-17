import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalculateServiceRoutingModule } from './calculate-service-routing.module';
import { CalculateComponent } from './calculate/calculate.component';
import { CalculateServiceComponent } from './calculate-service.component';


@NgModule({
  declarations: [
    CalculateComponent,
    CalculateServiceComponent
  ],
  imports: [
    CommonModule,
    CalculateServiceRoutingModule
  ]
})
export class CalculateServiceModule { }
