package server.bindings.git;

import server.util.JSONSerializer;

/**
 * Created by ayokota on 11/16/17.
 */
public class GitRepoSearchRequest {
    private String userInput;
    private String searchCriteria;
    private Integer page;
    private Integer perPage;

    public GitRepoSearchRequest() {
        this.userInput = "";
        this.searchCriteria = "";
        this.page = 0;
        this.perPage = 0;
    }

    public GitRepoSearchRequest(String  userInput,
                                String searchCriteria,
                                Integer page,
                                Integer perPage) {
        this.userInput = userInput;
        this.searchCriteria = searchCriteria;
        this.page = page;
        this.perPage = perPage;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
