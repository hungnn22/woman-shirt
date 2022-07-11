import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const AUTH_API = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class SizeService {

  constructor(private http: HttpClient) { }


  getListSize(){
    return this.http.get(AUTH_API + 'size');
  }

}
