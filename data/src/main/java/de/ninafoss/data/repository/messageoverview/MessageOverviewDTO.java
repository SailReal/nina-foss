
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
    "id",
    "payload",
    "i18nTitle",
    "sent"
})
@Generated("jsonschema2pojo")
public class MessageOverviewDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("payload")
    private Payload payload;
    @JsonProperty("i18nTitle")
    private I18nTitle i18nTitle;
    @JsonProperty("sent")
    private String sent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @JsonProperty("i18nTitle")
    public I18nTitle getI18nTitle() {
        return i18nTitle;
    }

    @JsonProperty("i18nTitle")
    public void setI18nTitle(I18nTitle i18nTitle) {
        this.i18nTitle = i18nTitle;
    }

    @JsonProperty("sent")
    public String getSent() {
        return sent;
    }

    @JsonProperty("sent")
    public void setSent(String sent) {
        this.sent = sent;
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
