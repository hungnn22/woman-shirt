import { Product } from './../models/product';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


const API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class SizeService {


  constructor(private http: HttpClient,
  ) {
    const a = this.getCategoryProduct();
  }


  getCategoryProduct():Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8080/api/v1/suggest/category-list");
  }
}
