
# APIProductInfoDTO

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | UUID of the api product  |  [optional]
**name** | **String** | Name of the API Product |  [optional]
**context** | **String** |  |  [optional]
**description** | **String** | A brief description about the API |  [optional]
**provider** | **String** | If the provider value is not given, the user invoking the API will be used as the provider.  |  [optional]
**thumbnailUri** | **String** |  |  [optional]
**state** | [**StateEnum**](#StateEnum) | State of the API product. Only published api products are visible on the store  |  [optional]
**securityScheme** | **List&lt;String&gt;** | Types of API security, the current API secured with. It can be either OAuth2 or mutual SSL or both. If it is not set OAuth2 will be set as the security for the current API.  |  [optional]


<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
CREATED | &quot;CREATED&quot;
PUBLISHED | &quot;PUBLISHED&quot;



