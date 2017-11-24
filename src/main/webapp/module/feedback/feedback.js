function feedbackListCtrl($scope, $http, $window, $location, $routeParams) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    $.showLoading();

    $scope.get=function () {
        $http.get('./admin/feedback',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.feedbacks = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 2000, 'error');
        });
    }

    $scope.get();
    $scope.delete=function (fId) {
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
                            headers: {token: $window.sessionStorage.openid},
                            url: './admin/feedback/'+fId
                        }).success(function () {
                            $.showLoading();
                            $scope.get();
                        }).error(function (data) {
                            $.toptip(data, 2000, 'error');
                        })
                    },function() {

                    });
                }
            }]
        });
    }
}

function feedbackEditCtrl($scope, $http, $window, $location, $routeParams) {


    $scope.add = function () {
        $.confirm("确定要提交吗？", function() {
            //点击确认后的回调函数
            $.showLoading();
            $http({
                method: 'POST',
                data: $scope.feedback,
                headers: {token: $window.sessionStorage.openid},
                url: './restapi/feedback'
            }).success(function () {
                $.hideLoading();
                $.alert("相关人员会尽快处理", "成功！");
            }).error(function (data) {
                $.hideLoading();
                $.toptip(data, 2000, 'error');
            })
        },function() {

        });


    }
}