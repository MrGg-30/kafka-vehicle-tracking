package com.epam.msvehicletracking.controller;

import com.epam.msvehicletracking.dto.VehicleSignalRequest;
import com.epam.msvehicletracking.mapper.ApiSignalRequestMapper;
import com.epam.msvehicletracking.service.VehicleSignalProducer;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class VehicleTrackingController {

    private final ApiSignalRequestMapper mapper;
    private final VehicleSignalProducer vehicleTrackingService;

    @PostMapping("/track-vehicle")
    public ResponseEntity<String> trackVehicle(@Valid @RequestBody VehicleSignalRequest vehicleSignal) {
        vehicleTrackingService.sendSignal(mapper.map(vehicleSignal));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
