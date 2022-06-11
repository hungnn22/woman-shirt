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
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {
  typeOption?: String;
  sortOption?: String;
  p: number = 1;
  url = "PRODUCTS";
  listProduct: Product[] = [];

  textSearch?: String;

  req: any = {
    "id": "",
    "active": null,
    "textSearch": "",
    "priceMin": "",
    "priceMax": "",
    "pageReq": {
      "page": 0,
      "pageSize": 10,
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
    })
  }
  ///////////////////////
  // getAllTutorial(): void {
  //   this.baseService.getAll(this.url).subscribe({
  //     next: (res) => {
  //       console.log(res);
  //       this.listProduct = res;
  //       console.log('Get Thành công');
  //     },
  //     error: (e) => console.error(e + "lỗi")
  //   })
  // }
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


  ///////////////////////////
  openPrice() {
    this.modalService.open(
      PriceComponent,
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
    this.req.pageReq.sortField = this.sortOption;
    switch (this.sortOption) {
      case 'Tên':
        this.req.pageReq.sortField = 'name';
        break;
      case 'Mới nhất':
        this.req.pageReq.sortField = 'latest';
        break;
      case 'Bán chạy':
        this.req.pageReq.sortField = 'bestseller';
        break;
      case 'Giá':
        this.req.pageReq.sortField = 'price';
        break;
      default:
        this.req.pageReq.sortField = 'name';
        break;
    }

    if (this.req.pageReq.sortDirection == '') {
      this.req.pageReq.sortDirection = 'ASC';
    } else if (this.req.sortDirection == 'ASC') {
      this.req.pageReq.sortDirection = 'DESC';
    } else {
      this.req.pageReq.sortDirection = 'ASC';
    }

    console.log(this.req);

    this.getListProduct(this.req);
  }


}
