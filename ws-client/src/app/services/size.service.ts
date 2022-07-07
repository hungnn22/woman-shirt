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


  getCategoryProduct() {
    return this.http.get("http://localhost:8080/api/v1/suggest/category-list");
  }
}
