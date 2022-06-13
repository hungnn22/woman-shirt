import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent implements OnInit {
  height = new FormControl('');
  weight = new FormControl('');

  sizeGroup: FormGroup[] = [];
  message: string = "";
  constructor() {
  }


  ngOnInit(): void {


  }
  findSize() {
    let h = this.height.value;
    let w = this.weight.value;
    console.log("height: " + h + ", weight: " + w);
    if (h == "" && w == "") {
      this.message = "Mời bạn điền thông tin"
    }
    this.findByHeight();
    this.findByWeight();
    this.findByBoth();

  }

  findByWeight() {
    let w = this.weight.value;
    if (42 <= w && w <= 54) {
      this.message = "Size phù hợp với bạn là size S"
    } else if (55 <= w && w <= 60) {
      this.message = "Size phù hợp với bạn là size M"
    } else if (61 <= w && w <= 66) {
      this.message = "Size phù hợp với bạn là size L"
    } else if (67 <= w && w <= 72) {
      this.message = "Size phù hợp với bạn là size XL"
    } else if (73 <= w && w <= 85) {
      this.message = "Size phù hợp với bạn là size XXL"
    } else {
      this.message = ""
    }

  }

  findByHeight() {
    let h = this.height.value;
    if (150 <= h && h <= 170) {
      this.message = "Size phù hợp với bạn là size S"
    } else if (166 <= h && h <= 174) {
      this.message = "Size phù hợp với bạn là size M"
    } else if (170 <= h && h <= 177) {
      this.message = "Size phù hợp với bạn là size L"
    } else if (175 <= h && h <= 180) {
      this.message = "Size phù hợp với bạn là size XL"
    } else if (178 <= h && h <= 190) {
      this.message = "Size phù hợp với bạn là size XXL"
    } else {
      this.message = ""
    }
  }

  findByBoth() {
    let h = this.height.value;
    let w = this.weight.value;
    if (160 <= h && h <= 170 && 49 <= w && w <= 54) {
      this.message = "Size phù hợp với bạn là size S"
    } else if (166 <= h && h <= 174 && 55 <= w && w <= 60) {
      this.message = "Size phù hợp với bạn là size M"
    } else if (170 <= h && h <= 177 && 61 <= w && w <= 66) {
      this.message = "Size phù hợp với bạn là size L"
    } else if (175 <= h && h <= 180 && 67 <= w && w <= 72) {
      this.message = "Size phù hợp với bạn là size XL"
    } else if (178 <= h && h <= 185 && 73 <= w && w <= 78) {
      this.message = "Size phù hợp với bạn là size XXL"
    } else {
      this.message = "";
      this.findByWeight();
    }
  }
}

