/**
 * Created by ayokota on 11/17/17.
 */
var app = angular.module('httpCaller', [
    'angular-loading-bar',
]);


app.factory('httpCallerFactory', function ($http) {
    return {
        getUserRepoInfo: function (gitUserSearchRequest, callback) {
            var req = {
                method: 'POST',
                url: 'rest/service/getUserRepoInfo',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                data: {
                    "userInput": gitUserSearchRequest.userInput,
                    "searchCriteria" : gitUserSearchRequest.searchCriteria,
                    "page" : gitUserSearchRequest.page,
                    "perPage": gitUserSearchRequest.perPage
                }
            };

            $http(req).then(function (response) {
                callback(response);
            });
        }

    }

});