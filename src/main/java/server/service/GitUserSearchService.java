package server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.Handlers.GitHandler;
import server.bindings.git.GitRepoSearchRequest;
import server.bindings.git.GitRepoSearchResponse;
import server.enums.GitUserSearcherCode;
import server.exceptions.GitUserSearcherException;
import server.util.JSONSerializer;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by ayokota on 11/16/17.
 */
@Component
@Path("/service")
public class GitUserSearchService {
    private static final Logger LOG = Logger.getLogger(GitUserSearchService.class);
    private static final String PING_STATUS = "Ping from Git User Search Service!";
    private static final String HEALTH_CHECK = "OK";

    @Autowired
    private GitHandler gitHandler;

    @PostConstruct
    public void initStatements() {
        LOG.error("Initializing");
    }

    @GET
    @Path("/ping")
    public String ping() {
        LOG.error(PING_STATUS);
        return PING_STATUS;
    }

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return HEALTH_CHECK;
    }

    @POST
    @Path("/getUserRepoInfo")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String getGitUserRepoInfo (String request) {
        GitRepoSearchResponse response = null;

        try {
            GitRepoSearchRequest gitRepoSearchRequest = JSONSerializer.deserialize(request, GitRepoSearchRequest.class);
            response = gitHandler.getGitRepos(gitRepoSearchRequest);

        } catch (GitUserSearcherException gse) {
            LOG.error("An Exception has occured in with error: " + gse.getMessage());
            response = new GitRepoSearchResponse(gse.getErrorCode().toString(), gse.getMessage()
                    , null, 0);
        } catch (Exception e) {
            LOG.error("An Exception has occured in getUserRepoInfo API with error: " + e.getMessage());
            response = new GitRepoSearchResponse(GitUserSearcherCode.GENERAL_ERROR.toString()
                    , e.getMessage(), null, 0);
        }

        return JSONSerializer.serialize(response);
    }

}


