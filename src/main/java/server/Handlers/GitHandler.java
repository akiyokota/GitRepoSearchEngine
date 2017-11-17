package server.Handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.bindings.git.GitRepoInfo;
import server.bindings.git.GitRepoSearchRequest;
import server.bindings.git.GitRepoSearchResponse;
import server.clients.GitClient;
import server.enums.GitUserSearcherCode;
import server.exceptions.GitUserSearcherException;

import java.util.List;

/**
 * Created by ayokota on 11/16/17.
 */
@Component
public class GitHandler {
    private static final Logger LOG = Logger.getLogger(GitHandler.class);

    @Autowired
    private GitClient gitClient;

    public GitRepoSearchResponse getGitRepos (GitRepoSearchRequest gitRepoSearchRequest) {
        GitRepoSearchResponse response = null;

        try {
            List<GitRepoInfo> gitRepos = gitClient.getGitUserRepo(gitRepoSearchRequest.getUserId());
            response = new GitRepoSearchResponse(GitUserSearcherCode.SUCCESS.toString(), "", gitRepos);

        } catch (GitUserSearcherException gse) {
            throw new GitUserSearcherException(gse.getMessage(), gse.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GitUserSearcherException("An error has occured in getGitRepos method :" +
                    e.getMessage(), GitUserSearcherCode.GENERAL_ERROR);
        }


        return response;
    }

}
