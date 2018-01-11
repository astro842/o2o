/**
 * Created by astro on 2018/1/10.
 */
$(function () {
    var listUrl = "/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";

    getList();
    function getList() {

        $.getJSON(listUrl,function (data) {
            if (data.success){
               var productList = data.productList;
               var tempHtml='';
               productList.map(function (item,index) {
                   var textOp = "下架";
                   var contraryStatus = 0;
                   //0：已下架
                   if (item.enableStatus == 0){
                       textOp ="上架";
                       contraryStatus = 1;
                   }else {
                       contraryStatus = 0;
                   }
                   tempHtml += ''+'<div class="row row-product">'
                         + '<div class="col-33">'
                         + item.productName
                         + '</div>'
                         + '<div class="col-20">'
                         + item.priority
                         + '</div>'
                         + '<a class="col-40">'
                         + '<a href="#" class="edit" data-id="'
                         + item.productId
                         + '"data-status="'
                         + item.enableStatus
                         + '">编辑</a>'
                         + '<a href="#" class="status" data-id="'
                         + item.productId
                         + '"data-status"'
                         + contraryStatus
                         + '">'
                         + textOp
                         + '</a>'
                         + '<a href="#" class="preview" data-id"'
                         + item.productId
                         + '"data-status="'
                         + item.enableStatus
                         + '">预览</a>'
                         + '</div></div>';
               });
               $('.product-wrap').html(tempHtml);
            }
        });
    }

    $('.product-wrap').on('click','a',function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')){
            window.location.href='/shopadmin/productopertion?productId='+e.currentTarget.dataset.id;
        }else if(target.hasClass('status')){

        }else if(target.hasClass('preview')){
           // window.location.href='/shopadmin/productopertion?productId='+e.currentTarget.dataset.id;
        }
    });
})