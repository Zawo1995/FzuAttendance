import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {isObject} from "util";
/**
 * Created by Administrator on 2019-03-06.
 */
@Injectable(
  {providedIn: 'root'}
)
export class HttpServiceProvider{

  constructor(public http: HttpClient) {  }

  /***
   * 将参数组装成字符串
   * @param params
   * @returns {any}
   */
  private paramsString(params: any): string {

    if (!params) return null;
    let str = "";
    for (let key in params) {
      if (params.hasOwnProperty(key)) {
        let value = params[key];
        if (value === null) continue;

        if (Array.isArray(value)) {
          if (value.length === 0) continue;

          for (let index = 0; index < value.length; index++) {
            let k = key + "[" + index + "]";
            let v = value[index];
            if (str.length > 1) str += "&";
            str += k + "=" + v;
          }
        } else if (isObject(value)) {

          for (let subKey in value) {
            if (value.hasOwnProperty(subKey)) {
              let v = value[subKey];
              if (v === null) continue;

              let k = key + "[" + subKey + "]";
              if (str.length > 1) str += "&";
              str += k + "=" + v;
            }
          }

        } else {
          if (str.length > 1) str += "&";
          str += key + "=" + value;
        }
      }
    }
    return str;
  }
  /**
   * 参数转换成HttpParams
   * @param params
   * @returns {any}
   */
  private encodeComplexHttpParams(params: any): any {
    if (!params) return null;
    return new HttpParams({fromString: this.paramsString(params)});
  }
  get($url: string, $params?: any): Promise<any> {
    let $this = this;
    return new Promise((resolve, reject) => {
      let url = "/FzuAttendance/" + $url;
      this.http.get(url, {
        params: this.encodeComplexHttpParams($params)
      }).subscribe(res => {
        resolve(res);
      }, error => {
        if (error.status == 200){
        } else {
          console.error(error);
        }
      });
    });
  }

  post($url: string, $params?: any): Promise<any> {
    let $this = this;
    return new Promise((resolve, reject) => {
      let url = "/FzuAttendance/" + $url;
      this.http.post(url, this.encodeComplexHttpParams($params)).subscribe(res => {
        resolve(res);
      }, error => {
        if (error.status == 200){
        } else {
          console.error(error);
        }
      });
    })
  }

  put($url: string, $params?: any): Promise<any> {
    let $this = this;
    return new Promise((resolve, reject) => {
      let url = "/FzuAttendance/" + $url;
      $params['_method'] = 'PUT';
       this.http.post(url, this.encodeComplexHttpParams($params)).subscribe(res => {
        resolve(res);
      }, error => {
        if (error.status == 200){
        } else {
          console.error(error);
        }
      });
    })
  }

  delete($url: string, $params?: any): Promise<any> {
    let $this = this;
    return new Promise((resolve, reject) => {
      let url = "/FzuAttendance/" + $url;
      $params['_method'] = 'DELETE';
      this.http.post(url, this.encodeComplexHttpParams($params)).subscribe(res => {
        resolve(res);
      }, error => {
        if (error.status == 200){
        } else {
          console.error(error);
        }
      });
    })
  }


}
