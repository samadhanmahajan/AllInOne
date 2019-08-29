import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from 'src/environments/environment'
import { Router } from '@angular/router';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  

@Injectable({
providedIn: 'root'
})

export class HttpService {

constructor(private httpclient: HttpClient,private router: Router) { }

baseUrl = environment.baseUrl;

//token = this.route.snapshot.paramMap.get('token');

public postRequest(url: any, data: any): any {
return this.httpclient.post(this.baseUrl + url, data,httpOptions);
}
public getRequest(url: any): any {
return this.httpclient.get(this.baseUrl + url,httpOptions);
}

public putRequest(url: any,data:any): any {
return this.httpclient.put(this.baseUrl + url,data,httpOptions);
}
public deleteRequest(url:any):any{
return this.httpclient.delete(this.baseUrl+url,httpOptions);
}
}