import {Injectable} from '@angular/core';
@Injectable({
    providedIn: 'root'
})
export class PageTitleService {
    constructor() {

    }

    setTitle(title: String,description:String) {
        $('#pagetitle').html(title);
        $('#pathtitle').html(title);
        $('#description').html(description);
    }
}

