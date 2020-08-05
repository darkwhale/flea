
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


// 获取用户信息
function ajax_user_info() {
    $.ajax({
        type: "get",
        url: "flea/user/user",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    //写个人信息
                    var main_body = document.getElementById("main_body");
                    // 清空main_body内容
                    main_body.innerHTML = "";

                    // 添加修改按钮；
                    var exchange_info = document.createElement("button");
                    exchange_info.setAttribute("class", "exchange-button btn-sm");
                    exchange_info.textContent = "修改信息";
                    exchange_info.onclick = function () {
                        change_user_info(message);
                    };
                    main_body.appendChild(exchange_info);

                    var line = document.createElement("hr");
                    main_body.appendChild(line);

                    var user_table = document.createElement("table");
                    // user_table.style.border = "0px";
                    main_body.appendChild(user_table);

                    var user_table_body = document.createElement("tbody");
                    user_table.appendChild(user_table_body);

                    //创建内容并插入
                    add_user_info(user_table_body, "昵称:", message.data.username);
                    add_user_info(user_table_body, "性别:", message.data.gender);
                    add_user_info(user_table_body, "手机:", message.data.userMobile);
                    add_user_info(user_table_body, "QQ:", message.data.userQq);
                    add_user_info(user_table_body, "微信:", message.data.userWx);
                    add_user_info(user_table_body, "入学年份:", message.data.enterYear);
                    add_user_info(user_table_body, "学院:", message.data.userCampus);
                    add_user_info(user_table_body, "宿舍地址:", message.data.userResideAddress);
                    add_user_info(user_table_body, "学习地址:", message.data.userStudyAddress);

                }else{
                    alert("用户已退出登录");
                    clear_cache();
                    window.location = get_main_url();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

}


// 书摊
function ajax_book_booth() {

    $.ajax({
        type: "get",
        url: "flea/bookBooth/bookBooth",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 如果data为null，则需要创建书摊;
                    var main_body = document.getElementById("main_body");

                    // 清空main_body内容
                    main_body.innerHTML = "";

                    var exchange_info = document.createElement("button");
                    exchange_info.setAttribute("class", "exchange-button btn-sm");

                    exchange_info.onclick = function () {
                        exchange_book_booth(message.data);
                    };

                    if (message.data === null) {
                        // 添加创建按钮；
                        exchange_info.textContent = "创建书摊";
                    }else {
                        exchange_info.textContent = "编辑书摊";
                    }
                    main_body.appendChild(exchange_info);

                    if (message.data !== null) {
                        // 创建擦亮按钮;
                        var rub_button = document.createElement("button");
                        rub_button.setAttribute("class", "exchange-button btn-sm rub_button");
                        rub_button.textContent = "擦亮书摊";
                        rub_button.onclick = function () {
                            rub_book_booth();
                        };
                        main_body.appendChild(rub_button);

                        // 创建删除按钮
                        var delete_button = document.createElement("button");
                        delete_button.setAttribute("class", "exchange-button btn-sm rub_button");
                        delete_button.textContent = "关闭书摊";
                        delete_button.onclick = function () {
                            var close_confirm = window.confirm("确定要关闭书摊？");
                            if (close_confirm === true) {
                                close_book_booth();
                            }
                        };
                        main_body.appendChild(delete_button);
                    }

                    var line = document.createElement("hr");
                    main_body.appendChild(line);

                    if (message.data === null) {

                        var prompt_info = document.createElement("blockquote");

                        var prompt_info_p = document.createElement("p");
                        prompt_info_p.innerText = "您还没有书摊，快去创建吧～";

                        var prompt_info_small = document.createElement("small");
                        prompt_info_small.innerText = "一位善意的智者";

                        prompt_info.appendChild(prompt_info_p);
                        prompt_info.appendChild(prompt_info_small);

                        main_body.appendChild(prompt_info);
                    }else {
                        // 创建表格
                        var user_table = document.createElement("table");
                        // user_table.style.border = "0px";
                        main_body.appendChild(user_table);

                        var user_table_body = document.createElement("tbody");
                        user_table.appendChild(user_table_body);

                        //创建内容并插入
                        add_user_info(user_table_body, "小摊名:", message.data.boothName);
                        add_user_info(user_table_body, "专业:", message.data.boothCampus);
                        add_user_info(user_table_body, "小摊位置:", message.data.address);
                        add_user_info(user_table_body, "小摊简介:", message.data.synopsis);

                        add_image(user_table_body, "小摊图片", message.data.icon);
                    }

                }else{
                    alert("用户已退出登录");
                    clear_cache();
                    window.location = get_main_url();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function close_book_booth() {
    $.ajax({
        type: "post",
        url: "flea/bookBooth/close",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 擦亮成功;
                    ajax_book_booth();
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function close_wares_booth() {
    $.ajax({
        type: "post",
        url: "flea/waresBooth/close",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 擦亮成功;
                    ajax_wares_booth();
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function ajax_wares_booth() {

    $.ajax({
        type: "get",
        url: "flea/waresBooth/waresBooth",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 如果data为null，则需要创建书摊;
                    var main_body = document.getElementById("main_body");

                    // 清空main_body内容
                    main_body.innerHTML = "";

                    var exchange_info = document.createElement("button");
                    exchange_info.setAttribute("class", "exchange-button btn-sm");

                    exchange_info.onclick = function () {
                        exchange_wares_booth(message.data);
                    };

                    if (message.data === null) {
                        // 添加创建按钮；
                        exchange_info.textContent = "创建杂货摊";
                    }else {
                        exchange_info.textContent = "编辑杂货摊";
                    }
                    main_body.appendChild(exchange_info);

                    if (message.data !== null) {
                        // 创建擦亮按钮;
                        var rub_button = document.createElement("button");
                        rub_button.setAttribute("class", "exchange-button btn-sm rub_button");
                        rub_button.textContent = "擦亮杂货摊";
                        rub_button.onclick = function () {
                            rub_wares_booth();
                        };
                        main_body.appendChild(rub_button);

                        // 创建删除按钮
                        var delete_button = document.createElement("button");
                        delete_button.setAttribute("class", "exchange-button btn-sm rub_button");
                        delete_button.textContent = "关闭杂货摊";
                        delete_button.onclick = function () {
                            var close_confirm = window.confirm("确定要关闭杂货摊？");
                            if (close_confirm === true) {
                                close_wares_booth();
                            }
                        };
                        main_body.appendChild(delete_button);
                    }

                    var line = document.createElement("hr");
                    main_body.appendChild(line);

                    if (message.data === null) {

                        var prompt_info = document.createElement("blockquote");

                        var prompt_info_p = document.createElement("p");
                        prompt_info_p.innerText = "您还没有杂货摊，快去创建吧～";

                        var prompt_info_small = document.createElement("small");
                        prompt_info_small.innerText = "一位善意的智者";

                        prompt_info.appendChild(prompt_info_p);
                        prompt_info.appendChild(prompt_info_small);

                        main_body.appendChild(prompt_info);
                    }else {
                        // 创建表格
                        var user_table = document.createElement("table");
                        // user_table.style.border = "0px";
                        main_body.appendChild(user_table);

                        var user_table_body = document.createElement("tbody");
                        user_table.appendChild(user_table_body);

                        //创建内容并插入
                        add_user_info(user_table_body, "小摊名:", message.data.boothName);
                        add_user_info(user_table_body, "小摊位置:", message.data.address);
                        add_user_info(user_table_body, "小摊简介:", message.data.synopsis);

                        add_image(user_table_body, "小摊图片", message.data.icon);
                    }

                }else{
                    alert("用户已退出登录");
                    clear_cache();
                    window.location = get_main_url();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });

}


function ajax_sales_booth() {
    var main_body = document.getElementById("main_body");

    // 清空main_body内容
    main_body.innerHTML = "";

    var exchange_info = document.createElement("button");
    exchange_info.setAttribute("class", "exchange-button btn-sm");

    exchange_info.onclick = function () {
        add_sales(message.data);
    };

    exchange_info.textContent = "新建商品";
    main_body.appendChild(exchange_info);

    var line = document.createElement("hr");
    main_body.appendChild(line);

    $.ajax({
        type: "get",
        url: "flea/sales/userSales",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    //  数组为空，表示没有商品；
                    if (message.data.length === 0) {
                        var prompt_info = document.createElement("blockquote");

                        var prompt_info_p = document.createElement("p");
                        prompt_info_p.innerText = "您还没有展览任何商品哦，快去挂售吧～";

                        var prompt_info_small = document.createElement("small");
                        prompt_info_small.innerText = "一位善意的智者";

                        prompt_info.appendChild(prompt_info_p);
                        prompt_info.appendChild(prompt_info_small);

                        main_body.appendChild(prompt_info);
                    }else {
                        for(index in message.data) {
                            sales = message.data[index];
                            create_sales_style(
                                main_body,
                                sales.salesId,
                                sales.icon,
                                sales.salesName,
                                sales.synopsis,
                                sales.newLevel,
                                sales.items
                                )
                        }

                    }
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function add_sales() {

}


function rub_book_booth() {
    $.ajax({
        type: "post",
        url: "flea/bookBooth/rub",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 擦亮成功;
                    alert("擦亮成功");
                    ajax_book_booth();
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function rub_wares_booth() {
    $.ajax({
        type: "post",
        url: "flea/waresBooth/rub",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 擦亮成功;
                    alert("擦亮成功");
                    ajax_wares_booth();
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function add_user_info(container, name, value) {
    if (value === null) {
        value = "未设置";
    }
    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    var sub_value = document.createElement("td");
    sub_value.innerText = value;
    sub_tr.appendChild(sub_label);
    sub_tr.appendChild(sub_value);
}

function add_image(container, name, value) {
    var sub_tr = document.createElement("tr");
    container.appendChild(sub_tr);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    var sub_value = document.createElement("td");
    var sub_image = document.createElement("img");

    if (value !== null) {
        sub_image.src = "images/" + value + '?t='+(+new Date());

    }

    sub_image.style.height = booth_image_size;
    sub_image.style.width = booth_image_size;

    sub_value.appendChild(sub_image);
    sub_tr.appendChild(sub_label);
    sub_tr.appendChild(sub_value);
}

function change_user_info(message) {

    var main_body = document.getElementById("main_body");
    // 清空main_body内容
    main_body.innerHTML = "";

    // 添加修改按钮；
    var exchange_info = document.createElement("button");
    exchange_info.setAttribute("class", "exchange-button btn-sm");
    exchange_info.textContent = "保存信息";
    exchange_info.onclick = function () {
        post_user_info();

    };
    main_body.appendChild(exchange_info);

    var line = document.createElement("hr");
    main_body.appendChild(line);

    var user_table = document.createElement("table");
    user_table.style.border = "0px";
    main_body.appendChild(user_table);

    var user_table_body = document.createElement("tbody");
    user_table.appendChild(user_table_body);

    //创建内容并插入
    exchange_user_info(user_table_body, "昵称:", message.data.username, "change_username");
    exchange_user_info(user_table_body, "性别:", message.data.gender, "gender");
    exchange_user_info(user_table_body, "手机:", message.data.userMobile, "userMobile");
    exchange_user_info(user_table_body, "QQ:", message.data.userQq, "userQq");
    exchange_user_info(user_table_body, "微信:", message.data.userWx, "userWx");
    exchange_user_info(user_table_body, "入学年份:", message.data.enterYear, "enterYear");
    exchange_user_info(user_table_body, "学院:", message.data.userCampus, "userCampus");
    exchange_user_info(user_table_body, "宿舍地址:", message.data.userResideAddress, "userResideAddress");
    exchange_user_info(user_table_body, "学习地址:", message.data.userStudyAddress, "userStudyAddress");

}


function exchange_user_info(container, name, value, label_id) {

    var sub_div = document.createElement("tr");
    container.appendChild(sub_div);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    var sub_value_td = document.createElement("td");

    if(label_id === "userCampus" || label_id === "userResideAddress" || label_id === "userStudyAddress") {

        if (label_id === "userCampus") {
            info_list = ajax_campus_list();
        }
        if(label_id === "userResideAddress") {
            info_list = ajax_life_address_list();
        }
        if(label_id === "userStudyAddress") {
            info_list = ajax_study_address_list();
        }

        sub_value = document.createElement("select");
        sub_value.setAttribute("id", label_id);

        for (index in info_list) {
            info = info_list[index];
            sub_option = document.createElement("option");

            if (label_id === "userCampus") {
                sub_option.value = info.campusId;
                sub_option.innerText = info.campusName;

            }else {
                sub_option.value = info.addressId;
                sub_option.innerText = info.addressRegion + " " + info.addressFloor;

            }

            if (value === info.campusName) {
                sub_option.selected = true;
            }

            sub_value.appendChild(sub_option);
        }
    }else if (label_id === "gender") {
        sub_value = document.createElement("div");
        sub_value.setAttribute("id", label_id);

        if (value === "女") {
            sub_value.innerHTML = "<label><input type='radio' name='gender' value='0'>男</label>" +
                "<label><input type='radio' name='gender' value='1' checked>女</label>"

        }else {
            sub_value.innerHTML = "<label><input type='radio' name='gender' value='0' checked>男</label>" +
                "<label><input type='radio' name='gender' value='1'>女</label>"
        }

    }else if(label_id === "enterYear") {
        info_list = get_enter_year_list();

        sub_value = document.createElement("select");
        sub_value.setAttribute("id", label_id);

        for (index in info_list) {
            info = info_list[index];
            sub_option = document.createElement("option");

            sub_option.value = info;
            sub_option.innerText = info;


            if (value === info) {
                sub_option.selected = true;
            }

            sub_value.appendChild(sub_option);
        }
    }else {
        sub_value = document.createElement("input");
        sub_value.setAttribute("id", label_id);
        sub_value.setAttribute("type", "text");
        sub_value.value = value;
    }

    sub_value_td.appendChild(sub_value);

    sub_div.appendChild(sub_label);
    sub_div.appendChild(sub_value_td);
}


// 提交用户信息
function post_user_info() {

    var userGender_list = document.getElementsByName("gender");
    var userGender = null;
    for (i = 0; i < userGender_list.length; i++) {
        if(userGender_list[i].checked) {
            userGender = userGender_list[i].value;
        }
    }

    var username = document.getElementById("change_username").value;
    var userMobile = document.getElementById("userMobile").value;
    var userQq = document.getElementById("userQq").value;
    var userWx = document.getElementById("userWx").value;
    var enterYear = document.getElementById("enterYear").value;
    var userCampusId = document.getElementById("userCampus").value;
    var userResideAddress = document.getElementById("userResideAddress").value;
    var userStudyAddress = document.getElementById("userStudyAddress").value;

    $.ajax({
        type: "post",
        url: "flea/user/update",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "username": username,
            "userGender": userGender,
            "userMobile": userMobile,
            "userQq": userQq,
            "userWx": userWx,
            "enterYear": enterYear,
            "userCampusId": userCampusId,
            "userResideAddressId": userResideAddress,
            "userStudyAddressId": userStudyAddress,
        }),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    document.cookie = document.cookie = 'username=' + message.data.username;
                    ajax_user_info();
                }else{
                    alert("用户已退出登录");
                    clear_cache();
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function post_book_booth() {

    var address_id = document.getElementById("booth_address").value;
    var campus_id = document.getElementById("booth_campus").value;
    var booth_name = document.getElementById("booth_name").value;
    var booth_synopsis = document.getElementById("booth_synopsis").value;
    var booth_icon = document.getElementById("image_window").src;

    $.ajax({
        type: "post",
        url: "flea/bookBooth/modify",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "addressId": address_id,
            "campusId": campus_id,
            "boothName": booth_name,
            "synopsis": booth_synopsis,
            "image_info": booth_icon,
        }),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    ajax_book_booth();
                }else{
                    alert("数据错误");
                    if (message.code === 3) {
                        clear_cache();
                    }
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(){
            alert("访问错误");
        }
    });

}

function post_wares_booth() {

    var address_id = document.getElementById("booth_address").value;
    var booth_name = document.getElementById("booth_name").value;
    var booth_synopsis = document.getElementById("booth_synopsis").value;
    var booth_icon = document.getElementById("image_window").src;

    $.ajax({
        type: "post",
        url: "flea/waresBooth/modify",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "addressId": address_id,
            "boothName": booth_name,
            "synopsis": booth_synopsis,
            "image_info": booth_icon,
        }),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    ajax_wares_booth();
                }else{
                    alert("数据错误");
                    if (message.code === 3) {
                        clear_cache();
                    }
                }
            }else {
                alert("数据错误");
            }
        },
        error: function(){
            alert("访问错误");
        }
    });

}

function exchange_book_booth(data) {

    // 创建或编辑小摊；
    var main_body = document.getElementById("main_body");
    // 清空main_body内容
    main_body.innerHTML = "";

    // 添加保存按钮；
    var exchange_info = document.createElement("button");
    exchange_info.setAttribute("class", "exchange-button btn-sm");
    exchange_info.textContent = "保存信息";
    exchange_info.onclick = function () {
        post_book_booth();

    };
    main_body.appendChild(exchange_info);

    var line = document.createElement("hr");
    main_body.appendChild(line);

    var user_table = document.createElement("table");
    user_table.style.border = "0px";
    main_body.appendChild(user_table);

    var user_table_body = document.createElement("tbody");
    user_table.appendChild(user_table_body);

    data_username = null;
    data_boothCampus = null;
    data_synopsis = null;
    data_address = null;
    data_icon = null;
    //创建内容并插入
    if (data !== null) {
        data_username = data.boothName;
        data_boothCampus = data.boothCampus;
        data_synopsis = data.synopsis;
        data_address = data.address;
        data_icon = data.icon;
    }
    exchange_book_booth_value(user_table_body, "小摊名字:", data_username, "booth_name");
    exchange_book_booth_value(user_table_body, "书籍专业:", data_boothCampus, "booth_campus");
    exchange_book_booth_value(user_table_body, "小摊简介:", data_synopsis, "booth_synopsis");
    exchange_book_booth_value(user_table_body, "小摊位置:", data_address, "booth_address");
    exchange_book_booth_value(user_table_body, "小摊招牌:", data_icon, "booth_icon");

}

function exchange_wares_booth(data) {

    // 创建或编辑小摊；
    var main_body = document.getElementById("main_body");
    // 清空main_body内容
    main_body.innerHTML = "";

    // 添加保存按钮；
    var exchange_info = document.createElement("button");
    exchange_info.setAttribute("class", "exchange-button btn-sm");
    exchange_info.textContent = "保存信息";
    exchange_info.onclick = function () {
        post_wares_booth();

    };
    main_body.appendChild(exchange_info);

    var line = document.createElement("hr");
    main_body.appendChild(line);

    var user_table = document.createElement("table");
    user_table.style.border = "0px";
    main_body.appendChild(user_table);

    var user_table_body = document.createElement("tbody");
    user_table.appendChild(user_table_body);

    data_username = null;
    data_synopsis = null;
    data_address = null;
    data_icon = null;
    //创建内容并插入
    if (data !== null) {
        data_username = data.boothName;
        data_synopsis = data.synopsis;
        data_address = data.address;
        data_icon = data.icon;
    }
    exchange_wares_booth_value(user_table_body, "小摊名字:", data_username, "booth_name");
    exchange_wares_booth_value(user_table_body, "小摊简介:", data_synopsis, "booth_synopsis");
    exchange_wares_booth_value(user_table_body, "小摊位置:", data_address, "booth_address");
    exchange_wares_booth_value(user_table_body, "小摊招牌:", data_icon, "booth_icon");

}

function exchange_book_booth_value(container, name, value, label_id) {

    var sub_div = document.createElement("tr");
    container.appendChild(sub_div);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    var sub_value_td = document.createElement("td");
    if(label_id === "booth_campus" || label_id === "booth_address") {
        if (label_id === "booth_campus") {
            info_list = ajax_campus_list();
        }
        if (label_id === "booth_address") {
            info_list = ajax_all_address_list();
        }

        sub_value = document.createElement("select");
        sub_value.setAttribute("id", label_id);

        for (index in info_list) {
            info = info_list[index];
            sub_option = document.createElement("option");

            if (label_id === "booth_campus") {
                sub_option.value = info.campusId;
                sub_option.innerText = info.campusName;

            }else {
                sub_option.value = info.addressId;
                sub_option.innerText = info.addressRegion + " " + info.addressFloor;
            }

            if (value === info) {
                sub_option.selected = true;
            }

            sub_value.appendChild(sub_option);
        }
    }else if (label_id === "booth_icon"){

        sub_value = document.createElement("div");

        sub_image_div = document.createElement("div");
        sub_image = document.createElement("img");
        sub_image.setAttribute("id", "image_window");
        // sub_image.src = value;
        if (value !== null) {
            sub_image.src = "images/" + value + '?t='+(+new Date());
        }
        sub_image.style.height = booth_image_size;
        sub_image.style.width = booth_image_size;
        sub_image_div.appendChild(sub_image);

        sub_file_div = document.createElement("div");

        sub_file_input = document.createElement("input");
        sub_file_input.type = "file";
        sub_file_input.setAttribute("id", "input_file");
        sub_file_input.setAttribute("class", "image-input-file")
        sub_file_input.accept = "image/png,image/jpeg";
        sub_file_input.onchange = function () {
            image_change();
        };

        sub_file_div.appendChild(sub_file_input);

        sub_value.appendChild(sub_image_div);
        sub_value.appendChild(sub_file_div);


    }else if(label_id === "booth_synopsis") {
        sub_value = document.createElement("textarea");
        sub_value.setAttribute("id", label_id);
        sub_value.setAttribute("rows", "3");
        sub_value.rows = 3;
        sub_value.value = value;
    }else {
        sub_value = document.createElement("input");
        sub_value.setAttribute("id", label_id);
        sub_value.setAttribute("type", "text");
        sub_value.value = value;
    }

    sub_value_td.appendChild(sub_value);

    sub_div.appendChild(sub_label);
    sub_div.appendChild(sub_value_td);
}

function exchange_wares_booth_value(container, name, value, label_id) {

    var sub_div = document.createElement("tr");
    container.appendChild(sub_div);
    var sub_label = document.createElement("td");
    sub_label.innerHTML = name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    var sub_value_td = document.createElement("td");
    if(label_id === "booth_address") {

        info_list = ajax_all_address_list();

        sub_value = document.createElement("select");
        sub_value.setAttribute("id", label_id);

        for (index in info_list) {
            info = info_list[index];
            sub_option = document.createElement("option");

            sub_option.value = info.addressId;
            sub_option.innerText = info.addressRegion + " " + info.addressFloor;

            if (value === info) {
                sub_option.selected = true;
            }

            sub_value.appendChild(sub_option);
        }
    }else if (label_id === "booth_icon"){

        sub_value = document.createElement("div");

        sub_image_div = document.createElement("div");
        sub_image = document.createElement("img");
        sub_image.setAttribute("id", "image_window");
        // sub_image.src = value;
        if (value !== null) {
            sub_image.src = "images/" + value + '?t='+(+new Date());
        }
        sub_image.style.height = booth_image_size;
        sub_image.style.width = booth_image_size;
        sub_image_div.appendChild(sub_image);

        sub_file_div = document.createElement("div");

        sub_file_input = document.createElement("input");
        sub_file_input.type = "file";
        sub_file_input.setAttribute("id", "input_file");
        sub_file_input.setAttribute("class", "image-input-file")
        sub_file_input.accept = "image/png,image/jpeg";
        sub_file_input.onchange = function () {
            image_change();
        };

        sub_file_div.appendChild(sub_file_input);

        sub_value.appendChild(sub_image_div);
        sub_value.appendChild(sub_file_div);


    }else if(label_id === "booth_synopsis"){
        sub_value = document.createElement("textarea");
        sub_value.setAttribute("id", label_id);
        sub_value.setAttribute("rows", "3");
        sub_value.value = value;
    }else {
        sub_value = document.createElement("input");
        sub_value.setAttribute("id", label_id);
        sub_value.setAttribute("type", "text");
        sub_value.value = value;
    }

    sub_value_td.appendChild(sub_value);

    sub_div.appendChild(sub_label);
    sub_div.appendChild(sub_value_td);
}

function image_change() {
    var file = document.getElementById("input_file").files[0];

    var size = file.size / 1024 / 2014;

    if (size > 0.5) {
        alert("图像过大");
        return;
    }
    var reader = new FileReader();
    reader.onloadend = function () {
        $("#image_window").attr("src", reader.result).attr({
            width: booth_image_size,
            height: booth_image_size
        });
    };
    if (file) {
        reader.readAsDataURL(file);
    }
}




