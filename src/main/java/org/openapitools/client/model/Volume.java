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


package org.openapitools.client.model;

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

/**
 * Volume
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-08-19T12:42:23.490528-04:00[America/New_York]")
public class Volume {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PROPERTIES = "properties";
  @SerializedName(SERIALIZED_NAME_PROPERTIES)
  private Object properties;

  public static final String SERIALIZED_NAME_CONFIG = "config";
  @SerializedName(SERIALIZED_NAME_CONFIG)
  private Object config;


  public Volume name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Volume name
   * @return name
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Volume name")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Volume properties(Object properties) {
    
    this.properties = properties;
    return this;
  }

   /**
   * Client-specific properties
   * @return properties
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Client-specific properties")

  public Object getProperties() {
    return properties;
  }


  public void setProperties(Object properties) {
    this.properties = properties;
  }


  public Volume config(Object config) {
    
    this.config = config;
    return this;
  }

   /**
   * Server-generated configuration
   * @return config
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Server-generated configuration")

  public Object getConfig() {
    return config;
  }


  public void setConfig(Object config) {
    this.config = config;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Volume volume = (Volume) o;
    return Objects.equals(this.name, volume.name) &&
        Objects.equals(this.properties, volume.properties) &&
        Objects.equals(this.config, volume.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, properties, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Volume {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
