import { LabelType, Options } from '@angular-slider/ngx-slider';
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-price',
  templateUrl: './price.component.html',
  styleUrls: ['./price.component.css']
})
export class PriceComponent implements OnInit {

  @Input() fromParent: any;
  minValue: number = 0;
  maxValue: number = 10000000;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.minValue = this.fromParent.min;
    this.maxValue = this.fromParent.max;
  }

  priceRange?: string;

  min?: number;
  max?: number;

  priceOption: any = {
    min: 0,
    max: 0
  }



  optionss: Options = {
    floor: 0,
    ceil: 10000000,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          {
            this.priceOption.min = value;
            return "<b>Giá thấp nhất: </b>" + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
          }
        case LabelType.High:
          this.priceOption.max = value;
          return "<b>Giá cao nhất: </b>" + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
        default:
          return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
      }
    }
  };

  // priceRangeChange(event: any) {
  //   console.log(event.target.value);
  //   this.priceRange = event.target.value;

  //   switch (this.priceRange) {
  //     case '0':
  //       this.fromParent.min = 0;
  //       this.fromParent.max = 100000;
  //       this.optionss.floor = 0;
  //       this.optionss.ceil = 100000;
  //        this.ngOnInit();
  //       break;
  //     case '1':
  //       this.optionss.floor = 100000;
  //       this.optionss.ceil = 1000000;
  //       break;
  //     case '2':
  //       this.optionss.floor = 1000000;
  //       this.optionss.ceil = 5000000;
  //       break;
  //     case '3':
  //       this.optionss.floor = 5000000;
  //       this.optionss.ceil = 10000000;
  //       break;
  //     case '4':
  //       this.optionss.floor = 10000000;
  //       this.optionss.ceil = 20000000;
  //       break;
  //     default:
  //       this.optionss.floor = 0;
  //       this.optionss.ceil = 100000;
  //       break;
  //   }
  // }

  accept() {
    this.activeModal.close(this.priceOption);
  }

  close() {
    this.activeModal.dismiss();
  }

  deleteFilter() {
    console.log('absdfeds');
    this.priceOption.min = '';
    this.priceOption.max = '';
    this.activeModal.close(this.priceOption);
  }

}
