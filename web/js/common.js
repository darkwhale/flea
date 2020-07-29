
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

//
// window.alert = function (str) {
//     var shield = document.createElement("DIV");
//     p = document.createElement("p");
//     shield.appendChild(p);
//
//     p.textContent = str;
// };
