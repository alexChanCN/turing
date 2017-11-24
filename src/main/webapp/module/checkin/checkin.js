function checkinCtrl($scope, $http, $window, $location, $routeParams) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    $.showLoading();
    $scope.get = function () {
        $http.get('./admin/punch/view',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.data = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.get();
    
    $scope.delete=function (sid,date) {
        $.confirm("确定要修改吗？", function() {
            //点击确认后的回调函数
            $http({
                method: 'DELETE',
                headers: {token: $window.sessionStorage.openid},
                url: './admin/punch/'+sid+"/"+date
            }).success(function () {
                $.showLoading();
                $scope.get();
            }).error(function (data) {
                $.toptip(data, 4000, 'error');
            })
        },function() {

        });
    }

    $scope.add=function (sid,date) {
        $.confirm("确定要修改吗？", function() {
            //点击确认后的回调函数
            var punch={'date':date,'student':{'id':sid}};
            $http({
                method: 'POST',
                data: punch,
                headers: {token: $window.sessionStorage.openid},
                url: './admin/punch'
            }).success(function () {
                $.showLoading();
                $scope.get();
            }).error(function (data) {
                $.toptip(data, 4000, 'error');
            })
        },function() {

        });
    }
}
