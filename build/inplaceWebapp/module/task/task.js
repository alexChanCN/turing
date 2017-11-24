function taskListCtrl($scope, $http, $window, $location, $routeParams) {
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
        $http.get('./admin/task',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.tasks = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.getStudents=function () {
        $http.get('./admin/student',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.students = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.delete=function (tId) {
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
                            url: './admin/task/'+tId
                        }).success(function () {
                            $scope.get();
                        }).error(function (data) {
                            $.toptip(data, 4000, 'error');
                        })
                    },function() {

                    });
                }
            }]
        });
    }
    $scope.add = function () {
        $.confirm("确定要提交吗？", function() {
            //点击确认后的回调函数
            $.showLoading();
            $http({
                method: 'POST',
                data: $scope.task,
                headers: {token: $window.sessionStorage.openid},
                url: './admin/task'
            }).success(function () {
                $.hideLoading();
                $scope.get();
            }).error(function (data) {
                $.hideLoading();
                $.toptip(data, 2000, 'error');
            })
        },function() {

        });


    }
    $scope.get();
    $scope.getStudents();
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

function answerListCtrl($scope, $http, $window, $location, $routeParams) {
    $.showLoading();
    $scope.get=function () {
        $http.get('./admin/answer?sid='+$routeParams.sid,{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.answers = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.get();
}