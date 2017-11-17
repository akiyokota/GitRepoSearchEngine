package server.bindings.git;

import server.util.JSONSerializer;

/**
 * Created by ayokota on 11/16/17.
 */
public class GitRepoInfo {
    private String name;
    private String html_url;
    private GitRepoOwner owner;

    public GitRepoInfo() {
        this.name = "";
        this.html_url = "";
        this.owner = new GitRepoOwner();
    }

    public GitRepoInfo(String name,
                        String html_url,
                        GitRepoOwner owner) {
        this.name = name;
        this.html_url = html_url;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public GitRepoOwner getOwner() {
        return owner;
    }

    public void setOwner(GitRepoOwner owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return JSONSerializer.serialize(this);
    }
}
