import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ProductService } from '../../services/product.service';
import { ProductDetail } from '../../models/product-detail';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from 'src/app/models/review';
import { Productoption } from 'src/app/models/productoption';
import { CartService } from 'src/app/services/cart.service';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  public productDetail !: ProductDetail;
  id !: string;
  public reviews !: Review[];
  public productOptions !: Productoption[];
  sizes:any;
  colors:any;
  quantity: number = 1;
  sizeSelected: string = '';
  colorSelected: string = '';
  productOptionId :string = '';
  images:any;
  selectedIndex = 0;
  @Input() indicators = true;
  @Input() controls = true;
  @Input() autoSlide = false;
  @Input() slideInterval = 3000;//Default to 3 second

  constructor(
    private product: ProductService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private route: ActivatedRoute,
    private cart: CartService,
    private auth: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProductDetail();
  }

  getProductDetail(){
    this.product.getProductDetails(this.id).subscribe(
      (response:any) => {
        console.log('data : ',response.data)
        this.productDetail = response.data;
        this.reviews = response.data.review;
        this.productOptions = response.data.productOptions;
        console.log('product-detail:',this.productDetail);
        console.log('productOptions:',this.productOptions);

        //remove duplicates size in an Array
        const sizeName = this.productOptions.map((item) => {
          return item.sizeName;
        })
        this.sizes = [...new Set(sizeName)];
        console.log('size: ', this.sizes);

        // const colorName = this.productOptions.map((item) => {
        //   return item.colorName;
        // })

        // this.colors = [...new Set(colorName)];

        // console.log('color: ', this.colors);

        const image = this.productOptions.map((item) => {
          return item.image;
        })

        this.images = [...new Set(image)];
        console.log('images: ', this.images);
      },
      err => {
        console.log('err : ',err)
      }
    );
  }

  addToCart(){
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['sign-in']);
      return;
    }

    if(this.sizeSelected == '' || this.colorSelected == ''){
      alert('Please select size and color');
      return;
    }

    this.cart.findProductOptionId(this.colorSelected, this.sizeSelected).subscribe(
      (response:any) => {

        this.productOptionId = response.data;
        console.log('response: ', response.data);
        console.log('productOptionId: ', this.productOptionId);
        this.cart.addToCart(this.productOptionId, this.quantity).subscribe(
          (response:any) => {
            console.log('response: ', response);
            this.toastr.success('Sản phẩm đã được thêm vào giỏ hàng !!');
            this.router.navigate(['/cart']);
          }
        )
      },
      err => {
        console.log('err : ',err)
      })
  }

  increase() {
    this.quantity ++;
  }

  decrease() {
    if(this.quantity <= 1){
      return;
    }

    this.quantity --;
  }

  changeSize(e:any){
    this.sizeSelected = e.target.value;
    this.cart.getListColorBySize(this.sizeSelected).subscribe(
      (response:any) => {
        this.colors = response.data;
      },
      err => {
        console.log('err : ',err)
      }
    )
  }

  changeColor(e: any){
    this.colorSelected = e.target.value;
    console.log('colorSelected: ', this.colorSelected);
  }

  //Change slide every 3 seconds
  autoSlideImages(): void {
    setInterval(() => {
      this.onNextClick();
    }, this.slideInterval);
  }

  //set index of image on dot/indicator click
  selectImage(index: number): void {
    this.selectedIndex = index;
  }

  onPrevClick(): void {
    if (this.selectedIndex === 0) {
      this.selectedIndex = this.images.length - 1;
    }else{
      this.selectedIndex --;
    }
  }

  onNextClick(): void {
    if (this.selectedIndex === this.images.length - 1) {
      this.selectedIndex = 0;
    } else {
      this.selectedIndex ++;
    }
  }

}
