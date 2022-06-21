import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css']
})
export class SizeComponent implements OnInit {
  height = new FormControl();
  weight = new FormControl();
  message = "";
  sizeH = 0;
  sizeW = 0;
  constructor(private modalService: NgbModal) {

  }

  ngOnInit(): void {

  }



  findSize() {
    const h = this.height.value;
    const w = this.weight.value;
    if (h == "" || w == ""||h==null||w==null) {
      this.message = "Vui lòng điền đầy đủ thông tin";
      return;
    }
    this.findByHeight();
    this.findByWeight();
    console.log("Ban đầu: H:" + this.sizeH + ",W:" + this.sizeW);

    if (this.sizeH === this.sizeW) {
      this.findMatch()
      console.log("H:" + this.sizeH + ",W:" + this.sizeW);
      console.log("Hợp");
    } else if (this.sizeH > this.sizeW) {
      this.findByHeight()
      console.log("H:" + this.sizeH + ",W:" + this.sizeW);
      console.log("Chiều cao");
    } else if (this.sizeH < this.sizeW) {
      this.findByWeight()
      console.log("H:" + this.sizeH + ",W:" + this.sizeW);
      console.log("Cân nặng");
    }
  }

  findMatch() {
    const h = this.height.value;
    const w = this.weight.value;
    if (h == "" || w == "") {
      this.message = "(*)Vui lòng điền đầy đủ thông tin";
      return;
    }
    if (h >= 140 && h <= 160 && w >= 40 && w <= 50) {
      this.message = "(*)Size phù hợp với bạn là Size S"
    } else if (h > 160 && h <= 165 && w > 50 && w <= 57) {
      this.message = "(*)Size phù hợp với bạn là Size M"
    } else if (h > 165 && h <= 170 && w > 57 && w <= 65) {
      this.message = "(*)Size phù hợp với bạn là Size L"
    } else if (h > 170 && h <= 175 && w > 65 && w <= 72) {
      this.message = "(*)Size phù hợp với bạn là Size XL"
    } else if (h < 190 && h > 176 && w <= 190 && w > 73) {
      this.message = "(*)Size phù hợp với bạn là Size XXL"
    } else {
      this.message = ""
    }

  }


  findByHeight() {
    const h = this.height.value;
    const w = this.weight.value;
    if (h == "" || w == "") {
      this.message = "(*)Vui lòng điền đầy đủ thông tin";
      return;
    }
    if (h >= 140 && h <= 160) {
      this.sizeH = 1;
      this.message = "(*)Size phù hợp với bạn là Size S"
    } else if (h > 160 && h <= 165) {
      this.sizeH = 2;
      this.message = "(*)Size phù hợp với bạn là Size M"
    } else if (h > 165 && h <= 170) {
      this.sizeH = 3;
      this.message = "(*)Size phù hợp với bạn là Size L"
    } else if (h > 170 && h <= 175) {
      this.sizeH = 4;
      this.message = "(*)Size phù hợp với bạn là Size XL"
    } else if (h > 176 && h < 190) {
      this.sizeH = 5;
      this.message = "(*)Size phù hợp với bạn là Size XXL"
    } else {
      this.message = ""
    }
    console.log(this.message + " " + this.sizeH);
    console.log("Chiều cao: " + h);
  }

  findByWeight() {
    const h = this.height.value;
    const w = this.weight.value;
    if (h == "" || w == "") {
      this.message = "(*)Vui lòng điền đầy đủ thông tin";
      return;
    }
    if (w >= 40 && w <= 50) {
      this.sizeW = 1;

      this.message = "(*)Size phù hợp với bạn là Size S"
    } else if (w > 50 && w <= 57) {
      this.sizeW = 2;
      this.message = "(*)Size phù hợp với bạn là Size M"
    } else if (w > 57 && w <= 65) {
      this.sizeW = 3;
      this.message = "(*)Size phù hợp với bạn là Size L"
    } else if (w > 65 && w <= 72) {
      this.sizeW = 4;
      this.message = "(*)Size phù hợp với bạn là Size XL"
    } else if (w <= 90 && w > 73) {
      this.sizeW = 5;
      this.message = "(*)Size phù hợp với bạn là Size XXL"
    } else {
      this.message = ""
    }
    console.log(this.message + " " + this.sizeW);
    console.log("cân nặng: " + w);
  }
}
