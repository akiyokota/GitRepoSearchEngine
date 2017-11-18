package server.bindings.git;

import server.bindings.base.ResponseBase;
import server.util.JSONSerializer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ayokota on 11/16/17.
 */

public class GitRepoSearchResponse extends ResponseBase {
    private List<GitRepoInfo> gitRepos;
    private Integer repoCount;

    public GitRepoSearchResponse () {
        this.setStatus("");
        this.setMsg("");
        this.gitRepos = new LinkedList<GitRepoInfo>();
        this.repoCount = 0;
    }

    public GitRepoSearchResponse (String status,
                                  String msg,
                                  List<GitRepoInfo> gitRepos,
                                  Integer repoCount) {
        this.setMsg(msg);
        this.setStatus(status);
        this.gitRepos = gitRepos;
        this.repoCount = repoCount;
    }

    public List<GitRepoInfo> getGitRepos() {
        return gitRepos;
    }

    public void setGitRepos(List<GitRepoInfo> gitRepos) {
        this.gitRepos = gitRepos;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
