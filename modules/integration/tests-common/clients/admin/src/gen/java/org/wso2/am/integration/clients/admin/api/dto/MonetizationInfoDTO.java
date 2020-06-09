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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MonetizationInfoDTO
 */

public class MonetizationInfoDTO {
  /**
   * Flag to indicate the monetization plan
   */
  @JsonAdapter(MonetizationPlanEnum.Adapter.class)
  public enum MonetizationPlanEnum {
    FIXEDRATE("FixedRate"),
    
    DYNAMICRATE("DynamicRate");

    private String value;

    MonetizationPlanEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MonetizationPlanEnum fromValue(String text) {
      for (MonetizationPlanEnum b : MonetizationPlanEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<MonetizationPlanEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MonetizationPlanEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MonetizationPlanEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return MonetizationPlanEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("monetizationPlan")
  private MonetizationPlanEnum monetizationPlan = null;

  @SerializedName("properties")
  private Map<String, String> properties = new HashMap<>();

  public MonetizationInfoDTO monetizationPlan(MonetizationPlanEnum monetizationPlan) {
    this.monetizationPlan = monetizationPlan;
    return this;
  }

   /**
   * Flag to indicate the monetization plan
   * @return monetizationPlan
  **/
  @ApiModelProperty(example = "FixedRate", required = true, value = "Flag to indicate the monetization plan")
  public MonetizationPlanEnum getMonetizationPlan() {
    return monetizationPlan;
  }

  public void setMonetizationPlan(MonetizationPlanEnum monetizationPlan) {
    this.monetizationPlan = monetizationPlan;
  }

  public MonetizationInfoDTO properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public MonetizationInfoDTO putPropertiesItem(String key, String propertiesItem) {
    this.properties.put(key, propertiesItem);
    return this;
  }

   /**
   * Map of custom properties related to each monetization plan
   * @return properties
  **/
  @ApiModelProperty(required = true, value = "Map of custom properties related to each monetization plan")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MonetizationInfoDTO monetizationInfo = (MonetizationInfoDTO) o;
    return Objects.equals(this.monetizationPlan, monetizationInfo.monetizationPlan) &&
        Objects.equals(this.properties, monetizationInfo.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monetizationPlan, properties);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonetizationInfoDTO {\n");
    
    sb.append("    monetizationPlan: ").append(toIndentedString(monetizationPlan)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

