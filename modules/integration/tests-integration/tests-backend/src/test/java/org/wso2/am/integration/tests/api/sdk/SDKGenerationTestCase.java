/*
*Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.am.integration.tests.api.sdk;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.am.integration.clients.store.api.ApiException;
import org.wso2.am.integration.clients.store.api.ApiResponse;
import org.wso2.am.integration.test.impl.RestAPIPublisherImpl;
import org.wso2.am.integration.test.impl.RestAPIStoreImpl;
import org.wso2.am.integration.test.utils.base.APIMIntegrationBaseTest;
import org.wso2.am.integration.test.utils.bean.APIRequest;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;

import java.io.File;
import java.net.URL;

public class SDKGenerationTestCase extends APIMIntegrationBaseTest {

    private final Log log = LogFactory.getLog(SDKGenerationTestCase.class);
    //details of the first tenant
    private final String firstTenantDomain = "tenant1.com";
    private final String firstTenantAdminUserName = "firstAdmin";
    private final String firstTenantAdminPassword = "password1";
    //details of the second tenant
    private final String secondTenantDomain = "tenant2.com";
    private final String secondTenantAdminUserName = "secondAdmin";
    private final String secondTenantAdminPassword = "password2";
    //details of the API to be created at the tenant space of first tenant
    private final String apiName = "TestAPI";
    private final String apiVersion = "1.0.0";
    //API will be created by tenant admin of the first tenant domain
    private final String apiProvider = firstTenantAdminUserName + "@" + firstTenantDomain;
    private RestAPIPublisherImpl restAPIPublisher;
    private String testApiId;
    private String privateApiId;

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
        //create first tenant
        tenantManagementServiceClient.addTenant(firstTenantDomain, firstTenantAdminPassword, firstTenantAdminUserName,
                "demo");
        //create second tenant
        tenantManagementServiceClient.addTenant(secondTenantDomain, secondTenantAdminPassword,
                secondTenantAdminUserName, "demo");
    }

    @Test(groups = {"wso2.am"}, description = "SDK Generation test case")
    public void testSDKGeneration() throws Exception {
        //login to API publisher as first tenant admin to create the API
        restAPIPublisher = new RestAPIPublisherImpl(apiProvider, firstTenantAdminPassword, firstTenantDomain,
                publisherURLHttps);
        //Wait till CommonConfigDeployer finishes adding the default set of policies to the database after tenant admin
        //login, if not api creation fails since Unlimited resource tier is not available in database.
        waitForAPIDeployment();
        //prepare API to create and publish
        String apiContext = "testContext";
        String url = "https://localhost:9443/test";
        APIRequest apiRequest = new APIRequest(apiName, apiContext, new URL(url));
        String tags = "sdkGen";
        apiRequest.setTags(tags);
        String description = "This is test API create by API manager integration test.";
        apiRequest.setDescription(description);
        apiRequest.setVersion(apiVersion);
        apiRequest.setSandbox(url);
        apiRequest.setResourceMethod("GET");
        apiRequest.setProvider(apiProvider);
        //set API visibility to public hence this API should be visible to all tenants
        apiRequest.setVisibility("public");
        HttpResponse response = restAPIPublisher.addAPI(apiRequest);
        testApiId = response.getData();
        int responseCode = response.getResponseCode();

        // Assert api creation.
        Assert.assertEquals(responseCode, HttpStatus.SC_CREATED);

        HttpResponse lifecycleResponse = restAPIPublisher.changeAPILifeCycleStatusToPublish(testApiId, false);

        // Assert successful lifecycle change
        Assert.assertEquals(lifecycleResponse.getResponseCode(), HttpStatus.SC_OK);

        //check for SDK generation in same tenant domain,
        //(i.e - tenant admin of first tenant logs into the API store)
        //we can use the apiProvider as the user name hence this is a login to the same tenant domain
        String sdkLanguage = "java";
        boolean isSDKGenerationSuccessfulInSameTenant = generateSDK(apiProvider, firstTenantAdminPassword,
                testApiId, sdkLanguage, firstTenantDomain);

        //check for SDK generation across tenant domains
        //(i.e - tenant admin of second tenant should log into the API store)
        boolean isSDKGenerationSuccessfulAcrossTenants = generateSDK
                (secondTenantAdminUserName + "@" + secondTenantDomain, secondTenantAdminPassword,
                        testApiId, sdkLanguage, firstTenantDomain);
        Assert.assertTrue(isSDKGenerationSuccessfulInSameTenant && !isSDKGenerationSuccessfulAcrossTenants);
    }

    @Test(groups = {"wso2.am"}, description = "SDK Generation test case for private apis")
    public void testSDKGenerationForPrivateAPIs() throws Exception {
        //login to API publisher as first tenant admin to create the API
        restAPIPublisher = new RestAPIPublisherImpl(apiProvider, firstTenantAdminPassword, firstTenantDomain,
                publisherURLHttps);
        //prepare API to create and publish
        String apiName = "PrivateAPI";
        String apiContext = "privateContext";
        String url = "https://localhost:9443/test";
        APIRequest apiRequest = new APIRequest(apiName, apiContext, new URL(url));
        String tags = "sdkGen";
        apiRequest.setTags(tags);
        String description = "This is test API create by API manager integration test.";
        apiRequest.setDescription(description);
        apiRequest.setVersion(apiVersion);
        apiRequest.setSandbox(url);
        apiRequest.setResourceMethod("GET");
        apiRequest.setProvider(apiProvider);
        //set API visibility to private hence this API should be visible within domain
        apiRequest.setVisibility("private");
        HttpResponse response = restAPIPublisher.addAPI(apiRequest);
        //update the lifecycle of the API to Published state, so that is it visible in store
        privateApiId = response.getData();
        int responseCode = response.getResponseCode();

        // Assert api creation.
        Assert.assertEquals(responseCode, HttpStatus.SC_CREATED);

        HttpResponse lifecycleResponse = restAPIPublisher.changeAPILifeCycleStatusToPublish(privateApiId, false);

        // Assert successful lifecycle change
        Assert.assertEquals(lifecycleResponse.getResponseCode(), HttpStatus.SC_OK);
        //check for SDK generation in same tenant domain,
        //(i.e - tenant admin of first tenant logs into the API store)
        //we can use the apiProvider as the user name hence this is a login to the same tenant domain
        String sdkLanguage = "java";
        boolean isSDKGenerationSuccessfulInSameTenant = generateSDK(apiProvider, firstTenantAdminPassword, privateApiId,
                sdkLanguage, firstTenantDomain);

        //check for SDK generation when API is private
        //(i.e - tenant admin of second tenant should log into the API store)
        boolean isSDKGenerationSuccessfulForPrivateAPIs = generateSDK
                (secondTenantAdminUserName + "@" + secondTenantDomain, secondTenantAdminPassword,
                        privateApiId, sdkLanguage, firstTenantDomain);
        Assert.assertTrue(isSDKGenerationSuccessfulInSameTenant && !isSDKGenerationSuccessfulForPrivateAPIs);
    }

    /**
     * This method generates SDK for a given API for a given programming language
     *
     * @param tenantAwareUserName username of the user who will log into store
     * @param password            password of the user who will log into store
     * @param language            programming language for the SDK
     * @return true if SDK generation is successful and the SDK is non empty
     * @throws Exception if SDK generation failed
     */
    private boolean generateSDK(String tenantAwareUserName, String password, String apiId, String language,
            String tenant) throws Exception {

        RestAPIStoreImpl restAPIStore = new RestAPIStoreImpl(tenantAwareUserName, password, tenant, storeURLHttps);
        try {
            restAPIStore.getAPI(apiId);
        } catch (ApiException e) {
            return false;
        }

        ApiResponse<byte[]> sdkGenerationResponse = restAPIStore.generateSDKUpdated(apiId, language);

        if (!sdkGenerationResponse.getHeaders().containsKey("Content-Disposition")) {
            log.error("SDK generation failed for API : " + apiName + " " + apiVersion + " User : " +
                    tenantAwareUserName);
            return false;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(sdkGenerationResponse.getData());
        String tempDirectoryLocation = "tmp";
        String zipFileName = RandomStringUtils.randomAlphabetic(5) + ".zip";
        File sdkArchive = new File(tempDirectoryLocation + File.separator + zipFileName);
        FileUtils.writeByteArrayToFile(sdkArchive, byteArrayOutputStream.toByteArray());

        //SDK archive file should not be empty
        if (FileUtils.sizeOf(sdkArchive) > 0.0) {
            log.info("SDK generated successfully for API : " + apiName + " " + apiVersion +
                    " User : " + tenantAwareUserName);
            return true;
        }
        log.error("SDK generation failed for API : " + apiName + " " + apiVersion + " User : " + tenantAwareUserName);
        return false;
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        if (privateApiId != null) {
            restAPIPublisher.deleteAPI(privateApiId);
        }
        if (testApiId != null) {
            restAPIPublisher.deleteAPI(testApiId);
        }
        tenantManagementServiceClient.deleteTenant(firstTenantDomain);
        tenantManagementServiceClient.deleteTenant(secondTenantDomain);
    }
}
