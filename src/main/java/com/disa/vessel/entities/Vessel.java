package com.disa.vessel.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;

import java.util.Objects;

public class Vessel {
    private final String name;

    // Exercise description did not include country
    // in response json. We null country in out in controller,
    // and skip nulls fields during serialization here.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    public Vessel(String name, String country) {
        this.name = Objects.requireNonNull(
                Strings.emptyToNull(name),
                "Vessel name is required");
        this.country = Objects.requireNonNull(
                Strings.emptyToNull(country),
                "Vessel country is required");
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
