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

    ajax_campus_show();
    show_campus_list();

});

function ajax_campus_show() {
    var campus_container = document.getElementById("campus_container");

    // 添加全部专业;
    var all_campus_body = document.createElement("h2");
    all_campus_body.setAttribute("class", "index-title");

    campus_container.appendChild(all_campus_body);

    var all_campus_a = document.createElement("a");
    all_campus_a.setAttribute("href", get_main_url() + "book.html");

    all_campus_a.setAttribute("class", "index-link");
    all_campus_a.innerText = "全部";

    all_campus_body.appendChild(all_campus_a);

    campus_list = ajax_campus_list();
    for (index in campus_list) {
        campus = campus_list[index];

        var campus_body = document.createElement("h2");
        campus_body.setAttribute("class", "index-title");

        campus_container.appendChild(campus_body);

        var campus_a = document.createElement("a");
        campus_a.setAttribute("href", get_main_url() + "book.html?campusId=" + campus.campusId);
        campus_a.setAttribute("class", "index-link");
        campus_a.innerText = campus.campusName;

        campus_body.appendChild(campus_a);
    }

}

function show_campus_list() {

    var left_container = document.getElementById("left_container");
    var right_container = document.getElementById("right_container");
    left_container.innerHTML = "";
    right_container.innerHTML = "";

    campus_id = GetRequest()["campusId"];
    pageNum = GetRequest()["pageNum"];
    pageSize = GetRequest()["pageSize"];

    $.ajax({
        type: "get",
        url: "flea/bookBooth/getByCampus",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "campusId": campus_id,
            "pageNum": pageNum,
            "pageSize": pageSize,
        },
        success: function (message) {
            if (message) {
                if (message.code === 0) {
                    //写个人信息
                    result = message.data.content;

                    if (result.length === 0) {
                        var prompt_info = document.createElement("blockquote");

                        var prompt_info_p = document.createElement("p");
                        prompt_info_p.innerText = "这里什么也没有～";

                        var prompt_info_small = document.createElement("small");
                        prompt_info_small.innerText = "一位善意的智者";

                        prompt_info.appendChild(prompt_info_p);
                        prompt_info.appendChild(prompt_info_small);


                        left_container.appendChild(prompt_info);
                    }else {

                        left_book_list = result.slice(0, (result.length + 1) / 2);
                        right_book_list = result.slice((result.length + 1) / 2);

                        for (index in left_book_list) {
                            sales = left_book_list[index];

                            create_sales_unit(left_container, sales);
                        }

                        for (index in right_book_list) {
                            sales = right_book_list[index];

                            create_sales_unit(right_container, sales);
                        }

                        // 创建页码；

                        var first = message.data.first;
                        var last = message.data.last;
                        var totalPages = message.data.totalPages;
                        var number = message.data.number;
                        var size = message.data.size;

                        var page_ul = document.createElement("ul");
                        page_ul.setAttribute("class", "pagination");

                        left_container.appendChild(page_ul);
                        if (typeof (campus_id) === "undefined") {
                            campus_dest = "";
                        }else {
                            campus_dest = "campusId=" + campus_id + "&";
                        }

                        if (size === 20) {
                            size_dest = "";
                        }else {
                            size_dest = "&pageSize=" + size;
                        }

                        // 上一页
                        var page_before = document.createElement("li");
                        page_ul.appendChild(page_before);

                        var page_before_a = document.createElement("a");
                        if(!first) {
                            page_before_a.setAttribute("href", get_main_url() +
                                "book.html?" +
                                campus_dest + "&pageNum=" + (number - 1) + size_dest);
                        }

                        page_before_a.innerText = "<";

                        if(first) {
                            page_before.setAttribute("class", "disabled");
                        }

                        page_before.appendChild(page_before_a);

                        // 计算左右边界
                        window_width = 3;
                        right_width = Math.max(number + window_width, 2 * window_width);
                        left_width = Math.min(number - window_width, totalPages - 2 * window_width - 1);
                        console.log(left_width, right_width);


                        for (index in [...Array(totalPages).keys()]) {

                            if (index < left_width || index > right_width) {
                                continue;
                            }

                            page_index = parseInt(index) + 1;


                            var page_li = document.createElement("li");
                            page_ul.appendChild(page_li);

                            var page_a = document.createElement("a");
                            if (index != number) {
                                page_a.setAttribute("href", get_main_url() +
                                    "book.html?" +
                                    campus_dest + "&pageNum=" + index + size_dest);
                            }

                            page_a.innerText = page_index;

                            if(index == number) {
                                page_li.setAttribute("class", "disabled");
                            }

                            page_li.appendChild(page_a);

                        }

                        // 下一页
                        var page_after = document.createElement("li");
                        page_ul.appendChild(page_after);

                        var page_after_a = document.createElement("a");
                        if(!last) {
                            page_after_a.setAttribute("href", get_main_url() +
                                "book.html?" +
                                campus_dest + "&pageNum=" + (number + 1) + size_dest);
                        }

                        page_after_a.innerText = ">";

                        if(last) {
                            page_after.setAttribute("class", "disabled");
                        }
                        page_after.appendChild(page_after_a);

                    }

                }
            }else {
                alert("数据错误");
            }
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
    user_td.setAttribute("width", "20%");
    parent_tr.appendChild(user_td);

    var image_content = document.createElement("img");
    image_content.style.width = sales_image_size;
    image_content.style.height = sales_image_size;

    image_icon = normal_icon(sales, 0);

    image_content.src = "images/" + image_icon + '?t='+(+new Date());

    image_td.appendChild(image_content);

    var sale_name_info = document.createElement("p");
    sale_name_info.setAttribute("class", "sales_name_info");
    sale_name_info.innerText = sales.salesName;
    var new_level_info = document.createElement("p");
    new_level_info.setAttribute("class", "new_level_info");
    new_level_info.innerText = sales.newLevel + "成新" + " " + sales.price + "元";
    // new_level_info.innerText = new_level_list[new_level];
    var items_info = document.createElement("p");
    items_info.innerText = "价格：" + sales.items;
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
    user_info.innerText = sales.salesCampus;

    user_td.appendChild(user_info);

    container.append(parent_table);
}
