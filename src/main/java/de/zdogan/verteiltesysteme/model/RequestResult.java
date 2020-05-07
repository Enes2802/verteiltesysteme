package de.zdogan.verteiltesysteme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult<T>{

    @JsonProperty
    String message;

    @JsonProperty
    boolean success;

    @JsonProperty
    T represantation;
}
