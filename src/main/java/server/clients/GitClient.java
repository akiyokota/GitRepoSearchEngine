package server.clients;

import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.bindings.git.GitRepoInfo;
import server.bindings.git.GitRepoSearchRequest;
import server.bindings.git.GitRepoSearchResult;
import server.bindings.git.GitUserInfo;
import server.enums.GitUserSearcherCode;
import server.exceptions.GitUserSearcherException;
import server.util.GitUserSearcherConstants;
import server.util.JSONSerializer;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by ayokota on 11/16/17.
 */
@Component
public class GitClient {
    private static final Logger LOG = Logger.getLogger(GitClient.class);

    private static final Type LIST_REPO_INFO_TYPE = new TypeToken<List<GitRepoInfo>>() {}.getType();

    @Value("${GIT_API_URL}")
    private String gitApiUrl;

    @Value("${GIT_SEARCH_REPO_API}")
    private String searchRepoApi;

    @Value("${GIT_SEARCH_USERS_API}")
    private String searchUserApi;

    @Value("${GIT_CONNECTTIMEOUT}")
    private Integer gitConnectionTimeOut;

    @Value("${GIT_READTIMEOUT}")
    private Integer gitReadTimeOut;

    private Client client;

    public WebResource getWebResource(String requestEndpoint) {
        if (client == null) {
            client = Client.create();
            client.setConnectTimeout(gitConnectionTimeOut);
            client.setReadTimeout(gitReadTimeOut);
        }
        return client.resource(requestEndpoint);
    }

    public GitRepoSearchResult getGitRepoSearchWithKeywords(GitRepoSearchRequest gitRepoSearchRequest) {
        GitRepoSearchResult response = null;

        try {
            String requestEndPoint = getGitRepoSearchWithKeywordsUrl(gitRepoSearchRequest);
            WebResource resource = getWebResource(requestEndPoint);

            String responseJson = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);

            response = JSONSerializer.deserialize(responseJson, GitRepoSearchResult.class);

        } catch (Exception e) {
            throw new GitUserSearcherException("An error has occured in getGitRepoWithKeywords method :" +
                    e.getMessage(), GitUserSearcherCode.HTTP_ERROR);
        }

        return response;
    }

    private String getGitRepoSearchWithKeywordsUrl (GitRepoSearchRequest gitRepoSearchRequest) {
        StringBuilder url = new StringBuilder();
        url.append(gitApiUrl).append(searchRepoApi)
                .append("?q=")
                .append(gitRepoSearchRequest.getUserInput().
                        replaceAll(" ", GitUserSearcherConstants.URL_WHITESPACE))
                .append("&page=").append(gitRepoSearchRequest.getPage())
                .append("&per_page=").append(gitRepoSearchRequest.getPerPage());
        return url.toString();
    }

    public List<GitRepoInfo> getGitUserRepo(GitRepoSearchRequest gitRepoSearchRequest) {
        List<GitRepoInfo> response = null;

        try {

            String requestEndPoint = buildGitSearchUserRequest(gitRepoSearchRequest);

            WebResource resource = getWebResource(requestEndPoint);

            String responseJson = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);

            response = JSONSerializer.deserialize(responseJson, LIST_REPO_INFO_TYPE);

        } catch (Exception e) {
            throw new GitUserSearcherException("An error has occured in getGitUserRepo method :" +
                    e.getMessage(), GitUserSearcherCode.HTTP_ERROR);
        }

        return response;
    }

    public String buildGitSearchUserRequest (GitRepoSearchRequest gitRepoSearchRequest) {
        StringBuilder url = new StringBuilder();
        url.append(buildRepoCountForUserUrl(gitRepoSearchRequest.getUserInput()))
                .append("/repos?page=").append(gitRepoSearchRequest.getPage())
                .append("&per_page=").append(gitRepoSearchRequest.getPerPage());
        return url.toString();
    }


    public Integer getRepoCountForUser(String userId) {
        Integer response = 0;

        try {
            String requestEndPoint = buildRepoCountForUserUrl(userId);
            WebResource resource = getWebResource(requestEndPoint);

            String responseJson = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);

            GitUserInfo gitUserInfo = JSONSerializer.deserialize(responseJson, GitUserInfo.class);
            if(gitUserInfo!=null ) {
                response = gitUserInfo.getPublic_repos();
            }
        } catch (Exception e) {
            throw new GitUserSearcherException("An error has occured in getRepoCountForUser method :" +
                    e.getMessage(), GitUserSearcherCode.HTTP_ERROR);
        }

        return response;
    }

    private String buildRepoCountForUserUrl ( String userId) {
        StringBuilder url = new StringBuilder();
        url.append(gitApiUrl).append(searchUserApi).append("/").append(userId);
        return url.toString();
    }
}
