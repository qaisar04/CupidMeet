package kz.baltabayev.locationdataservice.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String university;
    private String road;
    private String suburb;
    private String city_district;
    private String city;
    private String postcode;
    private String country;
    private String country_code;
}
