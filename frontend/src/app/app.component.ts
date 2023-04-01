import {Component, OnInit } from '@angular/core';
import { Product } from './model/products/product';
import { WebshopService } from './services/webshop.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  products: Product[] = [];
  firstElement = true;

  useReactiveStream = false;

  constructor(private webshopService: WebshopService) {
  }

  ngOnInit() {
    this.products = [];
    this.firstElement = true;
    const start = Date.now();
    if (this.useReactiveStream) {
      this.webshopService.getProductRx().subscribe({
        next: product => {
          if (this.firstElement) {
            const end = Date.now();
            console.log(`With Reactive: First element retrieved in ${end - start} ms`);
            this.firstElement = false;
          }
          this.products.push(product);
        },
        complete: () => {
          const end = Date.now();
          console.log(`With Reactive: Total execution time: ${end - start} ms`);
        }
      });
    }
    else {
      this.webshopService.getProducts().subscribe({
        next: products => {
          const end = Date.now();
          console.log(`With Blocking: First element retrieved in ${end - start} ms`);
          this.products = products;
        },
        complete: () => {
          const end = Date.now();
          console.log(`With Blocking: Total execution time: ${end - start} ms`);
        }
      });
    }
  }
}
