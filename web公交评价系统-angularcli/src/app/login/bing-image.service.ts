import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Image } from '../domain/entities';

@Injectable()
export class BingImageService {

  imageUrl: string;
  headers = new Headers({
    'Content-Type': 'application/json',
    'Ocp-Apim-Subscription-Key': '1c408bbe74f34401beac4747a0987c9d'
  });

  constructor(private http: Http) {
    const q = '雪+壁纸'
    const baseUrl: string = `https://api.cognitive.microsoft.com/bing/v7.0/images/search`;
    this.imageUrl = baseUrl + `?q=${q}&count=5&mkt=zh-CN&imageType=Photo&size=Large`;
  }

  getImageUrl(): Observable<Image[]>{
    return this.http.get(this.imageUrl, { headers: this.headers })
            .map(res => res.json().value as Image[])
            .catch(this.handleError);
  }
  private handleError(error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
  // yieldByInterval(items, time) {
  //   return Observable.from(items).zip(
  //     Observable.interval(time),
  //     (item, index) => item
  //   );
  // }
}
