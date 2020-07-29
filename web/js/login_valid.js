
function ajax_login() {
    var user_email = $("#user_email").val();
    var password = $("#password").val();

    if (user_email === "") {
        alert("请输入邮箱");
    }else if (password === "") {
        alert("请输入密码");
    }

    $.ajax({
        type: "post",
        url: "flea/user/login",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"email": user_email, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'username=' + message.data.username;

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