import { DialogComponent } from './../../../dialog/dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
// import { Product } from './../../../../model/product/product.model';
import { Product } from 'src/app/entity/product/product';
import { Component, OnInit } from '@angular/core';
import { LabelType, Options } from '@angular-slider/ngx-slider';
import { PriceComponent } from 'src/app/component/price/price.component';
import { ProductService } from 'src/app/service/product.service';
@Component({
  selector: 'app-content',

  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css'],
})
export class ContentComponent implements OnInit {
  typeOption?: String;
  sortOption: number = 0;
  p: number = 1;
  url = "PRODUCTS";
  listProduct: Product[] = [];

  priceMin: number = 0;
  priceMax: number = 10000000;
  textSearch?: String;

  req: any = {
    "id": "",
    "active": null,
    "textSearch": "",
    "priceMin": "",
    "priceMax": "",
    "pageReq": {
      "page": 0,
      "pageSize": 8,
      "sortField": "",
      "sortDirection": ""
    }
  }

  constructor(private http: HttpClient,
    private modalService: NgbModal,
    private productService: ProductService
  ) {

  }

  ngOnInit(): void {
    this.getListProduct(this.req);
  }

  getListProduct(req: any) {
    this.productService.listProduct(req).subscribe(products => {
      this.listProduct = products.data;
      console.log(this.listProduct);
    })
  }

  options: string[] = ['One', 'Two', 'Three'];
  options1: string[] = ["S", "M", "L", "XL"];


  /////////////////////////////////
  openDialog() {
    this.modalService.open(
      DialogComponent,
      {
        backdrop: true,
        centered: true,
      }
    )
      .result
      .then((result) => {
        // write your code here

      });
  }

  sortOptionChange() {
    console.log(this.sortOption);
    this.req.pageReq.sortField = this.sortOption;
    switch (this.sortOption) {
      case 1:
        this.req.pageReq.sortField = 'createdDate';
        this.req.pageReq.sortDirection = 'DESC';
        break;
      case 2:
        this.req.pageReq.sortField = 'latest';
        break;
      case 3:
        this.req.pageReq.sortField = 'price';
        this.req.pageReq.sortDirection = 'DESC';
        break;
      case 4:
        this.req.pageReq.sortField = 'price';
        this.req.pageReq.sortDirection = 'ASC';
        break;
      default:
        this.req.pageReq.sortField = 'createdDate';
        this.req.pageReq.sortDirection = 'DESC';
        break;
    }

    // if (this.req.pageReq.sortDirection == '') {
    //   this.req.pageReq.sortDirection = 'ASC';
    // } else if (this.req.sortDirection == 'ASC') {
    //   this.req.pageReq.sortDirection = 'DESC';
    // } else {
    //   this.req.pageReq.sortDirection = 'ASC';
    // }

    console.log(this.req);

    this.getListProduct(this.req);
  }

  openPrice() {
    const modalRef = this.modalService.open(PriceComponent,
      {
        scrollable: true,
        backdrop: true,
        centered: true,
        size: 'lg'
      });

    let data = {
      min: this.priceMin,
      max: this.priceMax
    }

    modalRef.componentInstance.fromParent = data;
    modalRef.result.then((result) => {
      console.log(result);
      this.priceMin = result.min;
      this.priceMax = result.max;
      this.req.priceMin = result.min;
      this.req.priceMax = result.max;
      this.getListProduct(this.req);

    }, (reason) => {
    });
  }

  onKeyUp(event: any) {
    // setTimeout(() => {

    // }, 3000);

    console.log(event.target.value);
    this.req.textSearch = event.target.value;
    console.log(this.req);
    this.getListProduct(this.req);
  }

  keyUp(event: any) {
    this.req.textSearch = event.target.value;
    if (this.req.textSearch.trim().length == 0) {
      this.getListProduct(this.req);
    }
  }

}
