import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportServiceComponent } from './report-service.component';

const routes: Routes = [
  {
    path: '',
    component: ReportServiceComponent,
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportServiceRoutingModule { }
