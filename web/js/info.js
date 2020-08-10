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

    ajax_user_info();

});

function ajax_user_info() {
    userId = GetRequest()["user"];

    userInfo = get_user_info(userId);

    // 在left_container写个人信息；
    var left_container = document.getElementById("left_container");
    show_user_info(left_container, userInfo);

    var main_container = document.getElementById("main_body");
    var bookList = get_book_booth(userId);
    var waresList = get_wares_booth(userId);

    show_book_info(main_container, bookList);

    if(bookList.length !== 0 && waresList.length !== 0) {
        var line = document.createElement("hr");
        main_container.appendChild(line);
    }

    show_wares_info(main_container, waresList);


}

function get_user_info(userId) {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/user/findById",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "userId": userId,
        },
        success: function (message) {
            if (message) {
                if (message.code === 0) {
                    //写个人信息
                    userInfo = message.data;
                }
            }else {
                alert("数据错误");
            }
        }
    });

    return userInfo;
}

function show_user_info(container, userInfo) {

    var user_table = document.createElement("table");
    container.appendChild(user_table);

    var user_table_body = document.createElement("tbody");
    user_table.appendChild(user_table_body);

    add_user_row(user_table_body, "昵称:", userInfo.username);
    add_user_row(user_table_body, "性别:", userInfo.gender);
    add_user_row(user_table_body, "手机:", userInfo.userMobile);
    add_user_row(user_table_body, "QQ:", userInfo.userQq);
    add_user_row(user_table_body, "微信:", userInfo.userWx);
    add_user_row(user_table_body, "入学年份:", userInfo.enterYear);
    add_user_row(user_table_body, "学院:", userInfo.userCampus);
    add_user_row(user_table_body, "宿舍地址:", userInfo.userResideAddress);
    add_user_row(user_table_body, "学习地址:", userInfo.userStudyAddress);

}

function add_user_row(sub_container, name, value) {
    if (value === null || value === "") {
        return
    }
    var sub_tr = document.createElement("tr");
    sub_container.appendChild(sub_tr);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    var sub_value = document.createElement("td");
    sub_value.innerText = value;
    sub_tr.appendChild(sub_label);
    sub_tr.appendChild(sub_value);
}

function get_book_booth(userId) {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/bookBooth/salesList",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "userId": userId,
        },
        success: function (message) {
            if (message) {
                if (message.code === 0) {
                    //写个人信息
                    bookList = message.data;
                }
            }else {
                alert("数据错误");
            }
        }
    });

    return bookList;
}


function get_wares_booth(userId) {
    $.ajax({
        type: "get",
        async: false,
        url: "flea/waresBooth/salesList",
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            "userId": userId,
        },
        success: function (message) {
            if (message) {
                if (message.code === 0) {
                    //写个人信息
                    waresList = message.data;
                }
            }else {
                alert("数据错误");
            }
        }
    });

    return waresList;
}

function show_book_info(container, bookList) {
    if(bookList.length !== 0) {
        var book_title = document.createElement("h4");
        book_title.innerText = "书摊：";
        container.appendChild(book_title);
    }


    for (index in bookList) {
        sales = bookList[index];
        create_book_unit(container, sales, 0);
    }
}

function show_wares_info(container, waresList) {
    if(waresList.length !== 0) {
        var wares_title = document.createElement("h4");
        wares_title.innerText = "小摊：";
        container.appendChild(wares_title);
    }


    for (index in waresList) {
        sales = waresList[index];
        create_book_unit(container, sales, 1);
    }
}

function create_book_unit(container, sales, type) {
    var parent_table = document.createElement("table");
    parent_table.setAttribute("class", "sales_element_div");

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

    if(type === 0) {
        image_icon = normal_icon(sales, 0);

    }else {
        image_icon = normal_icon(sales, 1);
    }
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
    if (type === 0) {
        user_info.innerText = sales.salesCampus;
    }else {
        user_info.innerText = sales.salesAddress;
    }
    user_td.appendChild(user_info);

    container.append(parent_table);
}
