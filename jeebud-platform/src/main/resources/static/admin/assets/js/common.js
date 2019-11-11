/** EasyWeb iframe v3.1.4 date:2019-08-05 License By http://easyweb.vip */

// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    version: true,
    base: getProjectUrl() + 'assets/module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    Cropper: 'Cropper/Cropper',
    zTree: 'zTree/zTree',
    introJs: 'introJs/introJs',
    fileChoose: 'fileChoose/fileChoose',
    tagsInput: 'tagsInput/tagsInput',
    CKEDITOR: 'ckeditor/ckeditor',
    Split: 'Split/Split',
    cascader: 'cascader/cascader',
    websocket: 'websocket/websocket',
    IconFonts: 'iconFonts/iconFonts',
    editormd: 'editormd/editormd'
}).use(['layer', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var admin = layui.admin;
    admin.getAjaxHeaders = function (requestUrl) {
        var headers = new Array();
        headers.push({name: 'app', value: 'jeebud'});
        return headers;
    }
    admin.ajaxSuccessBefore = function (res, requestUrl) {
        layer.closeAll('loading');
        if(res.code==0){
            return true;
        }
        if(res.code==10401){
            layer.confirm("登录已失效，请重新登录", {
                title: "温馨提示",
                skin: "layui-layer-admin"
            }, function () {
                location.href = SERVER_CTX + '/logout'
            })
            return false;  // 返回false阻止代码执行
        }
        layer.msg(res.message, {icon: 2});
        return false;
    }
    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, window == top ? 600 : 100);

});

// 获取当前项目的根路径，通过获取layui.js全路径截取assets之前的地址
function getProjectUrl() {
    var layuiDir = layui.cache.dir;
    if (!layuiDir) {
        var js = document.scripts, last = js.length - 1, src;
        for (var i = last; i > 0; i--) {
            if (js[i].readyState === 'interactive') {
                src = js[i].src;
                break;
            }
        }
        var jsPath = src || js[last].src;
        layuiDir = jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
    }
    return layuiDir.substring(0, layuiDir.indexOf('assets'));
}