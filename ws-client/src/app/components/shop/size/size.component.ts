import { SizeService } from './../../../services/size.service';
import { map, Observable, startWith } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

interface Product {
  id: string;
  name: string;
  image: string;
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

  formatLabel(value: number) {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }
    console.log(value);
    return value;
  }

  nameProduct = "Vui lòng chọn loại sản phẩm..."
  check = new FormControl();


  uploadImage(item: any) {
    console.log(item);
    this.image = item.image;
    this.nameProduct = item.name;
  }
  getCategoryProduct() {
    this.size.getCategoryProduct().subscribe((data: any) => {
      this.products = data.data;
      console.log(data);

    });
  }
}
