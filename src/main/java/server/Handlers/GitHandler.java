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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ayokota on 11/16/17.
 */
@Component
public class GitHandler {
    private static final Logger LOG = Logger.getLogger(GitHandler.class);
    private static final String NO_RESULT_FOUND = "No result is found with your search keyword(s)";

    @Autowired
    private GitClient gitClient;

    public GitRepoSearchResponse getGitRepos (GitRepoSearchRequest gitRepoSearchRequest) {
        GitRepoSearchResponse response = null;

        try {
            List<GitRepoInfo> gitRepos = new LinkedList<GitRepoInfo>();
            for(String userId : gitRepoSearchRequest.getUserInput()) {
                gitRepos.addAll(gitClient.getGitUserRepo(userId));
            }

            if(gitRepos.size()<1) {
                response = new GitRepoSearchResponse(GitUserSearcherCode.EMPTY_RESULT.toString()
                        , NO_RESULT_FOUND, gitRepos);
            } else {

                response = new GitRepoSearchResponse(GitUserSearcherCode.SUCCESS.toString(), "", gitRepos);
            }

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
