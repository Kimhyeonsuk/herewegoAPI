package com.herewego.herewegoapi.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeGameUnitDTO {
    String asis;
    String tobe;
}
