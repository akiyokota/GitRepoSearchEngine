package Server.Service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by ayokota on 11/16/17.
 */
@Component
@Path("/service")
public class GitUserSearchService {
    private static final Logger LOG = Logger.getLogger(GitUserSearchService.class);
    private static final String PING_STATUS = "Ping from Git User Search Service!";
    private static final String HEALTH_CHECK = "OK";

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


}


