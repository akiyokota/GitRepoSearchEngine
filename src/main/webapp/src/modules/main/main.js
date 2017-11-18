/**
 * Created by ayokota on 11/17/17.
 */
var app = angular.module('main', [
    'ui.router',
    'angular-loading-bar',
    'httpCaller',
    'objectFactory'
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

app.constant('mainConstant', {
    colRespositoryName: "repoName",
    colRepositoryUrl: "repoUrl",
    colOwnerName: "ownerName",
    colOwnerAvatar: "ownerAvatar",

    searchCriteriaUsers : "users",
    searchCriteriaRepos : "repositories",

    numPagePortals : 5,

    pagePortalLeftShift : "<",
    pagePortalRightShift : ">",
    pagePortalInit : "init"
});

app.controller('mainController', ['$scope', '$http', 'GitUserSearchRequest', 'httpCallerFactory', '$window', 'mainConstant'
    , function ($scope, $http, GitUserSearchRequest, httpCallerFactory, $window, mainConstant ) {

        /*** variables ***/
        $scope.userInput;
        $scope.gitRepos = [];
        $scope.constants = mainConstant;
        $scope.sortType = $scope.constants.colRespositoryName;
        $scope.sortReverse = false;
        $scope.currentPage = 0;
        $scope.orderPerPage = 5;
        $scope.numPages = 0;
        $scope.searchCriterias = [$scope.constants.searchCriteriaUsers, $scope.constants.searchCriteriaRepos];
        $scope.searchCriteria = $scope.searchCriterias[0];
        $scope.pagePortals = [];
        $scope.numPagePortals = $scope.constants.numPagePortals;
        $scope.gitUserSearchRequest = undefined;
        /*** functions ***/

        $scope.search = function(userInput) {
            if(userInput==undefined || userInput==null || userInput=="") {
                alert("Please type in an git user ID");
                return;
            }

            $scope.gitUserSearchRequest = new GitUserSearchRequest(userInput.trim(), $scope.searchCriteria,
                                                                    1, $scope.orderPerPage );
            httpCallerFactory.getUserRepoInfo($scope.gitUserSearchRequest, function(response) {
               //alert(JSON.stringify(response));
                if(response.status==200 && response.data !=null && response.data.status==="SUCCESS") {
                    var res = response.data;
                    //alert(JSON.stringify(res.gitRepos));
                    $scope.gitRepos = res.gitRepos;
                    $scope.numPages = Math.ceil(res.repoCount / $scope.orderPerPage) ;
                    $scope.currentPage = 1;
                    $scope.generatePagePortals($scope.constants.pagePortalInit);
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

        $scope.clear = function () {
            $scope.gitRepos=[];
            $scope.userInput='';
            $scope.currentPage = 0;
            $scope.numPages = 0;
        };

        $scope.getNumber = function(num) {
            return new Array(num);
        };

        $scope.generatePagePortals = function (shift) {
            var newPagePortals = [];
            if(shift===$scope.constants.pagePortalInit) {
                for (var i = 1; i <= Math.min($scope.numPages, $scope.numPagePortals); i++) {
                    newPagePortals.push(i);
                }
            } else if (shift === $scope.constants.pagePortalLeftShift) {
                for(var i = 0; i < $scope.pagePortals.length; i++) {
                    var newPageNumber = $scope.pagePortals[i] - $scope.numPagePortals;
                    newPagePortals.push(newPageNumber);
                }
                if(newPagePortals.length < $scope.numPagePortals) {
                    var numMissingPagePortals = $scope.numPagePortals - newPagePortals.length;
                    for(var i = 0; i < numMissingPagePortals; i++) {
                        newPagePortals.push(newPagePortals[newPagePortals.length-1] + 1);
                    }
                }
            } else if (shift === $scope.constants.pagePortalRightShift) {
                for(var i = 0; i < $scope.pagePortals.length; i++) {
                    var newPageNumber = $scope.pagePortals[i] + $scope.numPagePortals;
                    if(newPageNumber > $scope.numPages)
                        break;
                    newPagePortals.push(newPageNumber);
                }
            }
            $scope.pagePortals = newPagePortals;
        };

        $scope.goToPage = function (page) {
            $scope.gitUserSearchRequest.setPage(page);
            httpCallerFactory.getUserRepoInfo($scope.gitUserSearchRequest, function(response) {
                //alert(JSON.stringify(response));
                if(response.status==200 && response.data !=null && response.data.status==="SUCCESS") {
                    var res = response.data;
                    //alert(JSON.stringify(res.gitRepos));
                    $scope.gitRepos = res.gitRepos;
                    $scope.currentPage = page;
                } else {
                    if(response.data !=null) {
                        alert(response.data.msg);
                    } else {
                        alert("An error has been returned from server with status:" + response.status);
                    }
                }

            });
        };

        // styles related
        $scope.addStyleToSelectedPage = function (pageNumber) {
            if(pageNumber === $scope.currentPage)
                return "#FF0000";
            else
                return "#000000";
        };

    }]);



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

app.directive('ngEnter', function() {
    return function(scope, element, attrs) {
        element.bind("keydown keypress", function(event) {
            if(event.which === 13) {
                scope.$apply(function(){
                    scope.$eval(attrs.ngEnter, {'event': event});
                });

                event.preventDefault();
            }
        });
    };
});