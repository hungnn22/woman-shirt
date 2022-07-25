import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  isScreenChangePass = false;
  isScreenInfo=true;
  constructor() {
  }

  ngOnInit(): void {

  }
  changePass() {
    this.isScreenChangePass = true;
    this.isScreenInfo=false

  }
  info(){
    this.isScreenInfo=true
    this.isScreenChangePass = false;


  }
}
