function quizListCtrl($scope, $http, $window, $location, $routeParams) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    $.showLoading();
    $http.get('./admin/student/quiz',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
        $scope.students = data;
        $.hideLoading();
    }).error(function (data) {
        $.hideLoading();
        $.toptip(data, 2000, 'error');
    });
}

function quizEditCtrl($scope, $http, $window, $location, $routeParams) {
    $.showLoading();
    $scope.get = function () {
        $http.get('./admin/quiz/student/' + $routeParams.id,{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.quizs = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 2000, 'error');
        });
    }
    $scope.get();
    $scope.delete=function (quizId) {
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
                            url: './admin/quiz/'+quizId
                        }).success(function () {
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

    $scope.add = function () {
        $.confirm("确定要提交吗？", function() {
            //点击确认后的回调函数
            var sid = $routeParams.id;
            var score=$scope.quiz.score;
            $scope.quiz = {'student': {'id': sid},'score':score};
            $.showLoading();
            $http({
                method: 'POST',
                data: $scope.quiz,
                headers: {token: $window.sessionStorage.openid},
                url: './admin/quiz'
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
}