package org.liquibase.ext.persistence;

import io.titandata.client.ApiClient;
import io.titandata.client.ApiException;
import io.titandata.client.RepositoriesApi;
import liquibase.Scope;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandResultsBuilder;
import org.liquibase.ext.persistence.titan.*;
import org.liquibase.ext.persistence.utils.CommandExecutor;
import org.openapitools.client.model.Repository;
import org.openapitools.client.model.RepositoryStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TitanBase extends liquibase.command.AbstractCommandStep {

    protected final CommandExecutor CE = new CommandExecutor(Scope.getCurrentScope().getUI());
    protected static final ApiClient TitanClient = new ApiClient();
    private static final TitanRepo TitanRepo;
    private static final TitanLoader loader = TitanLoader.load();

    static {
        TitanClient.setBasePath("http://" + loader.Host + ":" + loader.Port);
        TitanRepo = new TitanRepo(TitanClient);
    }

    protected List<String> BuildArgs(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    protected List<String> CreateTitanArg(CommandResultsBuilder builder, CommandArgumentDefinition<String> key, String titanArg) {
        List<String> r = new ArrayList<>();
        String val = builder.getCommandScope().getArgumentValue(key);
        if (val != null) {
            if (val.contains(",")) {
                // Comma delineated
                for (String s : val.split(",", 0)) {
                    r.add(titanArg);
                    r.add(s);
                }
            } else {
                r.add(titanArg);
                r.add(val);
            }
        }
        return r;
    }

    protected String GetLatestCommit(String repo) throws ApiException {
        RepositoriesApi repoApi = new RepositoriesApi(TitanClient);
        RepositoryStatus repoStatus = repoApi.getRepositoryStatus(repo);
        return repoStatus.getLastCommit();
    }

    protected Repository GetRepoInfo(String repoName) throws ApiException {
        return TitanRepo.GetRepoInfo(repoName);
    }

    protected String GetConnectionUrl(String targetDB) {
        return "";
    }

    protected List<TitanVolume> GetVolumes(Repository repo) {
        return TitanRepo.GetVolumes(repo);
    }

    protected List<TitanPort> GetPorts(Repository repo) {
        return TitanRepo.GetPorts(repo);
    }

    protected TitanImage GetImage(Repository repo) {
        return TitanRepo.GetImage(repo);
    }

    protected List<String> GetEnv(Repository repo) {
        return TitanRepo.GetEnv(repo);
    }

    //https://www.baeldung.com/java-random-string#java8-alphanumeric
    protected String CreateRandomString(int len) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[0][];
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception { }
}

