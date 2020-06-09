/*
 * WSO2 API Manager - Admin
 * This document specifies a **RESTful API** for WSO2 **API Manager** - Admin Portal. Please see [full swagger definition](https://raw.githubusercontent.com/wso2/carbon-apimgt/v6.5.176/components/apimgt/org.wso2.carbon.apimgt.rest.api.admin/src/main/resources/admin-api.yaml) of the API which is written using [swagger 2.0](http://swagger.io/) specification. 
 *
 * OpenAPI spec version: v1.1
 * Contact: architecture@wso2.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.wso2.am.integration.clients.admin.api.dto;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import org.wso2.am.integration.clients.admin.api.dto.CustomUrlInfoDevPortalDTO;

/**
 * The custom url information of the tenant domain
 */
@ApiModel(description = "The custom url information of the tenant domain")

public class CustomUrlInfoDTO {
  @SerializedName("tenantDomain")
  private String tenantDomain = null;

  @SerializedName("tenantAdminUsername")
  private String tenantAdminUsername = null;

  @SerializedName("enabled")
  private Boolean enabled = null;

  @SerializedName("devPortal")
  private CustomUrlInfoDevPortalDTO devPortal = null;

  public CustomUrlInfoDTO tenantDomain(String tenantDomain) {
    this.tenantDomain = tenantDomain;
    return this;
  }

   /**
   * Get tenantDomain
   * @return tenantDomain
  **/
  @ApiModelProperty(example = "carbon.super", value = "")
  public String getTenantDomain() {
    return tenantDomain;
  }

  public void setTenantDomain(String tenantDomain) {
    this.tenantDomain = tenantDomain;
  }

  public CustomUrlInfoDTO tenantAdminUsername(String tenantAdminUsername) {
    this.tenantAdminUsername = tenantAdminUsername;
    return this;
  }

   /**
   * Get tenantAdminUsername
   * @return tenantAdminUsername
  **/
  @ApiModelProperty(example = "john@foo.com", value = "")
  public String getTenantAdminUsername() {
    return tenantAdminUsername;
  }

  public void setTenantAdminUsername(String tenantAdminUsername) {
    this.tenantAdminUsername = tenantAdminUsername;
  }

  public CustomUrlInfoDTO enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

   /**
   * Get enabled
   * @return enabled
  **/
  @ApiModelProperty(example = "true", value = "")
  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public CustomUrlInfoDTO devPortal(CustomUrlInfoDevPortalDTO devPortal) {
    this.devPortal = devPortal;
    return this;
  }

   /**
   * Get devPortal
   * @return devPortal
  **/
  @ApiModelProperty(value = "")
  public CustomUrlInfoDevPortalDTO getDevPortal() {
    return devPortal;
  }

  public void setDevPortal(CustomUrlInfoDevPortalDTO devPortal) {
    this.devPortal = devPortal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomUrlInfoDTO customUrlInfo = (CustomUrlInfoDTO) o;
    return Objects.equals(this.tenantDomain, customUrlInfo.tenantDomain) &&
        Objects.equals(this.tenantAdminUsername, customUrlInfo.tenantAdminUsername) &&
        Objects.equals(this.enabled, customUrlInfo.enabled) &&
        Objects.equals(this.devPortal, customUrlInfo.devPortal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantDomain, tenantAdminUsername, enabled, devPortal);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomUrlInfoDTO {\n");
    
    sb.append("    tenantDomain: ").append(toIndentedString(tenantDomain)).append("\n");
    sb.append("    tenantAdminUsername: ").append(toIndentedString(tenantAdminUsername)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    devPortal: ").append(toIndentedString(devPortal)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

