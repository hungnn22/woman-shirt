import { DialogComponent } from './../../../dialog/dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { BaseService } from './../../../../service/base.service';
import { Product } from './../../../../model/product/product.model';
import { Component, OnInit } from '@angular/core';
import { LabelType, Options } from '@angular-slider/ngx-slider';
@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {
  p:number=1;
  url = "PRODUCTS";
  listProduct: Product[] = [];
  public value: [number, number] = [50, 900];
  public min = 50;
  public max = 900;
  public largeStep = 2;
  public smallStep = 100;

  ////////////////

  constructor(private http: HttpClient, private baseService: BaseService, private modalService:NgbModal) {

   }

  ngOnInit(): void {
    this.getAllTutorial();
  }

  ///////////////////////
  getAllTutorial(): void {
    this.baseService.getAll(this.url).subscribe({
      next: (res) => {
        console.log(res);
        this.listProduct = res;
        console.log('Get Thành công');
      },
      error: (e) => console.error(e + "lỗi")
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

  ///////////////////////////


}
