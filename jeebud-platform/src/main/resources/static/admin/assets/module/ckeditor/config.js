/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';

    // 图片上传配置
    config.image_previewText = ' ';  // 清除图片预览的文字
    config.filebrowserImageUploadUrl = SERVER_CTX + "/ckeditor/upload";  // 图片上传url


    // 文件上传配置(音频、视频、flash等)
    config.filebrowserUploadUrl = SERVER_CTX + "/ckeditor/upload";  // 文件上传url
    config.extraPlugins = 'image2';
    config.toolbarGroups = [
        { name: 'document', groups: [ 'document', 'mode', 'doctools' ] },
        { name: 'clipboard', groups: [ 'undo', 'clipboard' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'insert', groups: [ 'insert' ] },
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'Templates,Anchor,CodeSnippet,Flash,Iframe,ShowBlocks,Html5audio,HorizontalRule,Copy,Cut,Paste,Smiley,PasteText,PasteFromWord,Outdent,Indent';
};
