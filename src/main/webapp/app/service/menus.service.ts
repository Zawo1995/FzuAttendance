import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})

export class MenusService {

    private menusOptions = new Subject();
    public menusOptionsObservable = this.menusOptions.asObservable();
    options: MenuOptions = new MenuOptions();

    constructor(){  }

    /**
     * 设置一些选项
     * @param options
     * @param reset 是否重新设置
     * @returns {MenuOptions}
     */
    setOptions(options: MenuOptions, reset?: boolean): MenuOptions{
        if (reset){
            this.options = options;
        } else {
            for (let key in options){
                this.options[key] = options[key];
            }
        }
        this.menusOptions.next(this.options);
        return this.options;
    }

    /**
     * 获得选项内容
     * @param key
     * @returns {{name?: string, link?: string, sign?: (string|number), children?: Array<MenuOptions>, [p: string]: any}}
     */
    getOption(key: string): any{
        return this.options[key];
    }

    getOptions(): MenuOptions{
        return this.options;
    }

}

export class MenuOptions{
    [key: string]: MenuOptionsValues
}

export class MenuOptionsValues{
    name?: string;
    link?: string;
    sign?: string;
    children?: Array<MenuOptions>;
    [key: string]: any
}

