package server.bindings.git;

import server.util.JSONSerializer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ayokota on 11/16/17.
 */
public class GitRepoSearchRequest {
    private List<String> userInput;

    public GitRepoSearchRequest() {
        this.userInput = new LinkedList<String>();
    }

    public GitRepoSearchRequest(List<String>  userInput) {
        this.userInput = userInput;
    }

    public List<String> getUserInput() {
        return userInput;
    }

    public void setUserInput(List<String> userInput) {
        this.userInput = userInput;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
