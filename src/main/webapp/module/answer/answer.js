function studentTaskCtrl($scope, $http, $window, $location, $routeParams,$route) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    var active='weui-bar__item--on';
    $scope.showTab1=1;
    $scope.showTab2=0;
    $scope.showTab3=0;
    $scope.tab1Active=active;
    $scope.tab2Active='';
    $scope.tab3Active='';
    $.showLoading();
    $scope.get=function () {
        $http.get('./restapi/task/undone?openid='+ $window.sessionStorage.openid).success(function (data) {
            console.log(data);
            $scope.tasks = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.getAnswers=function () {
        $http.get('./restapi/answer/student?openid='+ $window.sessionStorage.openid).success(function (data) {
            console.log(data);
            $scope.answers = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.getfiles=function () {
        $http.get('./restapi/file/list',{headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            console.log(data);
            $scope.files=data.fileName;
            console.log($scope.files);
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }

    $scope.delete=function (aId) {
        $.actions({
            actions: [{
                text: "删除",
                className: "color-danger",
                onClick: function() {
                    //do something
                    $.confirm("确定要删除吗？", function() {
                        //点击确认后的回调函数
                        $http({
                            method: 'DELETE',
                            headers: {token:  $window.sessionStorage.openid},
                            url: './restapi/answer/'+aId
                        }).success(function () {
                            $route.reload();
                        }).error(function (data) {
                            $.toptip(data, 4000, 'error');
                        })
                    },function() {

                    });
                }
            }]
        });
    }

    $scope.viewfile=function (filename) {
        console.log(filename);
        $window.location.href='./file/'+filename;
    }

    $scope.get();
    $scope.getAnswers();
    $scope.getfiles();
    $scope.onShowTab1=function () {
        $scope.showTab1=1;
        $scope.showTab2=0;
        $scope.showTab3=0;
        $scope.tab1Active=active;
        $scope.tab2Active='';
        $scope.tab3Active='';
    }
    $scope.onShowTab2=function () {
        $scope.showTab1=0;
        $scope.showTab2=1;
        $scope.showTab3=0;
        $scope.tab1Active="";
        $scope.tab2Active=active;
        $scope.tab3Active='';
    }
    $scope.onShowTab3=function () {
        $scope.showTab1=0;
        $scope.showTab2=0;
        $scope.showTab3=1;
        $scope.tab1Active="";
        $scope.tab2Active='';
        $scope.tab3Active=active;
    }
}
function answerEditCtrl($scope, $http, $window, $location, $routeParams,$route) {
    $scope.add = function () {
        $.confirm("确定要提交吗？", function() {
            //点击确认后的回调函数
            $.showLoading();
            $scope.answer={'task':{'id':$routeParams.tid},'content':$scope.content};
            debugger
            $http({
                method: 'POST',
                data: $scope.answer,
                headers: {token:  $window.sessionStorage.openid},
                url: './restapi/answer'
            }).success(function () {
                $.hideLoading();
                window.location.href="#/answer";
            }).error(function (data) {
                $.hideLoading();
                $.toptip(data, 2000, 'error');
            })
        },function() {
        });
    }
}

function fileupCtrl($scope,$http,$window,$location,$routeParams,$route){
    //console.log('1');
$scope.fileup=function () {
    var formData = new FormData();
    console.log( $('#file')[0].files[0]);
    formData.append('file', $('#file')[0].files[0]);
    $.ajax({
        url: './restapi/file/upload',
        type: 'POST',
        headers: {token:  $window.sessionStorage.openid},
        cache: false,
        data: formData,
        processData: false,
        contentType: false
    }).done(function(res) {
        $.toptip('提交成功！', 4000, '');
        window.location.href="#/answer";
        // console.log(res);
    }).fail(function(res) {
        $.toptip('提交失败', 2000, 'error');
        // window.location.href="#/answer";
    })
}



    //         $gallery = $("#gallery"),
    //         $galleryImg = $("#galleryImg"),
    //         $uploaderInput = $("#uploaderInput"),
    //         $uploaderFiles = $("#uploaderFiles"),
    //             // $scope.readBlobAsDataURL=function(blob, callback) {
    //             //     var a = new FileReader();
    //             //     a.onload = function(e) {callback(e.target.result);};
    //             //     a.readAsDataURL(blob);
    //             // },
    // $uploaderInput.on("change",function(e){
    //             var src, url = window.URL || window.webkitURL || window.mozURL, files = e.target.files;
    //             for (var i = 0, len = files.length; i < len; ++i) {
    //                 var file = files[i];
    //                 console.log(file);
    //                 if (url) {
    //                     // src = url.createObjectURL(file);//转成blob
    //                     var formData = new FormData();
    //                     formData.append('userfile',file);
    //                 } else {
    //                     src = e.target.result;
    //                 }
    //                 // console.log(src);
    //                 $uploaderFiles.append('<li class="weui-uploader__file">'+file.name+'</li>');
    //                 $http({
    //                     method:'POST',
    //                     data:formData,
    //                     header:{
    //                          token:$window.sessionStorage.openid},
    //                     url:'./restapi/file/upload',
    //                     processData: false,
    //                 }).success(function () {
    //                     $.hideLoading();
    //                     //window.location.href="#/answer";
    //                 }).error(function (data) {
    //                     $.hideLoading();
    //                     $.toptip(data, 2000, 'error');
    //                 })
    //             }
    //         });
    //     var index;
    //     $uploaderFiles.on("click", "li", function(){
    //         index = $(this).index();
    //         $galleryImg.attr("style", this.getAttribute("style"));
    //         $gallery.fadeIn(100);
    //     });
    //     $gallery.on("click", function(){
    //         $gallery.fadeOut(100);
    //     });
    //     $(".weui-gallery__del").click(function() {
    //         $uploaderFiles.find("li").eq(index).remove();
    //     });

}