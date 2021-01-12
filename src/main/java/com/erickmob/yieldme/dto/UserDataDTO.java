package com.erickmob.yieldme.dto;


import java.util.List;

import com.erickmob.yieldme.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDataDTO {

    @ApiModelProperty(position = 0)
    private String name;

    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;
    @ApiModelProperty(position = 3)
    private String password;
    @ApiModelProperty(position = 4)
    List<Role> roles;


}