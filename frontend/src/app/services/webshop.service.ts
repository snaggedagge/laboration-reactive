import {Injectable, NgZone} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Product} from "../model/products/product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebshopService {

  constructor(
    private httpClient: HttpClient,
    public zone: NgZone) {
  }

  public getProducts(): Observable<Product[]> {
    // This makes zero difference, seems we must use EventSource to get reactive streams to frontend.
    // With HTTP 1.1, this only allows max 6 connections simultaneously, see https://developer.mozilla.org/en-US/docs/Web/API/EventSource

    //var options = { headers: new HttpHeaders().set( 'Accept', 'application/stream+json' ) };
    return this.httpClient.get<Product[]>(`http://localhost:8070/products`);
  }

  public getProductRx(): Observable<Product> {
    const observable = new Observable<Product>(observer => {
      const eventSource = new EventSource('http://localhost:8060/products');
      eventSource.onmessage = x => this.zone.run(() => observer.next(JSON.parse(x.data)));
      eventSource.onerror = (x) => this.zone.run(() => observer.complete());
      return () => {
        eventSource.close();
      };
    });

    return observable;
  }
}
