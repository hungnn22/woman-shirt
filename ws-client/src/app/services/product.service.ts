import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

const API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  getProductDetails(id:string){
    return this.http.get(API + 'product/' + id);
  }

  listProduct(req: any): Observable<any> {
    return this.http.post<any>(API + 'product/search', { ...req });
  }

  getListProduct(req: any): Observable<any> {
    return this.http.post<any>(API + 'product/search/v2', { ...req });
  }

}
