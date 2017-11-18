/**
 * Created by ayokota on 11/18/17.
 */
var app = angular.module('objectFactory', [
]);


app.factory('GitUserSearchRequest', function () {

    function GitUserSearchRequest(userInput, searchCriteria, page, perPage) {
        this.userInput = userInput;
        this.searchCriteria = searchCriteria;
        this.page = page;
        this.perPage = perPage;
    };

    GitUserSearchRequest.prototype = {
        getUserInput : function() {
            return ( this.userInput );
        },
        getSearchCriteria : function() {
            return (this.searchCriteria );
        },
        getPage : function () {
            return ( this.page );
        },
        getperPage : function () {
            return ( this.perPage );
        },
        setPage : function (page) {
            this.page = page;
        }

    };

    return GitUserSearchRequest;
});