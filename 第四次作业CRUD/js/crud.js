
var pageCount="";
var sort="userName";
var sortOrder="asc";
$(document).ready(function () {
    var pn=$("#pageNumber").text();
    $("#btSearch").click(function (e) { 
        var obj={userName:$("#userName").val(),chrName:$("#chrName").val(),email:$("#email").val(),province:$("#provinceName").val()};
        var queryParams=JSON.stringify(obj);
        var obj2={pageSize:$("#pageSize").val(),pageNumber:pn,sort:sort,sortOrder:sortOrder};
        var pageParams=JSON.stringify(obj2);
        $.ajax({
            type: "post",
            url: "queryController.do",
         //   data: {queryParams:queryParams,pageParams:pageParams},
            data:{queryParams:queryParams,pageParams:pageParams},
            dataType: "json",
            success: function (response) {
                var rows=response.rows;
                total=response.total;
                var pageSize=$("#pageSize").val();
                pageCount=Math.ceil(total/pageSize);
                $("#total").text(total);
                $("#pageCount").text(pageCount);
                $("tbody").empty();
                $.each(rows,function(index,row){
                    var s=JSON.stringify(row);
                    var str="<tr data='"+s+"'>";
                    str=str+'<td><input name="item" type="checkbox" value='+row.userName+'/></td>';
                    str=str+'<td>'+row.userName+'</td>';
                    str=str+'<td>'+row.chrName+'</td>';
                    str=str+'<td>'+row.email+'</td>';
                    str=str+'<td>'+row.province+'</td>';
                    str=str+'<td>'+row.city+'</td>';
                    str=str+'<td><a href="#" id="btnDel" value='+row.userName+'>删除</a>';
                    str=str+'<a href="#" id="btnUpdate">修改</a></td>';
                    str=str+'</tr>';
                    $("tbody").append(str);
                });
            }
        });

    });
    $("#next").click(function (e) {
        pn++;
        if(pn>pageCount){
            alert("已经是最后一页");
            pn--;
        }else{
            $("#btSearch").click();
            document.getElementById("pageNumber").innerText=pn;
            
        }
        
    });
    $("#back").click(function (e) {
        pn--;
        if(pn<1){
            alert("已经是第一页");
            pn++;
        }else{
            $("#btSearch").click();
            document.getElementById("pageNumber").innerText=pn;
        }
         
        
    });
    $("#first").click(function (e) {
        pn=1;
        $("#btSearch").click();
        document.getElementById("pageNumber").innerText=pn;
    });
    $("#last").click(function (e) {
     //   alert("pageCount="+pageCount);
        pn=pageCount;
        $("#btSearch").click();
        document.getElementById("pageNumber").innerText=pn;
    });
    var flag=true;
    $("#sortByProvinceName").click(function (e) {
        alert("按省份排序");
        sort="province";
        if(flag){
            sortOrder="asc";
            flag=false;
        }else{
            sortOrder="desc";
            flag=true;
        }
        $("#btSearch").click();
    });
    $("#sortByUserName").click(function (e) {
        alert("按用户名排序");
        sort="userName";
        $("#btSearch").click();
        if(flag){
            sortOrder="asc";
            flag=false;
        }else{
            sortOrder="desc";
            flag=true;
        }
        $("#btSearch").click();
    });
    $("#ckAll").click(function (e) { 
        var items=document.getElementsByName("item");
        if(document.getElementById("ckAll").checked==true){
            for(var i=0;i<items.length;i++){
                items[i].checked=true;
            }
        }else{
            for(var i=0;i<items.length;i++){
                items[i].checked=false;
            }
        }
        
    });
    //每行的删除按钮
    $("table").on('click','#btnDel',function(){
        alert("点击删除");
        var userName=$(this).attr("value");
        $.ajax({
            type: "post",
            url: "deleteUser.do",
            data: {ids:userName},
            dataType: "json",
            success: function (response) {
                alert(response.info);
                if(response.code==0){
                    location.reload();
                }
            }
        });
    })
    //工具栏的删除按钮
    $("#btDelete").click(function (e) { 
        var len=$('tbody tr input:checkbox:checked').length;
        if(len==0){
            alert("至少需要选择一项！");
            return;
        }
        var vals=[];//定义数组
        $('tbody tr input:checkbox:checked').each(function(index,item){
            vals.push($(this).val());//循环将选择复选框的value值加入数组中
        });

        $.ajax({
            type: "post",
            url: "deleteUser.do",
            data: {ids:vals.join("")},
            dataType: "json",
            success: function (response) {
                alert(response.info);
                if(response.code==0){
                    location.reload();
                }
            }
        });
    });
    //增加按钮
    $("#btAdd").click(function (e) { 
        $("#action").val("insert");
        ShowDiv("MyDiv","fade");
    });
$("#btUpdate").click(function (e) {
    $("#action").val("update");
    ShowDiv("MyDiv","fade");
    
});
//修改按钮
  $("table").on('click','#btnUpdate',function(){
    $("#action").val("update");
      var us=$(this).parent().parent().find("td:eq(1)").text();
    var chr=$(this).parent().parent().find("td:eq(2)").text();
    var em=$(this).parent().parent().find("td:eq(3)").text();
      $("#userName2").val(us);
      $("#name").val(chr);
      $("#email2").val(em);
    ShowDiv("MyDiv","fade");
  });


});
    //弹出隐藏层
    function ShowDiv(show_div,bg_div) {
        document.getElementById(show_div).style.display="block";
        document.getElementById(bg_div).style.display="block";
        //弹出层居中
        var windowHeight=$(window).height();//获取当前窗口高度
        var windowWidth=$(window).width();//获取当前窗口宽度
        var popupHeight=$("#"+show_div).height();//获取弹出层高度
        var popupWeight=$("#"+show_div).width();//获取弹出层宽度
        var posiTop=(windowHeight-popupHeight)/2;
        var posiLeft=(windowWidth-popupWeight)/2;
        $("#"+show_div).css({"left":posiLeft+"px","top":posiTop+"px","display":"block"});//设置position
      }
      //关闭弹出层
      function CloseDiv(show_div,bg_div) {
          document.getElementById(show_div).style.display="none";
          document.getElementById(bg_div).style.display="none";
        }