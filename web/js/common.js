$(document).ready(function(){
    $('#search_input').keydown(function (e) {
        if(e.which === 13) {
            $('#search_button').trigger("click");
        }
    });
});


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


function ajax_all_region_list() {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/address/regionList",
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


function ajax_all_floor_list() {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/address/nonRegionList",
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
//
// function create_sales_style(container, salesId, icon, sale_name, synopsis, new_level, items) {
//     var parent_div = document.createElement("div");
//     parent_div.setAttribute("class", "sales_element_div");
//
//     var salesId_info = document.createElement("p");
//     salesId_info.hidden = true;
//     salesId_info.innerText = salesId;
//     parent_div.appendChild(salesId_info);
//
//     // 图像div
//     var image_div = document.createElement("div");
//     // image_div.style = "display: inline-block";
//     image_div.setAttribute("style", "display: inline-block; vertical-align:top");
//     var image_content = document.createElement("img");
//     image_content.style.width = sales_image_size;
//     image_content.style.height = sales_image_size;
//     image_content.src = "images/" + icon + '?t='+(+new Date());
//
//     image_div.appendChild(image_content);
//
//     // 文字div
//     var info_div = document.createElement("div");
//     info_div.setAttribute("class", "sales_info_div");
//     info_div.setAttribute("style", "display: inline-block; vertical-align:top");
//     var sale_name_info = document.createElement("p");
//     sale_name_info.setAttribute("class", "sales_name_info");
//     sale_name_info.innerText = sale_name;
//     var new_level_info = document.createElement("p");
//     new_level_info.setAttribute("class", "new_level_info");
//     new_level_info.innerText = new_level + "成新";
//     // new_level_info.innerText = new_level_list[new_level];
//     var items_info = document.createElement("p");
//     items_info.innerText = "书籍：" + items;
//     var synopsis_info = document.createElement("p");
//     synopsis_info.setAttribute("class", "synopsis_info");
//     synopsis_info.innerText = "简介：" + synopsis;
//
//     info_div.appendChild(sale_name_info);
//     info_div.appendChild(new_level_info);
//     info_div.appendChild(items_info);
//     info_div.appendChild(synopsis_info);
//
//     // 编辑和删除按钮
//     var control_div = document.createElement("div");
//
//
//     parent_div.appendChild(image_div);
//     parent_div.appendChild(info_div);
//
//     container.append(parent_div);
// }

function GetRequest(name) {
    var url = location.search;
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}


function normal_icon(sales, type) {
    var return_icon = sales.icon;
    if (sales.icon === null) {
        if (type === 0) {
            return_icon = book_default_icon;
        }else {
            return_icon = wares_default_icon;
        }
    }

    return return_icon;
}

function ajax_search() {
    var keyword = document.getElementById("search_input").value;

    if (keyword !== "" && keyword.trim().split(/\s+/).length < 5) {
        window.location.href = get_main_url() + "search.html?keyword=" + encodeURI(encodeURI(keyword));
    }
}

var image_server_dir = "http://127.0.0.1/images/";
var booth_image_size = "120px";
var sales_image_size = "120px";
var new_level_list = ["10成新", "9成新", "8成新", "7成新", "6成新", "5成新", "4成新", "3成新", "2成新", "1成新"];
var status_list = ["在售", "已预定", "已下架"];
var book_default_icon = "book_default.jpg";
var wares_default_icon = "wares_default.jpg";

var address_type_list = ["宿舍地址", "学习地址"];

