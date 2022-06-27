import { SizeComponent } from './../../../component/size/size.component';
import { LoginComponent } from '../../../component/login/login.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {

  }
  open() {
    this.modalService.open(
      LoginComponent,
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

  ///////////////////////
  //
}
