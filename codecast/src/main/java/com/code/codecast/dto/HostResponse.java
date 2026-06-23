package com.code.codecast.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HostResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
}