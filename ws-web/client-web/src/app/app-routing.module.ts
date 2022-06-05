import { LayoutComponent } from './view/layout/layout.component';
import { LoginComponent } from './component/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  // { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "home", component: LayoutComponent },
  { path: "**", redirectTo:"/home",pathMatch:'full' },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
