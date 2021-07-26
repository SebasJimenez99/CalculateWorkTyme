import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogComponent } from './components/dialog/dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { TableComponent } from './components/table/table.component';
import { MatCardModule } from '@angular/material/card';
import { TableModule } from 'primeng/table';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ButtonModule } from 'primeng/button';
import { DialogModule }  from 'primeng/dialog';
import { ConfirmDialogModule }   from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    DialogComponent,
    TableComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatButtonModule,
    ButtonModule,
    MatCardModule,
    TableModule,
    MatInputModule,
    MatToolbarModule,
    DialogModule,
    ConfirmDialogModule,
    InputTextModule,
    CalendarModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
