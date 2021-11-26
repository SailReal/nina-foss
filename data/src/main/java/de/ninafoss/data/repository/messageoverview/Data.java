
package de.ninafoss.data.repository.messageoverview;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "headline",
    "provider",
    "severity",
    "msgType",
    "transKeys",
    "area"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("headline")
    private String headline;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("msgType")
    private String msgType;
    @JsonProperty("transKeys")
    private TransKeys transKeys;
    @JsonProperty("area")
    private Area area;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("headline")
    public String getHeadline() {
        return headline;
    }

    @JsonProperty("headline")
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @JsonProperty("severity")
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("msgType")
    public String getMsgType() {
        return msgType;
    }

    @JsonProperty("msgType")
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @JsonProperty("transKeys")
    public TransKeys getTransKeys() {
        return transKeys;
    }

    @JsonProperty("transKeys")
    public void setTransKeys(TransKeys transKeys) {
        this.transKeys = transKeys;
    }

    @JsonProperty("area")
    public Area getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(Area area) {
        this.area = area;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
