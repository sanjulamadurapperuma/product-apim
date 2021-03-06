/*
 *Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.am.integration.tests.restapi.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.wso2.am.integration.test.utils.base.APIMIntegrationBaseTest;
import org.wso2.am.integration.tests.restapi.RESTAPITestConstants;
import org.wso2.am.integration.tests.restapi.utils.RESTAPITestUtil;
import org.wso2.carbon.automation.engine.annotations.ExecutionEnvironment;
import org.wso2.carbon.automation.engine.annotations.SetEnvironment;
import org.wso2.carbon.automation.engine.context.TestUserMode;

import java.io.File;

import static org.testng.Assert.assertTrue;

/**
 * This is an integration test case to the test the functionality of the regenerate consumer secret REST API
 * which can be used to regenerate the consumer secret of an application.
 */
@SetEnvironment(executionEnvironments = {ExecutionEnvironment.STANDALONE})
public class ApplicationRegenerateConsumerSecretTestCase extends APIMIntegrationBaseTest {

    @Factory(dataProvider = "userModeDataProvider")
    public ApplicationRegenerateConsumerSecretTestCase(TestUserMode userMode) {

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
    }

    @Test(groups = {"wso2.am"}, description = "REST API Implementation test : Application Regenerate Consumer Secret " +
            "handling test case")
    public void testRegenerateConsumerSecret() {

        String gatewayURL = getGatewayURLNhttp();
        String keyManagerURL = getKeyManagerURLHttp();

        // File name of the JSON data file related to : Regenerating consumer secret test case
        String dataFileName = "ApplicationRegenerateConsumerSecretTestCase.txt";
        String dataFilePath = (new File(System.getProperty(RESTAPITestConstants.USER_DIR))).getParent() +
                RESTAPITestConstants.PATH_SUBSTRING + dataFileName;
        boolean testSuccessStatus = new RESTAPITestUtil().testRestAPI(dataFilePath, gatewayURL, keyManagerURL);
        assertTrue(testSuccessStatus);
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {

        super.cleanUp();
    }

}
