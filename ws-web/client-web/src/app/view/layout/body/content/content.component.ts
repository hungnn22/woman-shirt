import { DialogComponent } from './../../../dialog/dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { BaseService } from './../../../../service/base.service';
import { Product } from './../../../../model/product/product.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {
  url = "PRODUCTS";
  listProduct: Product[] = [];
  constructor(private http: HttpClient, private baseService: BaseService, private modalService:NgbModal) { }

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
