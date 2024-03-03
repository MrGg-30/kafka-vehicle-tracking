package com.epam.msvehicletracking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleSignalRequest {
    @NotNull
    private String vehicleId;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
