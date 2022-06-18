import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  showDropDown: boolean = false;
  show: boolean = false;
  showMenu: boolean = false;
  //@ViewChild('headerMenu') headerMenu: ElementRef;
  constructor(
    private router: Router,
  ) {}

  ngOnInit(): void {}

  logout() {
    this.show = false;
    //this.authService.logout();
  }

  goToMyProfile() {
    this.show = false;
    this.router.navigateByUrl('/my-profile');
  }
  goToAdminPage() {
    this.show = false;
    this.router.navigateByUrl('/admin');
  }

  onShowDropDown() {
    this.showDropDown = !this.showDropDown;
  }

  onShowMenu() {
    this.showDropDown = !this.showDropDown;
  }
}