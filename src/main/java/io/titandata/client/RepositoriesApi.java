/*
 * Titan API
 * API used by the Titan CLI
 *
 * The version of the OpenAPI document: 0.3.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.titandata.client;

import io.titandata.client.ApiCallback;
import io.titandata.client.ApiClient;
import io.titandata.client.ApiException;
import io.titandata.client.ApiResponse;
import io.titandata.client.Configuration;
import io.titandata.client.Pair;
import io.titandata.client.ProgressRequestBody;
import io.titandata.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.ApiError;
import org.openapitools.client.model.Repository;
import org.openapitools.client.model.RepositoryStatus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoriesApi {
    private ApiClient localVarApiClient;

    public RepositoriesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public RepositoriesApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for createRepository
     * @param repository New repository to create (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Newly created repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createRepositoryCall(Repository repository, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = repository;

        // create path and map variables
        String localVarPath = "/v1/repositories";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createRepositoryValidateBeforeCall(Repository repository, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repository' is set
        if (repository == null) {
            throw new ApiException("Missing the required parameter 'repository' when calling createRepository(Async)");
        }
        

        okhttp3.Call localVarCall = createRepositoryCall(repository, _callback);
        return localVarCall;

    }

    /**
     * Create new repository
     * 
     * @param repository New repository to create (required)
     * @return Repository
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Newly created repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public Repository createRepository(Repository repository) throws ApiException {
        ApiResponse<Repository> localVarResp = createRepositoryWithHttpInfo(repository);
        return localVarResp.getData();
    }

    /**
     * Create new repository
     * 
     * @param repository New repository to create (required)
     * @return ApiResponse&lt;Repository&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Newly created repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Repository> createRepositoryWithHttpInfo(Repository repository) throws ApiException {
        okhttp3.Call localVarCall = createRepositoryValidateBeforeCall(repository, null);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create new repository (asynchronously)
     * 
     * @param repository New repository to create (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Newly created repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createRepositoryAsync(Repository repository, final ApiCallback<Repository> _callback) throws ApiException {

        okhttp3.Call localVarCall = createRepositoryValidateBeforeCall(repository, _callback);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteRepository
     * @param repositoryName Name of the repository (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Repository deleted </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteRepositoryCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/repositories/{repositoryName}"
            .replaceAll("\\{" + "repositoryName" + "\\}", localVarApiClient.escapeString(repositoryName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteRepositoryValidateBeforeCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repositoryName' is set
        if (repositoryName == null) {
            throw new ApiException("Missing the required parameter 'repositoryName' when calling deleteRepository(Async)");
        }
        

        okhttp3.Call localVarCall = deleteRepositoryCall(repositoryName, _callback);
        return localVarCall;

    }

    /**
     * Remove a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Repository deleted </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public void deleteRepository(String repositoryName) throws ApiException {
        deleteRepositoryWithHttpInfo(repositoryName);
    }

    /**
     * Remove a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Repository deleted </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteRepositoryWithHttpInfo(String repositoryName) throws ApiException {
        okhttp3.Call localVarCall = deleteRepositoryValidateBeforeCall(repositoryName, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Remove a repository (asynchronously)
     * 
     * @param repositoryName Name of the repository (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Repository deleted </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteRepositoryAsync(String repositoryName, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteRepositoryValidateBeforeCall(repositoryName, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getRepository
     * @param repositoryName Name of the repository (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository info </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getRepositoryCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/repositories/{repositoryName}"
            .replaceAll("\\{" + "repositoryName" + "\\}", localVarApiClient.escapeString(repositoryName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getRepositoryValidateBeforeCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repositoryName' is set
        if (repositoryName == null) {
            throw new ApiException("Missing the required parameter 'repositoryName' when calling getRepository(Async)");
        }
        

        okhttp3.Call localVarCall = getRepositoryCall(repositoryName, _callback);
        return localVarCall;

    }

    /**
     * Get info for a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @return Repository
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository info </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public Repository getRepository(String repositoryName) throws ApiException {
        ApiResponse<Repository> localVarResp = getRepositoryWithHttpInfo(repositoryName);
        return localVarResp.getData();
    }

    /**
     * Get info for a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @return ApiResponse&lt;Repository&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository info </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Repository> getRepositoryWithHttpInfo(String repositoryName) throws ApiException {
        okhttp3.Call localVarCall = getRepositoryValidateBeforeCall(repositoryName, null);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get info for a repository (asynchronously)
     * 
     * @param repositoryName Name of the repository (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository info </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getRepositoryAsync(String repositoryName, final ApiCallback<Repository> _callback) throws ApiException {

        okhttp3.Call localVarCall = getRepositoryValidateBeforeCall(repositoryName, _callback);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getRepositoryStatus
     * @param repositoryName Name of the repository (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository status </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getRepositoryStatusCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/repositories/{repositoryName}/status"
            .replaceAll("\\{" + "repositoryName" + "\\}", localVarApiClient.escapeString(repositoryName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getRepositoryStatusValidateBeforeCall(String repositoryName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repositoryName' is set
        if (repositoryName == null) {
            throw new ApiException("Missing the required parameter 'repositoryName' when calling getRepositoryStatus(Async)");
        }
        

        okhttp3.Call localVarCall = getRepositoryStatusCall(repositoryName, _callback);
        return localVarCall;

    }

    /**
     * Get current status of a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @return RepositoryStatus
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository status </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public RepositoryStatus getRepositoryStatus(String repositoryName) throws ApiException {
        ApiResponse<RepositoryStatus> localVarResp = getRepositoryStatusWithHttpInfo(repositoryName);
        return localVarResp.getData();
    }

    /**
     * Get current status of a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @return ApiResponse&lt;RepositoryStatus&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository status </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<RepositoryStatus> getRepositoryStatusWithHttpInfo(String repositoryName) throws ApiException {
        okhttp3.Call localVarCall = getRepositoryStatusValidateBeforeCall(repositoryName, null);
        Type localVarReturnType = new TypeToken<RepositoryStatus>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get current status of a repository (asynchronously)
     * 
     * @param repositoryName Name of the repository (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository status </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getRepositoryStatusAsync(String repositoryName, final ApiCallback<RepositoryStatus> _callback) throws ApiException {

        okhttp3.Call localVarCall = getRepositoryStatusValidateBeforeCall(repositoryName, _callback);
        Type localVarReturnType = new TypeToken<RepositoryStatus>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listRepositories
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> List of repositories </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listRepositoriesCall(final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/repositories";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listRepositoriesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listRepositoriesCall(_callback);
        return localVarCall;

    }

    /**
     * List repositories
     * 
     * @return List&lt;Repository&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> List of repositories </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public List<Repository> listRepositories() throws ApiException {
        ApiResponse<List<Repository>> localVarResp = listRepositoriesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * List repositories
     * 
     * @return ApiResponse&lt;List&lt;Repository&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> List of repositories </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<Repository>> listRepositoriesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = listRepositoriesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<Repository>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List repositories (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> List of repositories </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listRepositoriesAsync(final ApiCallback<List<Repository>> _callback) throws ApiException {

        okhttp3.Call localVarCall = listRepositoriesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<Repository>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateRepository
     * @param repositoryName Name of the repository (required)
     * @param repository New repository (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Updated repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateRepositoryCall(String repositoryName, Repository repository, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = repository;

        // create path and map variables
        String localVarPath = "/v1/repositories/{repositoryName}"
            .replaceAll("\\{" + "repositoryName" + "\\}", localVarApiClient.escapeString(repositoryName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateRepositoryValidateBeforeCall(String repositoryName, Repository repository, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repositoryName' is set
        if (repositoryName == null) {
            throw new ApiException("Missing the required parameter 'repositoryName' when calling updateRepository(Async)");
        }
        
        // verify the required parameter 'repository' is set
        if (repository == null) {
            throw new ApiException("Missing the required parameter 'repository' when calling updateRepository(Async)");
        }
        

        okhttp3.Call localVarCall = updateRepositoryCall(repositoryName, repository, _callback);
        return localVarCall;

    }

    /**
     * Update or rename a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @param repository New repository (required)
     * @return Repository
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Updated repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public Repository updateRepository(String repositoryName, Repository repository) throws ApiException {
        ApiResponse<Repository> localVarResp = updateRepositoryWithHttpInfo(repositoryName, repository);
        return localVarResp.getData();
    }

    /**
     * Update or rename a repository
     * 
     * @param repositoryName Name of the repository (required)
     * @param repository New repository (required)
     * @return ApiResponse&lt;Repository&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Updated repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Repository> updateRepositoryWithHttpInfo(String repositoryName, Repository repository) throws ApiException {
        okhttp3.Call localVarCall = updateRepositoryValidateBeforeCall(repositoryName, repository, null);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update or rename a repository (asynchronously)
     * 
     * @param repositoryName Name of the repository (required)
     * @param repository New repository (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Updated repository </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Malformed user input </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> No such object </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> An internal error occurred </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateRepositoryAsync(String repositoryName, Repository repository, final ApiCallback<Repository> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateRepositoryValidateBeforeCall(repositoryName, repository, _callback);
        Type localVarReturnType = new TypeToken<Repository>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}