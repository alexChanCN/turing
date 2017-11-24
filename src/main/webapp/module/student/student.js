function studentListCtrl($scope, $http, $window, $location, $routeParams) {
    if($window.sessionStorage.openid==null){
        $window.sessionStorage.openid=$location.search().openid;
    }
    $.showLoading();
    $scope.get=function () {
        $http.get('./admin/student',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.students = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 2000, 'error');
        });
    }
    $scope.get();
    
    $scope.addStudent=function () {
        $.modal({
            title: "添加",
            text: "选择添加方式",
            buttons: [
                { text: "手动添加", onClick: function(){
                    window.location.href = "#/student/s";


                } },
                { text: "excel导入", onClick: function(){ window.location.href='#/student/e'} },
                { text: "取消", className: "default", onClick: function(){} },
            ]
        });
    }
    $scope.addLesson=function () {
        $.prompt({
            title: '添加',
            text: '请输入添加班级的名称',
            input: '2016年春季期',
            empty: false, // 是否允许为空
            onOK: function (input) {
                //点击确认
                $scope.lesson={'name':input};
                $http({
                    method: 'POST',
                    data: $scope.lesson,
                    headers: {token: $window.sessionStorage.openid},
                    url: './admin/lesson'
                }).success(function () {
                    $.alert("添加成功");
                    $scope.get();
                }).error(function (data) {
                    $.toptip(data, 4000, 'error');
                })
            },
            onCancel: function () {
                //点击取消
            }
        });
    }
    $scope.delete=function (sId) {
        $.actions({
            actions: [{
                text: "删除",
                className: "color-danger",
                onClick: function() {
                    //do something
                    $.confirm("确定要删除吗？", function() {
                        //点击确认后的回调函数
                        $.showLoading();
                        $http({
                            method: 'DELETE',
                            headers: {token: $window.sessionStorage.openid},
                            url: './admin/student/'+sId
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
}
function sEditCtrl($scope, $http, $window, $location, $routeParams) {

    $scope.addStudent=function () {
        $http({
            method: 'POST',
            data: $scope.student,
            headers: {token: $window.sessionStorage.openid},
            url: './admin/student'
        }).success(function () {
            $.alert("添加成功");
            window.location.href='#/student/list';
        }).error(function (data) {
            $.toptip(data, 4000, 'error');
        })
    }
}
function eEditCtrl($scope, $http, $window, $location, $routeParams,Upload) {
    $scope.upload = function upload() {
        debugger
        Upload.upload({
            url: './admin/student/excel',
            headers: {
                'token': $window.sessionStorage.token
            },
            data: {
                file:$scope.file
            }
        }).progress(function (evt) {
            //进度条
            $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));

            console.log('progess:' + $scope.progress + '%');
        }).success(function (data, status, headers, config) {
            //上传成功
            $.alert("添加成功");
            window.location.href = "#/student/list";
        }).error(function (data, status, headers, config) {
            //上传失败
            $.toptip(data, 4000, 'error');
        });


    }


}

