
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
    "language",
    "category",
    "event",
    "urgency",
    "severity",
    "certainty",
    "eventCode",
    "headline",
    "description",
    "instruction",
    "contact",
    "parameter",
    "area"
})
@Generated("jsonschema2pojo")
public class Info {

    @JsonProperty("language")
    private String language;
    @JsonProperty("category")
    private List<String> category = null;
    @JsonProperty("event")
    private String event;
    @JsonProperty("urgency")
    private String urgency;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("certainty")
    private String certainty;
    @JsonProperty("eventCode")
    private List<EventCode> eventCode = null;
    @JsonProperty("headline")
    private String headline;
    @JsonProperty("description")
    private String description;
    @JsonProperty("instruction")
    private String instruction;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("parameter")
    private List<Parameter> parameter = null;
    @JsonProperty("area")
    private List<Area> area = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("category")
    public List<String> getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(List<String> category) {
        this.category = category;
    }

    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }

    @JsonProperty("urgency")
    public String getUrgency() {
        return urgency;
    }

    @JsonProperty("urgency")
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @JsonProperty("severity")
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("certainty")
    public String getCertainty() {
        return certainty;
    }

    @JsonProperty("certainty")
    public void setCertainty(String certainty) {
        this.certainty = certainty;
    }

    @JsonProperty("eventCode")
    public List<EventCode> getEventCode() {
        return eventCode;
    }

    @JsonProperty("eventCode")
    public void setEventCode(List<EventCode> eventCode) {
        this.eventCode = eventCode;
    }

    @JsonProperty("headline")
    public String getHeadline() {
        return headline;
    }

    @JsonProperty("headline")
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("instruction")
    public String getInstruction() {
        return instruction;
    }

    @JsonProperty("instruction")
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @JsonProperty("contact")
    public String getContact() {
        return contact;
    }

    @JsonProperty("contact")
    public void setContact(String contact) {
        this.contact = contact;
    }

    @JsonProperty("parameter")
    public List<Parameter> getParameter() {
        return parameter;
    }

    @JsonProperty("parameter")
    public void setParameter(List<Parameter> parameter) {
        this.parameter = parameter;
    }

    @JsonProperty("area")
    public List<Area> getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(List<Area> area) {
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
