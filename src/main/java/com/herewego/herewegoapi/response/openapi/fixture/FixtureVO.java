package com.herewego.herewegoapi.response.openapi.fixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixtureVO {
    Integer id;

    String referee;

    String timezone;

    String date;

    Long timestamp;

    VenueVO venue;
}
