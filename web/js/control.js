
$(document).ready(function(){
    var username = getCookie("username");
    if (username === "") {
        $("#username").hide();
        $("#login").text("登录");
        $("#register").text("注册");
        $("#logout").hide();
    }else {
        $("#username").text(username);
        $("#login").hide();
        $("#register").hide();
        $("#logout").text("退出");
    }
    ajax_amoy();
});

function ajax_amoy() {

    $.ajax({
        type: "get",
        async: false,
        url: "flea/amoy/list",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    var amoy_list =  message.data;
                    var left_container = document.getElementById("left_container");
                    var right_container = document.getElementById("right_container");

                    if (amoy_list.length === 0) {

                        var prompt_info = document.createElement("blockquote");

                        var prompt_info_p = document.createElement("p");
                        prompt_info_p.innerText = "暂时还没有人挂售商品，快去挂售商品吧～";

                        var prompt_info_small = document.createElement("small");
                        prompt_info_small.innerText = "一位善意的智者";

                        prompt_info.appendChild(prompt_info_p);
                        prompt_info.appendChild(prompt_info_small);


                        left_container.appendChild(prompt_info);

                    }else {
                        left_amoy_list = amoy_list.slice(0, (amoy_list.length + 1) / 2);
                        right_amoy_list = amoy_list.slice((amoy_list.length + 1) / 2);

                        for (index in left_amoy_list) {
                            sales = left_amoy_list[index];

                            create_sales_unit(left_container, sales);
                        }
                        for (index in right_amoy_list) {
                            sales = right_amoy_list[index];

                            create_sales_unit(right_container, sales);
                        }
                    }

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

}

function create_sales_unit(container, sales) {
    var parent_table = document.createElement("table");
    parent_table.setAttribute("class", "sales_element_div mover");
    parent_table.onclick = function () {
        window.location.href = get_main_url() + "info.html?user=" + sales.userId;
    };

    var parent_tbody = document.createElement("tbody");
    parent_table.appendChild(parent_tbody);

    var parent_tr = document.createElement("tr");
    parent_tbody.appendChild(parent_tr);

    var image_td = document.createElement("td");
    image_td.setAttribute("width", "20%");
    parent_tr.appendChild(image_td);

    var info_td = document.createElement("td");
    info_td.setAttribute("class", "sales_info_div");
    info_td.setAttribute("align", "left");
    info_td.setAttribute("width", "50%");
    parent_tr.appendChild(info_td);

    var user_td = document.createElement("td");
    user_td.setAttribute("width", "10%");
    parent_tr.appendChild(user_td);

    var image_content = document.createElement("img");
    image_content.style.width = sales_image_size;
    image_content.style.height = sales_image_size;

    if(sales.salesType === 0) {
        image_icon = normal_icon(sales, 0);
    }else {
        image_icon = normal_icon(sales, 1);
    }

    image_content.src = image_server_dir + image_icon + '?t='+(+new Date());

    image_td.appendChild(image_content);

    var sale_name_info = document.createElement("p");
    sale_name_info.setAttribute("class", "sales_name_info");
    sale_name_info.innerText = sales.salesName;
    var new_level_info = document.createElement("p");
    new_level_info.setAttribute("class", "new_level_info");
    new_level_info.innerText = sales.newLevel + "成新" + " " + sales.price + "元";
    // new_level_info.innerText = new_level_list[new_level];
    var items_info = document.createElement("p");
    items_info.innerText = "商品项：" + sales.items;
    var synopsis_info = document.createElement("p");
    synopsis_info.setAttribute("class", "synopsis_info");

    // 处理简介信息；
    if (sales.synopsis.length > 20) {
        synopsis_str = sales.synopsis.slice(0, 15) + "..." + sales.synopsis.slice(sales.synopsis.length - 5);
    }else {
        synopsis_str = sales.synopsis;
    }

    synopsis_info.innerText = "简介：" + synopsis_str;

    info_td.appendChild(sale_name_info);
    info_td.appendChild(new_level_info);
    info_td.appendChild(items_info);
    info_td.appendChild(synopsis_info);

    var user_info = document.createElement("strong");
    if (sales.salesType === 0) {
        user_info.innerText = sales.salesCampus;
    }else {
        user_info.innerText = sales.salesAddress;
    }
    user_td.appendChild(user_info);

    container.append(parent_table);
}
