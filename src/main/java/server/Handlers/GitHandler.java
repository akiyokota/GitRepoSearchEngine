package server.Handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.bindings.git.GitRepoInfo;
import server.bindings.git.GitRepoSearchRequest;
import server.bindings.git.GitRepoSearchResponse;
import server.bindings.git.GitRepoSearchResult;
import server.clients.GitClient;
import server.enums.GitUserSearcherCode;
import server.exceptions.GitUserSearcherException;
import server.util.GitUserSearcherConstants;

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
              validateRequest(gitRepoSearchRequest);

            Integer repoCount = null;
            List<GitRepoInfo> repoList = null;

              switch(gitRepoSearchRequest.getSearchCriteria()) {
                  case GitUserSearcherConstants.SEARCH_CRITERIA_REPOS:
                      GitRepoSearchResult gitRepoSearchResult =
                              gitClient.getGitRepoSearchWithKeywords(gitRepoSearchRequest);
                      repoCount = gitRepoSearchResult.getTotal_count();
                      repoList = gitRepoSearchResult.getItems();
                      break;
                  case GitUserSearcherConstants.SEARCH_CRITERIA_USERS:
                      repoCount = gitClient.getRepoCountForUser(gitRepoSearchRequest.getUserInput());
                      repoList = gitClient.getGitUserRepo(gitRepoSearchRequest);
                      break;
                  default:
                      throw new GitUserSearcherException("Searching Criteria not recognized",
                              GitUserSearcherCode.INVALID_REQUEST);
              }


            if(repoList.size()<1) {
                response = new GitRepoSearchResponse(GitUserSearcherCode.EMPTY_RESULT.toString()
                        , NO_RESULT_FOUND, repoList, 0);
            } else {

                response = new GitRepoSearchResponse(GitUserSearcherCode.SUCCESS.toString(), ""
                        , repoList, repoCount);
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

    private void validateRequest (GitRepoSearchRequest gitRepoSearchRequest) {
        if(gitRepoSearchRequest==null)
            throw new GitUserSearcherException("Request cannot be empty", GitUserSearcherCode.INVALID_REQUEST);
        if(gitRepoSearchRequest.getUserInput()==null || gitRepoSearchRequest.getUserInput().trim().isEmpty())
            throw new GitUserSearcherException("User input cannot be empty", GitUserSearcherCode.INVALID_REQUEST);
        if(gitRepoSearchRequest.getSearchCriteria()==null || gitRepoSearchRequest.getSearchCriteria().trim().isEmpty())
            throw new GitUserSearcherException("Search Criteria cannot be empty", GitUserSearcherCode.INVALID_REQUEST);
        if(gitRepoSearchRequest.getPage()==null || gitRepoSearchRequest.getPage()<1)
            throw new GitUserSearcherException("Page cannot be empty", GitUserSearcherCode.INVALID_REQUEST);
        if(gitRepoSearchRequest.getPerPage()==null || gitRepoSearchRequest.getPerPage()<1)
            throw new GitUserSearcherException("Per Page cannot be empty", GitUserSearcherCode.INVALID_REQUEST);
    }


}
