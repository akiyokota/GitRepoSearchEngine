package server.clients;

import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.bindings.git.GitRepoInfo;
import server.enums.GitUserSearcherCode;
import server.exceptions.GitUserSearcherException;
import server.util.GitUserSearcherConstants;
import server.util.JSONSerializer;

import javax.ws.rs.core.HttpHeaders;
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

    public List<GitRepoInfo> getGitUserRepo(String userId) {
        List<GitRepoInfo> response = null;

        try {
            String requestEndPoint = buildGitRepoRequest(userId);
            WebResource resource = getWebResource(requestEndPoint);

            String responseJson = resource.accept(MediaType.APPLICATION_JSON_TYPE).
                    header(HttpHeaders.USER_AGENT, "FastPromise").
                    get(String.class);

            response = JSONSerializer.deserialize(responseJson, LIST_REPO_INFO_TYPE);
        } catch (Exception e) {
            throw new GitUserSearcherException("An error has occured in getGitUserRepo method :" +
                    e.getMessage(), GitUserSearcherCode.HTTP_ERROR);
        }

        return response;
    }

    private String buildGitRepoRequest (String userId) {
        if(userId==null || userId.isEmpty()) {
            throw new GitUserSearcherException("Input userId is invalid", GitUserSearcherCode.INVALID_REQUEST);
        }
        return gitApiUrl.replace(GitUserSearcherConstants.USER_ID_PLACEHOLDER, userId);
    }

}
