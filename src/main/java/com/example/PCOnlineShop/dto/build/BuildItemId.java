package com.example.PCOnlineShop.dto.build;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildItemId {
    private int mainboardId;
    private int cpuId;
    private int ramId;
    private int gpuId;
    private int storageId;
    private int psuId;
    private int caseId;
    private int coolerId;
    private int otherId;
    private double totalPrice;
}
