
$(function () {
    $.extend({
        printArea: function (classPath) {

            'use strict';

            if (classPath && $.type(classPath) != 'array'){
                console.error("In method $.printArea, the way you use it is illegal.");
                return;
            }

            var options = {
                $classPath: [],
                template: {
                    css: null,
                    iframe: null,
                    html5: null
                }
            };
            if (!options.$classPath.length){
                options.$classPath = classPath;
                this.$classPath = classPath;
            }

            var template = {
                css: '<link rel="stylesheet" href="$$classPath">',
                iframe: '<iframe style="width: 0; height: 0;" name="iframe_$$date" id="iframe_$$date"></iframe>',
                html5: '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>print</title>$$head</head><body>$$body</body></html>'
            };

            function transform(s1, s2, s3) {
                s2 = s2.replace(/\$/g, '\\$');
                var reg = new RegExp(s2, 'g');
                return s1.replace(reg, s3);
            }

            function getTemplate(type) {
                switch (type) {
                    case "css":{
                        if (options.template.css) return options.template.css;
                        options.template.css = "";
                        for (var i in options.$classPath){
                            options.template.css += transform(template.css, "$$classPath", options.$classPath[i]);
                        }
                        return options.template.css;
                    }
                    case "iframe": {
                        if (options.template.iframe){
                            options.template.iframe[0].name = 'iframe_' + new Date().getTime();
                            return options.template.iframe;
                        }else {
                            options.template.iframe = $(transform(template.iframe, "$$date", new Date().getTime())).appendTo('body');
                            options.template.iframe.contents().find("head").append(getTemplate("css"));
                            return options.template.iframe;
                        }
                    }
                    case "html5":{
                        return options.template.html5 = options.template.html5 || transform(template.html5, "$$head", options.template.css);
                    }
                }
            }

            function print($e, usePlugin) {
                var iframe = getTemplate("iframe");
                var contents = iframe.contents();
                contents.find("body").append($e.clone());
                if (usePlugin) {
                    // console.log(contents[0]);
                    LODOP.ADD_PRINT_HTM(10,55,"100%","100%",contents[0]);
                    contents.find("body").html('');
                    LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
                    LODOP.PREVIEW();
                }else {
                    contents[0].execCommand('print');
                    contents.find("body").html('');
                }
            }

            getTemplate('iframe');

            this.printById = function (id, usePlugin) {
                var $id = '#'+id;
                var $e = $($id);

                if (!$e.length) {
                    console.error('DOM does not exist!(id is '+id+')');
                    return;
                }

                print($e, usePlugin);

            };

            this.printByElem = function (elem, usePlugin) {
                var $e = $(elem);

                if (!$e.length) {
                    console.error('DOM of print does not exist!');
                    return;
                }

                print($e, usePlugin);

            };

        }
    });
});


