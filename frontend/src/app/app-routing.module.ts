import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'report',
    pathMatch: 'full'
  },
  {
    path: 'calculate',
    loadChildren: () => import('./screens/calculate-service/calculate-service.module').then(m => m.CalculateServiceModule)
  },
  {
    path: 'report',
    loadChildren: () => import('./screens/report-service/report-service.module').then(m => m.ReportServiceModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
