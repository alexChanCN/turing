function taskListCtrl($scope, $http, $window, $location, $routeParams) {
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
        $http.get('./admin/task',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.tasks = data;
            $.hideLoading();
        }).error(function (data) {
            console.log(data);
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }
    $scope.getStudents=function () {
        $http.get('./admin/student',{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.students = data;
            $.hideLoading();
        }).error(function (data) {
            console.log(data);
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }

    $scope.getLessons=function () {
        $scope.lesson="";
        var lessons=new Array();
        $http.get('./admin/lesson',{header:{'token':$window.sessionStorage.openid}})
            .success(function (data) {
                console.log(data);
                for (var i =0;i<data.length;i++){
                    // var info={"title": data[i].name, "value": data[i].id}
                    // lessons.push(info);
                    lessons.push(data[i].name+","+data[i].id);
                }
                console.log(lessons);
                $("#lesson").select({
                        title:"选择课程",
                        items:lessons,
                        onchange:function (d) {
                            console.log(this.d);
                        },
                        onClose: function() {
                            console.log("close");
                            var result=$scope.lesson.split(",");
                            $window.sessionStorage.lesson = result[1];
                            $scope.getLessonFile(result[1]);
                        },
                        onOpen: function() {
                            console.log("open");
                        }
                    })
            })
    };

    $scope.getLessonFile=function (lessonid) {
        $http.get('./admin/file/lesson/'+lessonid,{ headers:{'token':$window.sessionStorage.openid}}).success(function (data) {
            $scope.files = data;
            $.hideLoading();
        }).error(function (data) {
            $.hideLoading();
            $.toptip(data, 4000, 'error');
        });
    }

    $scope.adminviewfile=function (filename) {
        $window.open("./file/"+filename);
        //$window.location.href='./file/'+filename;
    }
    $scope.import=function(files){
        console.log(files.length);
        for(var i=0;i<files.length;i++)
        {
            console.log(files[i].fileName);
            $window.open("./file/"+files[i].fileName);
        }
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
    $scope.getLessons();
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
        // $scope.getLessons();
    }
}

function adminfilelistCtrl($scope, $http, $window, $location, $routeParams) {
    $.showLoading();
    // $scope.getfiles=function () {
    //     //$routeParams:路由中带参数
    //     $http.get('./admin/file/newest/'+$routeParams.sid,{header:{'token':$window.sessionStorage.openid}}).success(function (data) {
    //         console.log(data);
    //         $scope.files=data.fileName;
    //         //console.log($scope.files);
    //         $.hideLoading();
    //     }).error(function (data) {
    //         $.hideLoading();
    //         $.toptip('未提交', 4000, 'error');
    //     });
    // }
    $scope.adminviewfile=function (filename) {
        // console.log(filename);
        $window.open("./file/"+filename);
       //$window.location.href='./file/'+filename;
    }
    // $scope.getfiles();
}


function selectlessonCtrl($scope, $http, $window, $location, $routeParams) {
    // $.showLoading();
    var lessons=new Array();
    $scope.getLessons=function () {
        $http.get('./admin/lesson',{header:{'token':$window.sessionStorage.openid}})
            .success(function (data) {
                // console.log(data.length);
                for (var i =0;i<data.length;i++){
                    // console.log(data[i].name);
                    lessons.push(data[i].name);
                }
            })
    };
    $scope.getLessons();
    console.log(lessons);
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