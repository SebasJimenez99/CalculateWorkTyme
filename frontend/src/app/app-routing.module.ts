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
    loadChildren: () => import('./screens/calculate/calculate.module').then(m => m.CalculateModule)
  },
  {
    path: 'report',
    loadChildren: () => import('./screens/report/report.module').then(m => m.ReportModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
