package com.herewego.herewegoapi.response.openapi.fixture;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FixtureByIdResponseVO {
    String get;

    ParameterVO parameters;

    List<ErrorVO> errors;

    Integer results;

    List<FixtureResponseVO> response;
}
