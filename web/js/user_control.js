
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

    // 控制显示
    ajax_user_info();

});

// 获取用户信息
function ajax_user_info() {
    $.ajax({
        type: "get",
        url: "flea/user/user",
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
                    exchange_info.setAttribute("onclick", "");
                    exchange_info.textContent = "修改信息";
                    main_body.appendChild(exchange_info);

                    var line = document.createElement("hr");
                    main_body.appendChild(line);

                    //创建内容并插入
                    add_user_info(main_body, "昵称:", message.data.username);
                    add_user_info(main_body, "性别:", message.data.gender);
                    add_user_info(main_body, "学院:", message.data.userCampus);
                    add_user_info(main_body, "手机:", message.data.userMobile);
                    add_user_info(main_body, "宿舍地址:", message.data.userResideAddress);
                    add_user_info(main_body, "学习地址:", message.data.userStudyAddress);
                    add_user_info(main_body, "入学年份:", message.data.enterYear);
                    add_user_info(main_body, "QQ:", message.data.userQq);
                    add_user_info(main_body, "微信:", message.data.userWx);

                }else if (message.code === 1){
                    alert("用户已存在");
                }else{
                    alert("数据错误")
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

function add_user_info(container, name, value) {
    if (value === null) {
        value = "未设置";
    }
    var sub_div = document.createElement("div");
    container.appendChild(sub_div);
    var sub_label = document.createElement("label");
    sub_label.innerText = name;
    var sub_value = document.createElement("strong");
    sub_value.innerText = value;
    sub_div.appendChild(sub_label);
    sub_div.appendChild(sub_value);
}

function change_user_info() {
    $.ajax({
        type: "get",
        url: "flea/user/user",
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    //写个人信息
                    var main_body = document.getElementById("main_body");
                    // 清空main_body内容
                    main_body.innerHTML = "";

                    // 添加修改按钮；
                    var exchange_info = document.createElement("a");
                    exchange_info.setAttribute("class", "exchange-link");
                    exchange_info.setAttribute("href", "");
                    exchange_info.setAttribute("onclick", "")
                    exchange_info.textContent = "修改信息";
                    main_body.appendChild(exchange_info);

                    //创建内容并插入
                    add_user_info(main_body, "昵称:", message.data.username);
                    add_user_info(main_body, "性别:", message.data.gender);
                    add_user_info(main_body, "学院:", message.data.userCampus);
                    add_user_info(main_body, "手机:", message.data.userMobile);
                    add_user_info(main_body, "宿舍地址:", message.data.userResideAddress);
                    add_user_info(main_body, "学习地址:", message.data.userStudyAddress);
                    add_user_info(main_body, "入学年份:", message.data.enterYear);
                    add_user_info(main_body, "QQ:", message.data.userQq);
                    add_user_info(main_body, "微信:", message.data.userWx);

                }else if (message.code === 1){
                    alert("用户已存在");
                }else{
                    alert("数据错误")
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
