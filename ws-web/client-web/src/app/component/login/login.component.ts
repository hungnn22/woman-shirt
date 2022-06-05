import { Component, OnInit, TemplateRef } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private modalService:NgbModal) {

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
}

