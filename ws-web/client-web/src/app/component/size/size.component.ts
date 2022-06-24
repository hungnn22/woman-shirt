import { Options } from '@angular-slider/ngx-slider';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent {
  public source: Array<{ text: string; value: number; img: string }> = [
    { text: "Áo thun", value: 1, img: "https://product.hstatic.net/1000026602/product/img_6743_4bc7b609abe64eaf9255b8d24e854fab.jpg" },
    { text: "Áo freesize", value: 2, img: "https://product.hstatic.net/1000026602/product/img_6743_4bc7b609abe64eaf9255b8d24e854fab.jpg" },
    { text: "Áo gió", value: 3, img: "" },
    { text: "Quần Jeans", value: 4, img: "" },
    { text: "Quần kaki", value: 5, img: "" },
    { text: "Quần short", value: 6, img: "" },
    { text: "Quần âu", value: 7, img: "" },
    { text: "Giày Sneaker", value: 8, img: "" },
    { text: "SlipOn", value: 9, img: "" },
  ];

  public data: Array<{ text: string; value: number }>;
  value: number = 150;
  options: Options = {
    floor: 155,
    ceil: 195
  };
  constructor() {
    this.data = this.source.slice();
  }

  handleFilter(value: any) {
    this.data = this.source.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  formatLabel(value: number) {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }

    return value;
  }
}
