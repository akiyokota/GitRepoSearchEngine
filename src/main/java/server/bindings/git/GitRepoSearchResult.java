package server.bindings.git;

import server.util.JSONSerializer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ayokota on 11/17/17.
 */
public class GitRepoSearchResult {
    private Integer total_count;
    private List<GitRepoInfo> items;

    public GitRepoSearchResult () {
        this.total_count = 0;
        this.items = new LinkedList<GitRepoInfo>();
    }

    public GitRepoSearchResult (Integer total_count, List<GitRepoInfo> items) {
        this.total_count = total_count;
        this.items = items;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public List<GitRepoInfo> getItems() {
        return items;
    }

    public void setItems(List<GitRepoInfo> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
