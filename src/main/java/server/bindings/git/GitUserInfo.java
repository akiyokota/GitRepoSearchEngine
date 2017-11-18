package server.bindings.git;

import server.util.JSONSerializer;

/**
 * Created by ayokota on 11/17/17.
 */
public class GitUserInfo {
    private String login;
    private Integer public_repos;

    public GitUserInfo() {
        this.login="";
        this.public_repos=0;
    }

    public GitUserInfo(String login, Integer public_repos) {
        this.login = login;
        this.public_repos = public_repos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(Integer public_repos) {
        this.public_repos = public_repos;
    }

    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
