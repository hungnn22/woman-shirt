import { SizeService } from './../../../services/size.service';
import { map, Observable, startWith } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

interface Product {
  id: string;
  name: string;
  image: string;
}
interface Size {
  cateId: string;
  weight: string;
  height: string;
}
@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent implements OnInit {

  constructor(private size: SizeService,) {

  }

  nameControl = new FormControl('');
  products: Product[] = [];
  image = [];
  filteredOptions: Observable<string[]> | undefined;
  ngOnInit(): void {
    this.getCategoryProduct();
  }


  nameProduct = "Vui lòng chọn loại sản phẩm..."
  check = new FormControl();


  height: number = 0;
  updateHeight($event:any) {
    this.height = $event.value;
    console.log(this.height);
  }

  weight: number = 30;
  updateWeight($event:any) {
    this.weight = $event.value;
    console.log(this.weight);

  }

id="";
  uploadImage(item: any) {
    console.log(item);
    this.image = item.image;
    this.nameProduct = item.name;
    this.id=item.id;
    console.log(this.id);
  }
  getCategoryProduct() {
    this.size.getCategoryProduct().subscribe((data: any) => {
      this.products = data.data;
      console.log(data);

    });
  }

  // getSize() {
  //   this.size.getSize2().subscribe((data: any) => {
  //     this.products = data.data;
  //     console.log(data);

  //   });
  // }

}
