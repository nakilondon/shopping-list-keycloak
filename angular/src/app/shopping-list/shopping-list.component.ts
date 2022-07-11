import { Component, OnDestroy, OnInit } from '@angular/core';
import { ShoppingListService } from '../shopping-list.service';
import { ShoppingItem } from '../shopping-item';

import { BehaviorSubject, Observable, Subscription, switchMap, throwError, timer } from 'rxjs';

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html'
})

export class ShoppingListComponent implements OnInit {
  shoppingItems$: Observable<ShoppingItem[]> | undefined;
  refreshShoppingItems$ = new BehaviorSubject<boolean>(true);
  subscription: Subscription | undefined;
  everyFiveSeconds: Observable<number> = timer(0, 5000);
  
  constructor( private shoppingService: ShoppingListService ) { }

  //ngOnDestroy(): void {
  //  if (this.subscription !== undefined){
  //    this.subscription.unsubscribe();
  //  }
  //}

  ngOnInit(): void {
    this.shoppingItems$ = this.refreshShoppingItems$.pipe(switchMap(_ => this.shoppingService.getItems()));
    this.subscription = this.everyFiveSeconds.subscribe(() => this.refreshShoppingItems$.next(true));
  }

  clearList() {
    this.shoppingService.clearList().subscribe(
      response => {
        console.log(response)
        this.refreshShoppingItems$.next(true);
      },
      err => {
        console.error(err)
        this.refreshShoppingItems$.next(true);
      },
    );
    
  }
}