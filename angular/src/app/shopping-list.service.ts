import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ShoppingItem } from './shopping-item';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ShoppingListService {
  items: ShoppingItem[] = [];
  constructor(private http: HttpClient) { }

  getItems(): Observable<ShoppingItem[]> {
    return this.http.get<ShoppingItem[]>('http://192.168.1.23:8095/products');
  }

  sendList() {
    return this.http.post<{message:string}>('http://192.168.1.23:8095/sendMail',"");
  }


  clearList() {
    return this.http.post('http://192.168.1.23:8095/clearList',"");
  }

}
