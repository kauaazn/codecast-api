package com.code.codecast.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class StudioResponse {

    private Long id;
    private String name;
    private Integer maxCapacity;
    private List<String> equipments;
}