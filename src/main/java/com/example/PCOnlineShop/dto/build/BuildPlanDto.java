package com.example.PCOnlineShop.dto.build;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildPlanDto {
    private ComponentRule planMotherboard;
    private ComponentRule planCpu;
    private ComponentRule planGpu;
    private ComponentRule planRam;
    private ComponentRule planStorage;
    private ComponentRule planPsu;
    private  ComponentRule planCase;
    private   ComponentRule planCooling;
}
