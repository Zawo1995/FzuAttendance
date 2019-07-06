layui.define('jquery', function(exports){
    "use strict";
    var $ = layui.$
        ,hint = layui.hint();
    var CheckBox = function(options){
        this.options = options;
    };
    //初始化
    CheckBox.prototype.init = function(elem){
        var that = this;
        elem.addClass('checkBox'); //添加checkBox样式
        that.checkbox(elem);
    };
    //树节点解析
    CheckBox.prototype.checkbox = function(elem,children){
        var that = this, options = that.options;
        var nodes = children || options.nodes;
        layui.each(nodes, function(index, item){
            var li = $([
                '<li class="'+(item.disabled?'layui-disabled':'')+' block' + (item.on?' on':'') + '"' +
                '    name="' + item.name + '"' +
                '    value="' + item.value + '"' +
                '    '+(item.title?'onmouseover="layui.layer.tips(\''+item.title+'\',this,{tips:2})"':'') +
                '    '+(item.title?'onmouseout="layui.layer.closeAll(\'tips\');"':'')+'>'+
                    (item.label||item.name),
                '	<i class="choice">' +
                '		<i class="triangle"></i>' +
                '		<i class="right layui-icon layui-icon-ok"></i>' +
                '	</i>' +
                (((item.showDel || options.showDel) && !item.disabled)?(
                    '	<i class="del">' +
                    '		<i class="layui-icon layui-icon-delete"></i>' +
                    '	</i>'
                ):'') +
                // '	<span class="hide">'+(item.on?'<input type="hidden" name="'+item.name+'" value="'+item.value+'">':'')+'</span>' +
                '	<span class="hide">' +
                '       <input type="checkbox" name="'+item.name+'" value="'+item.value+'" '+(item.on?'checked':'')+' '+(item.disabled?'disabled':'')+' >' +
                '   </span>' +
                '</li>'
            ].join(''));
            elem.append(li);

            item.elem = li;
            item.checkbox = li.find("span.hide>input[type='checkbox']");

            if (!item.disabled){
                //触发点击节点回调
                typeof options.click === 'function' && that.click(li, item);
                //触发删除节点回调
                typeof options.del === 'function' && that.del(li, item);
            }
        });
        options.success ? options.success(nodes) : '';
    };
    //点击节点回调
    CheckBox.prototype.click = function(elem, item){
        var that = this, options = that.options;
        elem.on('click', function(e){
            var checkbox = elem.find("span.hide>input[type='checkbox']")[0];
            elem.toggleClass("on");
            if(elem.hasClass("on")){
                item.on = true;
                // elem.children("span.hide").html('<input type="hidden" name="'+item.name+'" value="'+item.value+'">');
                checkbox.checked = true;
            }else{
                item.on = false;
                // elem.children("span.hide").html('');
                checkbox.checked = false;
            }

            $(checkbox).trigger('change');

            layui.stope(e);
            options.click(item);
        });
    };
    //  赋值
    CheckBox.prototype.val = function ($name, $value) {
        var that = this, options = that.options, nodes = options.nodes;
        var valueType = $.type($value);

        if ($value){
            var items = (function () {
                var field = [];
                for (var i in nodes){
                    if (nodes[i].name === $name && ((valueType !== 'array' && $value === nodes[i].value) || (valueType === 'array' && $value.indexOf(nodes[i].value) >= 0))) {
                        field.push(nodes[i])
                    }
                }
                return field;
            })();

            for (var i in nodes){
                var item = nodes[i];
                var elem = item.elem;
                var on = items.indexOf(item) >= 0;
                var checkbox = elem.find("span.hide>input[type='checkbox']")[0];
                if (on && !elem.hasClass("on")){
                    elem.toggleClass("on");
                    item.on = true;
                    checkbox.checked = true;
                    // elem.children("span.hide").html('<input type="checkbox" name="'+item.name+'" value="'+item.value+'">');
                }else if (!on && elem.hasClass("on")){
                    elem.toggleClass("on");
                    item.on = false;
                    checkbox.checked = false;
                    // elem.children("span.hide").html('');
                }
            }
        }else if ($name){
            $value = (function () {
                var field = [];
                for (var i in nodes){
                    if (nodes[i].on && nodes[i].name === $name) {
                        field.push(nodes[i].value)
                    }
                }
                if(!/^.*\[\]$/.test($name)) field = field.length>0?field[0]:null;
                return field;
            })();
        }else {
            $value = (function () {
                var field = {};
                for (var i in nodes){
                    var item = nodes[i];
                    if (!item.on) continue;
                    if(/^.*\[\]$/.test(item.name)){
                        var key = item.name.replace(/^(.*)\[\]$/, '$1');
                        if (!field[key]) field[key] = [];
                        field[key].push(item.value);
                    }else{
                        field[item.name] = item.value;
                    }
                }
                return field;
            })();
        }


        return $value;

    };
    //点击节点回调
    CheckBox.prototype.del = function(elem, item){
        var that = this, options = that.options;
        elem.children('i.del').on('click', function(e){
            var index = layer.confirm('确定删除 ['+(item.label||item.name)+'] 吗？', {
                btn: ['删除','取消']
            }, function(){
                layer.close(index);
                if(options.del(item)){
                    elem.closest(".block").remove();
                    layui.stope(e);
                }
            });
            return false;
        });
    };

    //暴露接口
    exports('checkbox', function(options){
        var checkbox = new CheckBox(options = options || {});
        var elem = $(options.elem);
        if(!elem[0]){
            return hint.error('layui.checkbox 没有找到'+ options.elem +'元素');
        }
        checkbox.init(elem);
        return checkbox;
    });
}).link('assets/layui/checkbox/checkbox.css','checkboxcss');
