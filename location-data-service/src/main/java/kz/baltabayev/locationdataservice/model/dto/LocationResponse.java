package kz.baltabayev.locationdataservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.baltabayev.locationdataservice.model.payload.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponse {
    private String place_id;
    private String licence;
    private String osm_type;
    private long osm_id;
    private String lat;
    private String lon;
    private String display_name;
    private Address address;
    private String[] boundingbox;
}
