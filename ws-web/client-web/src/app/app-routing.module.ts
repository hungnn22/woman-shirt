import { ContentComponent } from './view/layout/body/content/content.component';
import { SizeComponent } from './component/size/size.component';
import { LayoutComponent } from './view/layout/layout.component';
import { LoginComponent } from './component/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "size", component: SizeComponent },
  // { path: "", redirectTo: "/login", pathMatch: "full" },
  {
    path: "", component: ContentComponent,

  },
  { path: "**", redirectTo: "", pathMatch: 'full' },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
