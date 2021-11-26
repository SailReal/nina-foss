
package de.ninafoss.data.repository.messagedetails;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "identifier",
    "sender",
    "sent",
    "status",
    "msgType",
    "scope",
    "code",
    "references",
    "info"
})
@Generated("jsonschema2pojo")
public class MessageDetailsDTO {

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("sent")
    private String sent;
    @JsonProperty("status")
    private String status;
    @JsonProperty("msgType")
    private String msgType;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("code")
    private List<String> code = null;
    @JsonProperty("references")
    private String references;
    @JsonProperty("info")
    private List<Info> info = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty("sent")
    public String getSent() {
        return sent;
    }

    @JsonProperty("sent")
    public void setSent(String sent) {
        this.sent = sent;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("msgType")
    public String getMsgType() {
        return msgType;
    }

    @JsonProperty("msgType")
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    @JsonProperty("code")
    public List<String> getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(List<String> code) {
        this.code = code;
    }

    @JsonProperty("references")
    public String getReferences() {
        return references;
    }

    @JsonProperty("references")
    public void setReferences(String references) {
        this.references = references;
    }

    @JsonProperty("info")
    public List<Info> getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(List<Info> info) {
        this.info = info;
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
