import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { ContactComponent } from './components/contact/contact.component';
import { ShopComponent } from './components/shop/shop.component';


const routes: Routes = [
  { path: '', redirectTo: '/home',pathMatch: 'full'},
  { path:'home', component: HomeComponent },
  { path:'product-detail/:id', component: ProductDetailComponent},
  { path:'cart', component: CartComponent},
  { path:'contact', component: ContactComponent},
  { path:'shop', component: ShopComponent},
  { path: '',
    loadChildren: () => import('./components/authentication/authentication.module').then(m => m.AuthenticationModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
