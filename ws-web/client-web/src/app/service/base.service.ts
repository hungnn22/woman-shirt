import { Product } from './../model/product/product.model';
import { Observable } from 'rxjs';
import { IBaseService } from './iBase.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const baseUrl = "http://localhost:3000/";
@Injectable({
  providedIn: 'root'
})
export class BaseService implements IBaseService {
  listProduct: Product[] = [];
  constructor(private http: HttpClient) { }

  getAll(url: string): Observable<Product[]> {
    return this.http.get<Product[]>(baseUrl + url);
  }
}
