function studentTaskCtrl($scope, $http, $window, $location, $routeParams,$route) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    var active='weui-bar__item--on';
    $scope.showTab1=1;
    $scope.showTab2=0;
    $scope.tab1Active=active;
    $scope.tab2Active='';
    $.showLoading();
    $scope.get=function () {
        $http.get('./restapi/task/undone?openid='+ $window.sessionStorage.openid).success(function (data) {
            $scope.tasks = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.getAnswers=function () {
        $http.get('./restapi/answer/student?openid='+ $window.sessionStorage.openid).success(function (data) {
            $scope.answers = data;
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

    $scope.get();
    $scope.getAnswers();
    $scope.onShowTab1=function () {
        $scope.showTab1=1;
        $scope.showTab2=0;
        $scope.tab1Active=active;
        $scope.tab2Active='';
    }
    $scope.onShowTab2=function () {
        $scope.showTab1=0;
        $scope.showTab2=1;
        $scope.tab1Active="";
        $scope.tab2Active=active;
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