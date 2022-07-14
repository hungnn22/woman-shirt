import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './layouts/header/header.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { RelatedProductsComponent } from './components/product-detail/related-products/related-products.component';
import { CartComponent } from './components/cart/cart.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { HomeComponent } from './components/home/home.component';
import { ContactComponent } from './components/contact/contact.component';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ShopComponent } from './components/shop/shop.component';
import { SizeComponent } from './components/shop/size/size.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { PriceComponent } from './components/shop/price/price.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { WishListComponent } from './components/wish-list/wish-list.component';
import { LocationComponent } from './components/shop/location/location.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ProductDetailComponent,
    RelatedProductsComponent,
    CartComponent,
    ContactComponent,
    ShopComponent,
    SizeComponent,
    PriceComponent,
    WishListComponent,
    LocationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NgxSpinnerModule,
    HttpClientModule,
    FormsModule,
    //NgxSliderModule,
    MaterialModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    NgbModule,
    CarouselModule,
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
