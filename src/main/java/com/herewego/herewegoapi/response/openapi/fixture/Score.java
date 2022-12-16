package com.herewego.herewegoapi.response.openapi.fixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    Integer home;

    Integer away;
}
