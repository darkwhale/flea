
function ajax_register() {
    var user_email = $("#user_email").val();
    var user_name = $("#user_name").val();
    var password = $("#password").val();

    if (user_email === "") {
        alert("请输入邮箱");
    }else if(user_name ===""){
        alert("请输入用户名");
    }else if (password === "") {
        alert("请输入密码");
    }

    $.ajax({
        type: "post",
        url: "flea/user/register",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"email": user_email, "username": user_name, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    $.ajax({
                        type: "post",
                        url: "flea/user/login",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({"email": user_email, "password": password}),
                        dataType: "json",
                        success: function(new_message){
                            if (new_message){
                                if (new_message.code === 0) {
                                    // 写cookie
                                    document.cookie = 'username=' + new_message.data.username;

                                    window.location=get_main_url();
                                }else{
                                    alert("用户名或密码错误");
                                }
                            }else {
                                alert("数据错误")
                            }
                        },
                        error: function(message){
                            alert("访问错误");
                        }
                    });

                }else{
                    alert("用户名或密码错误");
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
