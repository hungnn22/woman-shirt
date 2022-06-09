import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  listProduct(req: any): Observable<any> {
    return this.http.post<any>(`${environment.product_api}/search`, { ...req });
  }
}
