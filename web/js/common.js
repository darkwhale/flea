

function clear_cache() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if(keys) {
        for(var i = keys.length; i--;) {
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
        }
    }
}

function getCookie(name)
{
    var start;
    var end;
    if (document.cookie.length > 0) {
        start = document.cookie.indexOf(name + "=");
        if (start !== -1) {
            start = start + name.length + 1;
            end = document.cookie.indexOf(";", start);
            if (end === -1){
                end = document.cookie.length;
            }
            return document.cookie.substring(start, end);
        }
    }
    return ""
}

function get_main_url() {
    return "http://127.0.0.1/";
}

function get_enter_year_list() {
    return ["2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"];
}

function ajax_campus_list() {

    $.ajax({
        type: "get",
        async: false,
        url: "flea/campus/getList",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    campus_list =  message.data;

                }else{
                    alert("服务器错误");
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return campus_list;
}

function ajax_study_address_list() {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/address/studyList",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    study_list = message.data;

                }else{
                    alert("服务器错误");
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return study_list;
}

function ajax_life_address_list() {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/address/lifeList",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    life_list = message.data;

                }else{
                    alert("服务器错误");
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return life_list;
}


function ajax_all_address_list() {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/address/floorList",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    address_list = message.data;

                }else{
                    alert("服务器错误");
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

    return address_list;
}
// 设置弹出窗口样式
// window.alert = function (str) {
//     var shield = document.createElement("DIV");
//     p = document.createElement("p");
//     shield.appendChild(p);
//
//     p.textContent = str;
// };

function create_image_style(icon, sale_name) {

}
