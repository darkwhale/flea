
function ajax_login() {
    var user_email = $("#user_email").val();
    var password = $("#password").val();

    if (user_email === "") {
        alert("请输入邮箱");
    }else if (password === "") {
        alert("请输入密码");
    }

    login(user_email, password);

}

function login(email, password) {
    $.ajax({
        type: "post",
        url: "flea/user/login",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"email": email, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'user_name=' + message.data.username;
                    document.cookie = 'role=' + message.data.role.toString();

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
}
