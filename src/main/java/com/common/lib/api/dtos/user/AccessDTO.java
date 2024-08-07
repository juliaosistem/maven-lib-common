package com.common.lib.api.dtos.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessDTO {
    @JsonProperty("manageGroupMembership")
    private  boolean manageGroupMembership;

    @JsonProperty("view")
    private  boolean view ;

    @JsonProperty("impersonate")
    private boolean impersonate;

    @JsonProperty("manage")
    private  boolean manage;
}
