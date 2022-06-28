import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

let auth_token = window.localStorage.getItem('auth-token');

const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${auth_token}`
});

const requestOptions = { headers: headers };

const AUTH_API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  addToCart(productOptionId:string,quantity:number) {
    return this.http.post(AUTH_API + 'cart/add-to-cart', {productOptionId,quantity}, requestOptions);
  }

  getListColorBySize(size:string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("size",size);

    return this.http.get(AUTH_API + 'product-option/findColor', {params: queryParams});
  }

  findProductOptionId(colorId: string,size:string){
    return this.http.post(AUTH_API + 'product-option/findId', {colorId,size}, httpOptions);
  }

}
