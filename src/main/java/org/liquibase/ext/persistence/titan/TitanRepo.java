package org.liquibase.ext.persistence.titan;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.titandata.client.ApiClient;
import io.titandata.client.ApiException;
import io.titandata.client.CommitsApi;
import io.titandata.client.RepositoriesApi;
import org.openapitools.client.model.Commit;
import org.openapitools.client.model.Repository;
import org.openapitools.client.model.RepositoryStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TitanRepo {

    private final ApiClient TitanClient;
    private final RepositoriesApi repoApi;
    private final ObjectMapper oMapper;

    public TitanRepo(ApiClient titanClient) {
        TitanClient = titanClient;
        repoApi = new RepositoriesApi(TitanClient);
        oMapper = new ObjectMapper();
    }

    public Repository GetRepoInfo(String repoName) throws ApiException {
        return repoApi.getRepository(repoName);
    }

    public List<TitanVolume> GetVolumes(Repository repo) {
        Map<String, Object> v2 = getV2(repo.getProperties());
        ArrayList vols = (ArrayList) v2.get("volumes");

        List<TitanVolume> volumes = new ArrayList<>();
        int index = 0;
        while (vols.size() > index) {
            Map<String, String> v = oMapper.convertValue(vols.get(index++), Map.class);
            TitanVolume vol = new TitanVolume(TitanClient){};
            vol.setName(v.get("name"));
            vol.setPath(v.get("path"));
            volumes.add(vol);
        }

        return volumes;
    }

    public List<TitanPort> GetPorts(Repository repo) {
        Map<String, Object> v2 = getV2(repo.getProperties());
        ArrayList ps = (ArrayList) v2.get("ports");

        List<TitanPort> ports = new ArrayList<>();
        int index = 0;
        while (ps.size() > index) {
            Map<String, String> p = oMapper.convertValue(ps.get(index++), Map.class);
            TitanPort port = new TitanPort(){};
            port.setProtocol(p.get("protocol"));
            port.setPort(p.get("port"));
            ports.add(port);
        }

        return ports;
    }

    public String GetPortFromCommit(Commit commit) {
        Map<String, Object> v2 = getV2(commit.getProperties());
        ArrayList ps = (ArrayList) v2.get("ports");

        List<TitanPort> ports = new ArrayList<>();
        int index = 0;
        while (ps.size() > index) {
            Map<String, String> p = oMapper.convertValue(ps.get(index++), Map.class);
            TitanPort port = new TitanPort(){};
            port.setProtocol(p.get("protocol"));
            port.setPort(p.get("port"));
            ports.add(port);
        }

        return ports.get(1).port;
    }

    public String GetPortFromRepo(String repo) throws ApiException {
        RepositoryStatus status = repoApi.getRepositoryStatus(repo);
        CommitsApi commitsApi = new CommitsApi(TitanClient);
        Commit commit = commitsApi.getCommit(repo, status.getLastCommit());

        return GetPortFromCommit(commit);
    }

    public TitanImage GetImage(Repository repo) {
        Map<String, Object> v2 = getV2(repo.getProperties());
        Map<String, String> imageMap = oMapper.convertValue(v2.get("image"), Map.class);

        TitanImage titanImage = new TitanImage();
        titanImage.setImage(imageMap.get("image"));
        titanImage.setDigest(imageMap.get("digest"));
        titanImage.setTag(imageMap.get("tag"));

        return titanImage;
    }

    public List<String> GetEnv(Repository repo){
        Map<String, Object> v2 = getV2(repo.getProperties());
        List<String> envs = oMapper.convertValue(v2.get("environment"), List.class);
        return envs;
    }

    private Map<String, Object> createMapObject(Object obj) {
        return oMapper.convertValue(obj, Map.class);
    }

    private Map getV2(Object properties) {
        Map<String, Object> props = createMapObject(properties);
        Map<String, Object> v2 = createMapObject(props.get("v2"));
        return v2;
    }
}
