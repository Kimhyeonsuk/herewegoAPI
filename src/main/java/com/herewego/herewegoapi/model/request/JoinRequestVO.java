package com.herewego.herewegoapi.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.herewego.herewegoapi.common.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinRequestVO {
    AuthProvider authProvider;

    String email;

    String name;

    String image;
}
