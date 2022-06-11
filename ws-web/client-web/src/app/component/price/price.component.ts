import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-price',
  templateUrl: './price.component.html',
  styleUrls: ['./price.component.css']
})
export class PriceComponent implements OnInit {
  public value: [number, number] = [50, 900];
  public min = 50;
  public max = 900;
  public largeStep = 2;
  public smallStep = 50;

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
  constructor() { }

  ngOnInit(): void {
  }
  change(){
    this.req.priceMin = this.value[0];
    this.req.priceMax = this.value[1];

    console.log(this.req);
  }

}
