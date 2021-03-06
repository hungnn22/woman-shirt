import { LocationComponent } from './components/shop/location/location.component';
import { SizeComponent } from './components/shop/size/size.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { ContactComponent } from './components/contact/contact.component';
import { ShopComponent } from './components/shop/shop.component';
import { WishListComponent } from './components/wish-list/wish-list.component';
import { AuthGuard } from './guards/auth.guard';
import { CheckoutComponent } from './components/checkout/checkout.component';


const routes: Routes = [
  { path: '', redirectTo: '/home',pathMatch: 'full'},
  { path:'home', component: HomeComponent },
  { path:'product-detail/:id', component: ProductDetailComponent},
  { path:'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path:'contact', component: ContactComponent},
  { path:'checkout', component: CheckoutComponent },
  { path:'wishlist', component: WishListComponent,canActivate: [AuthGuard] },
  { path:'shop', component: ShopComponent},
  { path:'location', component: LocationComponent},

  { path:'size', component: SizeComponent},

  { path: '',
    loadChildren: () => import('./components/authentication/authentication.module').then(m => m.AuthenticationModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
