/**
 * Created by ayokota on 11/17/17.
 */
var app = angular.module('main', [
    'ui.router',
    'angular-loading-bar',
    'httpCaller'
]);

app.config(['$httpProvider', function ($httpProvider) {
    //initialize get if not there
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }

    //disable IE ajax request caching
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
}]);

app.constant('mainConsts', {
    colRespositoryName: "repoName",
    colRepositoryUrl: "repoUrl",
    colOwnerName: "ownerName",
    colOwnerAvatar: "ownerAvatar"
});

app.controller('mainController', ['$scope', '$http', 'GitUserSearchRequest', 'httpCallerFactory', '$window', 'mainConsts'
    , '$filter'
    , function ($scope, $http, GitUserSearchRequest, httpCallerFactory, $window, mainConsts, $filter) {

        /*** variables ***/

        $scope.gitRepos = [];
        $scope.constants = mainConsts;
        $scope.sortType = $scope.constants.colRespositoryName;
        $scope.sortReverse = false;
        $scope.currentPage = 0;
        $scope.orderPerPageOptions = [5, 10, 20, 50, 100];
        $scope.orderPerPage = $scope.orderPerPageOptions[0];

        /*** functions ***/

        $scope.search = function(gitUserId) {
            if(gitUserId==undefined || gitUserId==null || gitUserId=="") {
                alert("Please type in an git user ID");
                return;
            }

            // var gitUserSearchRequest = new GitUserSearchRequest(gitUserId);
            // alert("you typed in " + gitUserSearchRequest.getUserId());
            httpCallerFactory.getUserRepoInfo(gitUserId, function(response) {
               //alert(JSON.stringify(response));
                if(response.status==200 && response.data !=null && response.data.status==="SUCCESS") {
                    var res = response.data;
                    //alert(JSON.stringify(res.gitRepos));
                    $scope.gitRepos = res.gitRepos;
                } else {
                    if(response.data !=null) {
                        alert(response.data.msg);
                    } else {
                        alert("An error has been returned from server with status:" + response.status);
                    }
                }

            });
        };

        $scope.openLink = function (linkUrl) {
            if ($window.confirm("Would you like to open up "
                    + linkUrl
                    + " in a new window?")) {
                $window.open(linkUrl);
            }
        };

        $scope.changeSortFilter = function (column) {
            try {
                if ($scope.sortType == column) {
                    $scope.sortReverse = !$scope.sortReverse;
                } else {
                    $scope.sortType = column;
                    $scope.sortReverse = false;
                }
            } catch (error) {
                var message = "An error has occured in changeSortFilter: " +  error;
                alert(message);
            }
        };

        $scope.getCurrentPage = function (filterKeyword) {
            if ($scope.numberOfPages(filterKeyword) < $scope.currentPage) {
                $scope.currentPage = 0;
            }
        };

        $scope.previousPage = function () {
            $scope.currentPage = $scope.currentPage - 1;
        };

        $scope.nextPage = function () {
            $scope.currentPage = $scope.currentPage + 1;
        };

        $scope.numberOfPages = function (filterKeyword) {
            var filteredRepo = $filter('resultFilter')($scope.gitRepos, filterKeyword);
            return $scope.calcPages(filteredRepo.length, $scope.orderPerPage);
        };

        $scope.calcPages = function (totalOrders, orderPerPage) {
            var numPages = 0;

            try {
                numPages = Math.ceil(totalOrders / orderPerPage);
            } catch (error) {

            }

            return numPages;
        }
    }]);

app.factory('GitUserSearchRequest', function () {

    function GitUserSearchRequest(userId) {
        this.userId = userId;
    };

    GitUserSearchRequest.prototype = {
        getUserId : function() {
            return ( this.userId );
        }
    };

    return GitUserSearchRequest;
});

app.filter('resultFilter', [ function () {
    return function (input, filterKeyword) {
        if(input == undefined || input == null || input == ""
            || filterKeyword==undefined || filterKeyword==null || filterKeyword=="")
            return input;

        var filterKeywordLower = filterKeyword.toLowerCase();
        var result = [];

        for(var i = 0; i < input.length; i++) {
            var record = input[i];

            if(record.name.toLowerCase().indexOf(filterKeywordLower) !== -1) {
                result.push(record);
            }
            else if(record.html_url.toLowerCase().indexOf(filterKeywordLower) !== -1) {
                result.push(record);
            }
            else if(record.owner.login.toLowerCase().indexOf(filterKeywordLower) !== -1) {
                result.push(record);
            }

        }
        return result;
    };
}]);