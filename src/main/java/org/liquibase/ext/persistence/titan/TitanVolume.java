package org.liquibase.ext.persistence.titan;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.titandata.client.ApiClient;
import io.titandata.client.ApiException;
import io.titandata.client.VolumesApi;
import org.openapitools.client.model.Volume;

import java.util.Map;

public class TitanVolume {
    public String name;
    public String path;

    private final VolumesApi volumeApi;

    public TitanVolume(ApiClient TitanClient) {
        volumeApi = new VolumesApi(TitanClient);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Volume GetVolume(String repo, String volumeName) throws ApiException {
        return volumeApi.getVolume(repo, volumeName);
    }

    public void ActivateVolume(String repoName, String volName) throws ApiException {
        volumeApi.activateVolume(repoName, volName);
    }

    public void DeactivateVolume(String repoName, String volName) throws ApiException {
        volumeApi.deactivateVolume(repoName, volName);
    }

    public Map<String, String> GetVolumeConfig(Volume volume) {
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, String> config = oMapper.convertValue(volume.getConfig(), Map.class);
        return config;
    }
}