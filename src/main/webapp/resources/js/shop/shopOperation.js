/**
 * Created by astro on 2017/12/28.
 */
$(function () {
    var shopId = getQueryString("shopId");
    var isEdit = shopId ? true : false;
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    //alert(initUrl);
    var shopInfoUrl = '/shopadmin/getshopbyid?shopId='+shopId;
    var editShopUrl = '/shopadmin/modifyshop';

    if (!isEdit){
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl,function (data) {
           if (data.success) {
               var shop = data.shop;
               $('#shopName').val(shop.shopName);
               $('#shopAddr').val(shop.shopAddr);
               $('#shopDesc').val(shop.shopDesc);
               $('#shopPhone').val(shop.phone);
               var shopCategory = '<option data-id = "' + shop.shopCategory.shopCategoryId + '" selected>'
                   + shop.shopCategory.shopCategoryName + '</option>';
               var tempArea = '';
               data.areaList.map(function (item, index) {
                   tempArea += '<option data-id="' + item.areaId + '">'
                       + item.areaName + '</option>';
               });
               $('#shopCategory').html(shopCategory);
               $('#shopCategory').attr('disabled', 'disabled');
               $('#areaCategory').html(tempArea);
               $('#areaCategory').attr('dao-id', shop.areaId);
           }
        });

    }


    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                        + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                //alert(tempHtml);
                $('#shopCategory').html(tempHtml);
                $('#areaCategory').html(tempAreaHtml);
            }
        });

    }
        $('#submit').click(function () {

            var shop = {};
            if (isEdit){
                shop.shopId=shopId;
            }
            shop.shopName = $('#shopName').val();
            shop.shopAddr = $('#shopAddr').val();
            shop.shopDesc = $('#shopDesc').val();
            shop.phone = $('#shopPhone').val();
            shop.shopCategory={
                shopCategoryId : $('#shopCategory').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area ={
                areaId : $('#areaCategory').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shopImg')[0].files[0];
            var formData = new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));

            $.ajax({
                url : (isEdit ? editShopUrl : registerShopUrl),
                type : 'POST',
                data : formData,
                contentType : false,
                processData :false,
                cache : false,
                success :function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                    }else {
                        $.toast('提交失败！');
                    }
                }

            });

        });

})