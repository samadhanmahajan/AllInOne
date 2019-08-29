import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LabelService {
  baseUrl=environment.baseUrl;
  constructor(private httpclient : HttpClient, private route:Router) { }

  
public postRequest(url:any,data:any)
{
  return this.httpclient.post(this.baseUrl+url,data,{headers: new HttpHeaders().set("emailID",localStorage.getItem("emailID"))})
}
public putRequest(url:any,data:any)
{
  return this.httpclient.put(this.baseUrl+url,data,{headers:new HttpHeaders().set("emailID",localStorage.getItem("emailID"))})
}
public getRequest(url:any)
{
  return this.httpclient.get(this.baseUrl+url,{headers: new HttpHeaders().set("emailID",localStorage.getItem("emailID"))});
}
public deleteRequest(url:any)
{
  return this.httpclient.delete(this.baseUrl+url,{headers:new HttpHeaders().set("emailID",localStorage.getItem("emailID"))}); 
}

}