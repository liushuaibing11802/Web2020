var userName_correct = false;
var password_correct = false;
var province_correct = false;
var email_correct = false;
var passwordEnd_correct = false;
var city_correct = false;
var name_correct = false;

function fillProvince() {
    $.ajax({
        type: "post",
        url: "queryProvinceCity.do",
        data: {},
        dataType: "json",
        success: function (response) {
            var provinceElement = document.getElementById("province");
            //娓呴櫎select鐨勬墍鏈塷ption
            provinceElement.options.length = 0;
            //澧炲姞涓�釜閫夐」
            provinceElement.add(new Option("请选择省份", ""));
            //寰幆澧炲姞鍏朵粬鎵�湁閫夐」
            for (index = 0; index < response.length; index++) {
                provinceElement.add(new Option(response[index].provinceName, response[index].provinceCode));
            }
        }
    });

}
function jqAjaxRegister(){
    if(!userName_correct||!password_correct||!province_correct||!email_correct||!city_correct||!passwordEnd_correct){
        $("#userName2").blur();
        $("#password").blur();
        $("#province").blur();
        $("#email2").blur();
        $("#city").blur();
        return;
    }
    var data={userName:$("#userName2").val(),password:$("#password").val(),chrName:$("#name").val(),
    province:$("#province option:selected").text(),city:$("#city option:selected").text(),
    email:$("#email2").val(),title:$("#action").val()};
    $.ajax({
        type: "post",
        url: "updateUser.do",
        data: data,
        dataType: "json",
        success: function (response) {
            if(response.code==0){
                alert("执行成功");
            }else{
                alert("操作失败");
                $("#checkError").text(response.info);
            }
        }
    });
}

$(document).ready(function () {
    fillProvince();

    $("#province").change(function (e) {
        $("#city").empty();
        $("#city").append($("<option>").val("").text("请选择城市"));
        if ($(this).val() == "") {
            $("#provinceError").text("必须选择省份！");
            return;
        }else{
            $("#provinceError").text("");
        }
        province_correct = true;
        $("provinceError").text("");

        var provinceCode = $("#province").val();
        $.ajax({
            type: "post",
            url: "queryProvinceCity.do",
            data: { provinceCode: provinceCode },
            dataType: "json",
            success: function (response) {
                for (index = 0; index < response.length; index++) {
                    var option = $("<option>").val(response[index].cityCode).text(response[index].cityName);
                    $("#city").append(option);
                }
            }
        });
    });

    $("#userName2").blur(function () {
        var t = $(this).val();
        var rule = /^[A-Za-z][a-zA-Z0-9]{3,14}$/;
        var result = rule.test(t.trim());
        if (t == "" || t == null) {
            $("#userNameError").text("用户名不能为空");
        } else if (!result) {
            $("#userNameError").text("用户名格式不正确");
        }
        else if (t != null && result) {
            $.ajax({
                type: "post",
                url: "ajaxUserCheck.do",
                data: { username: t },
                dataType: "json",
                success: function (response) {
                    if (response.code == 1) {
                        $("#userNameError").text(response.info);
                    } else {
                        $("#userNameError").text("okokokokok");
                        userName_correct=true;
                    }
                }
            });

        } else {
            $("#userNameError").text("");
        }

    });
    $("#name").blur(function (e) {
        var t = $(this).val();
        var rule = /^[\u4e00-\u9fa5]{2,4}$/;
        var result = rule.test(t.trim());
        if (t == "" || t == null) {
            $("#nameError").text("真实姓名不能为空");
        } else if (!result) {
            $("#nameError").text("姓名格式不正确");
        } else {
            $("#nameError").text("");
            name_correct=true;
        }

    });
    $("#email2").blur(function (e) {
        var t = $(this).val();
        var rule = /^[A-z0-9]+@[a-z0-9]+.com$/;
        var result = rule.test(t.trim());
        if (t == "" || t == null) {
            $("#emailError").text("邮箱不能为空");
        } else if (!result) {
            $("#emailError").text("邮箱格式不正确");
        } else {
            $("#emailError").text("");
            email_correct=true;
        }

    });
    $("#city").blur(function (e) {
        var t = $(this).val();
        if (t == "" || t == null) {
            $("#cityError").text("必须选择城市");
        }
        else {
            $("#cityError").text("");
            city_correct=true;
        }

    });
    $("#password").blur(function (e) {
        var t = $(this).val();
        var rule = /^\d{4,}$/;
        var result = rule.test(t.trim());
        if (t == "" || t == null) {
            $("#passwordError").text("密码不能为空");
        } else if (!result) {
            $("#passwordError").text("密码格式不正确");
        } else {
            $("#passwordError").text("");
            password_correct=true;
        }

    });
    $("#passwordEnd").blur(function (e) {
        var t = $(this).val();
        var T=$("#password").val();
        if (t == "" || t == null) {
            $("#passwordEndError").text("确认密码不能为空");
        } else if (t!=T) {
            $("#passwordEndError").text("两次密码不一样");
        } else {
            $("#passwordEndError").text("");
            passwordEnd_correct=true;
        }

    });
});