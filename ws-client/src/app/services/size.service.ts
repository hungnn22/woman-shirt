import { Product } from './../models/product';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


const AUTH_API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class SizeService {


  constructor(private http: HttpClient,
  ) {
    const a = this.getCategoryProduct();
  }


  getCategoryProduct(): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8080/api/v1/suggest/category-list");
  }


  getSize(cateId: string, height: string, weight: string):Observable<object> {
    return this.http.get("http://localhost:8080/api/v1/suggest/search?categoryId=" + cateId + "&height=" + height + "&weight=" +weight);
  }

  getListSize(){
    return this.http.get(AUTH_API + 'size');
  }
}
