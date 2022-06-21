import { MatIconModule } from '@angular/material/icon';
import { LoginComponent } from './component/login/login.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LayoutComponent } from './view/layout/layout.component';
import { HeaderComponent } from './view/layout/header/header.component';
import { FooterComponent } from './view/layout/footer/footer.component';
import { InputsModule } from '@progress/kendo-angular-inputs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ContentComponent } from './view/layout/body/content/content.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select'
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { DialogComponent } from './view/dialog/dialog.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { PriceComponent } from './component/price/price.component';
import { SizeComponent } from './component/size/size.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { CustomMaterialPaginatorService } from './service/custom-material-paginator.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent,
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    DialogComponent,
    PriceComponent,
    SizeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    InputsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    NgxPaginationModule,
    MatGridListModule,
    MatCardModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatFormFieldModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatInputModule,
    NgxSliderModule,
    MatProgressSpinnerModule,
    MatPaginatorModule
  ],
  providers: [{ provide: MatPaginatorIntl, useClass: CustomMaterialPaginatorService }],
  bootstrap: [AppComponent],
})

export class AppModule { }


