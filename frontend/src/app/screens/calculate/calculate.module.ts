import { DialogComponent } from '../../components/dialog/dialog.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalculateRoutingModule } from './calculate-routing.module';
import { CalculateComponent } from './calculate.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { CalendarModule } from 'primeng/calendar';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    CalculateComponent
  ],
  imports: [
    CommonModule,
    CalculateRoutingModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    CalendarModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    HttpClientModule,
    MatDialogModule,
  ],
  providers: [
    DialogComponent
  ]
})
export class CalculateModule { }
