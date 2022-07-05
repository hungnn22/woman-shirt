import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Product } from 'src/app/models/product';
import { AuthService } from 'src/app/services/auth.service';
import { CustomMaterialPaginatorService } from 'src/app/services/custom-material-paginator.service';
import { ProductService } from 'src/app/services/product.service';
import { DialogComponent } from './dialog/dialog.component';
import { PriceComponent } from './price/price.component';
import { SizeComponent } from './size/size.component';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

  isFilter : boolean = false;
  typeOption?: String;
  sortOption: number = 0;
  loading = true;
  resultSearch: string = '';
  listProduct: Product[] = [];

  //page
  length = 0;
  pageSize = 8;
  pageIndex = 0;
  pageSizeOptions = [4, 8, 16, 32, 64];
  showFirstLastButtons = true;


  //search
  priceMin: number = 0;
  priceMax: number = 10000000;
  textSearch?: String;

  //request
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

  constructor(
    private http: HttpClient,
    private modalService: NgbModal,
    private productService: ProductService,
    private customMaterialPaginator: CustomMaterialPaginatorService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getListProduct(this.req);
  }

  getListProduct(req: any) {
    this.productService.listProduct(req).subscribe(datas => {
      this.loading = true;
      this.listProduct = datas.data;
      this.length = datas.totalElements;
      this.loading = false;
    })
  }

  // options: string[] = ['One', 'Two', 'Three'];
  // options1: string[] = ["S", "M", "L", "XL"];


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
    this.getListProduct(this.req);
    this.isFilter = true;
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
      this.priceMin = result.min;
      this.priceMax = result.max;
      this.req.priceMin = result.min;
      this.req.priceMax = result.max;
      this.getListProduct(this.req);
      this.isFilter = true;
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

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.req.pageReq.page = this.pageIndex;
    this.req.pageReq.pageSize = this.pageSize;
    this.pageProduct(this.req);
    this.isFilter = true;
  }

  pageProduct(req: any) {
    this.productService.listProduct(req).subscribe(datas => {
      this.listProduct = datas.data;
    })
  }

  openSize() {
    this.modalService.open(
      SizeComponent,
      {
        backdrop: true,
        centered: true,
        size:'xl'
      }
    )
      .result
      .then((result:any) => {
        // write your code here
      });
  }

}
