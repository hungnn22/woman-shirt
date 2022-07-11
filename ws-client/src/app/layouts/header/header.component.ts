import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { CartItem } from 'src/app/models/CartItem';
import { CartService } from 'src/app/services/cart.service';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn!: boolean;
  public cartItem !: CartItem;


  constructor(
    private router: Router,
    private tokenService: TokenStorageService,
    private cartService: CartService
  ) {
    this.router.events.pipe(filter(e => e instanceof NavigationEnd)).subscribe(e => {
      this.init();
    });
  }

  ngOnInit(): void {

  }

  init(){
    this.isLoggedIn = this.tokenService.loggedIn();
    this.getCartItemCount();

  }

  getCartItemCount(){
     if(this.isLoggedIn){
      this.cartService.countItemInCart().subscribe({
        next: (res: any) => {
          console.log('data : ',res);
          this.cartItem = res.data;
          console.log('cartItem:',this.cartItem);
        },error: (err) => {
          console.log('error: ',err);
        }
       })
     }
  }

  logout(){
    this.tokenService.signOut();
    window.location.reload();
    this.router.navigate(['/home']);
  }


}
