package org.wso2.am.integration.tests.restapi.testcases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.wso2.am.integration.test.utils.base.APIMIntegrationConstants;
import org.wso2.am.integration.test.utils.bean.*;
import org.wso2.am.integration.tests.api.lifecycle.APIManagerLifecycleBaseTest;
import org.wso2.carbon.automation.engine.context.TestUserMode;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;

import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

public class APISecurityAuditTestCase extends APIManagerLifecycleBaseTest {
    private static final Log log = LogFactory.getLog(APISecurityAuditTestCase.class);
    private ServerConfigurationManager serverConfigurationManager;

    private String apiId;

    @Factory(dataProvider = "userModeDataProvider")
    public APISecurityAuditTestCase(TestUserMode userMode) {
        this.userMode = userMode;
    }

    @DataProvider
    public static Object[][] userModeDataProvider() {
        return new Object[][]{
                new Object[]{TestUserMode.SUPER_TENANT_ADMIN}
        };
    }
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init(userMode);
        serverConfigurationManager = new ServerConfigurationManager(gatewayContextWrk);
        serverConfigurationManager.applyConfiguration(new File(
                getAMResourceLocation() + File.separator + "configFiles" + File.separator + "apiSecurityAudit"
                        + File.separator + "deployment.toml"));
    }

    @Test(groups = "wso2.am", description = "Check the functionality of API Security Audit feature")
    public void testAuditAPIGet() throws Exception {
//        String apiName = "AuditAPI";
//        String apiVersion = "1.0.0";
//        String apiContext = "audit";
//        String providerName = "admin";
//        String endpointUrl = "http://testurl";
//
//        APIRequest apiRequest = new APIRequest(apiName, apiContext, new URL(endpointUrl));
//        apiRequest.setVersion(apiVersion);
//        apiRequest.setProvider(providerName);
//        apiRequest.setTiersCollection(APIMIntegrationConstants.API_TIER.UNLIMITED);
//        apiRequest.setTier(APIMIntegrationConstants.API_TIER.UNLIMITED);
//
//        apiId = createAndPublishAPIUsingRest(apiRequest, restAPIPublisher, false);

        // Add api
//        HttpResponse serviceResponse = restAPIPublisher.addAPI(apiRequest);
//        apiId = serviceResponse.getData();
//        HttpResponse response = restAPIPublisher.getAuditApi(apiId);
//
//        Assert.assertEquals(response.getResponseCode(), Response.Status.OK.getStatusCode(),
//                "The response code is not 200 OK");
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        if (TestUserMode.SUPER_TENANT_ADMIN == userMode) {
            serverConfigurationManager.restoreToLastConfiguration();
        }
    }
}
