/**
 * Created by ayokota on 11/18/17.
 */
var app = angular.module('objectFactory', [
]);


app.factory('GitUserSearchRequest', function () {

    function GitUserSearchRequest(userInput, searchCriteria, page, perPage, languageFilterList) {
        this.userInput = userInput;
        this.searchCriteria = searchCriteria;
        this.page = page;
        this.perPage = perPage;
        this.languageFilterList = languageFilterList;
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
        getLanguageFilterList: function () {
            return ( this.languageFilterList );
        },
        setPage : function (page) {
            this.page = page;
        }

    };

    return GitUserSearchRequest;
});