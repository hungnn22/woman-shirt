import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductService } from '../../services/product.service';
import { ProductDetail } from '../../models/product-detail';
import { Router } from '@angular/router';
import { Review } from 'src/app/models/review';
import { Productoption } from 'src/app/models/productoption';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  public productDetail !: ProductDetail;
  productId !: string;
  public reviews !: Review[];
  public productOptions !: Productoption[];
  sizes:any;
  colors:any;
  color: string = '';

  constructor(
    private product: ProductService,
    private spinner: NgxSpinnerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getProductDetail();
  }

  getProductDetail(){
    this.product.getProductDetails('3166675c-8def-42a0-b9f6-975a25738ce8').subscribe(
      (response:any) => {
        console.log('data : ',response.data)
        //debugger
        this.productDetail = response.data;
        this.reviews = response.data.review;
        this.productOptions = response.data.productOptions;
        console.log('product-detail:',this.productDetail);
        console.log('productOptions:',this.productOptions);

        //remove duplicates size in an Array
        const sizeName = this.productOptions.map((item) => {
          return item.sizeName;
        })

        this.sizes = [...new Set(sizeName)];

        console.log('size: ', this.sizes);

        const colorName = this.productOptions.map((item) => {
          return item.colorName;
        })

        this.colors = [...new Set(colorName)];

        console.log('color: ', this.colors);
      },
      err => {
        console.log('err : ',err)
      }
    );
  }

  changeProductOption(event:any){
    console.log('event : ',event)
  }

  changeColor(color: string) {
    this.color = color;
  }


}
