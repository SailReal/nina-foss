
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
    "areaDesc",
    "geocode"
})
@Generated("jsonschema2pojo")
public class Area {

    @JsonProperty("areaDesc")
    private String areaDesc;
    @JsonProperty("geocode")
    private List<Geocode> geocode = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("areaDesc")
    public String getAreaDesc() {
        return areaDesc;
    }

    @JsonProperty("areaDesc")
    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    @JsonProperty("geocode")
    public List<Geocode> getGeocode() {
        return geocode;
    }

    @JsonProperty("geocode")
    public void setGeocode(List<Geocode> geocode) {
        this.geocode = geocode;
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
