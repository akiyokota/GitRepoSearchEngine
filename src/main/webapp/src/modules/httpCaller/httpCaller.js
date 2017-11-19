/**
 * Created by ayokota on 11/17/17.
 */
var app = angular.module('httpCaller', [
    'angular-loading-bar',
]);


app.factory('httpCallerFactory', function ($http) {
    return {
        /**
         * This function calls gitUserRepoInfo API via HTTP POST request
         *
         * @param gitUserSearchRequest : request parameters for gitUserRepoInfo API
         *
         * @return callback : return back to caller with the callback information
         */
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
                    "perPage": gitUserSearchRequest.perPage,
                    "languageFilterList": gitUserSearchRequest.languageFilterList
                }
            };

            $http(req).then(function (response) {
                callback(response);
            });
        }

    }

});