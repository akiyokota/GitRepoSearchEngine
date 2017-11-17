package server.bindings.git;

import server.util.JSONSerializer;

/**
 * Created by ayokota on 11/16/17.
 */
public class GitRepoSearchRequest {
    private String userId;

    public GitRepoSearchRequest() {
        this.userId = "";
    }

    public GitRepoSearchRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
