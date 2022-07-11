import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Cart } from 'src/app/models/cart';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  public cart!: Cart[];
  //totalPrice!: number;
  totalPrice!: string;

  constructor(
    private spinner: NgxSpinnerService,
    private cartService: CartService,
    private toastr: ToastrService,
  ) {}

  ngOnInit(): void {
    this.getAllCart();
  }

  getAllCart(){
    this.cartService.getListCart().subscribe({
      next: (response:any) => {
        console.log('res :',response);
        this.cart = response.data.carts;
        this.totalPrice = response.data.totalPrice;
        console.log('cart : ',this.cart);

      },error: (err) => {
        console.log('error: ',err);
      }
    });
  }

  increment(cart: Cart) {
    this.cartService.updateCart(cart.productOptionId!, cart.quantity! + 1).subscribe({
      next: (response:any) => {
        this.getAllCart();
        this.toastr.success('Update cart success !!!');
      }
    });
  }

  decrement(cart: Cart): void {
    this.cartService.updateCart(cart.productOptionId!, cart.quantity! - 1).subscribe({
      next: (response:any) => {
        this.getAllCart();
        this.toastr.success('Update cart success !!!');
      }
    });
  }

  deleteItem(id:any){
    this.cartService.deleteCart(id).subscribe({
      next: (response:any) => {
        console.log('res :',response);
        this.getAllCart();
      },error: (err) => {
        console.log('error: ',err);
      }
    });
  }

  //change the number of products in the cart with event
  changeQuantity(e : any,cart: Cart){
    console.log('e : ',e);
    console.log('cart : ',cart);
    this.cartService.updateCart(cart.productOptionId!, e.target.value).subscribe({
      next: (response:any) => {
        this.getAllCart();
        this.toastr.success('Update cart success !!!');
      }
    });

    //if nothing is entered, set quantity equal to 1
    if(e.target.value == ''){
      this.cartService.updateCart(cart.productOptionId!, 1).subscribe({
        next: (response:any) => {
          this.getAllCart();
          this.toastr.success('Update cart success !!!');
        }
      });
    }
  }



}
