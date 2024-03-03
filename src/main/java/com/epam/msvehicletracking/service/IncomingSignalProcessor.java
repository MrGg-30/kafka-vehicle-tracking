package com.epam.msvehicletracking.service;

import com.epam.msvehicletracking.model.Coordinate;
import com.epam.msvehicletracking.model.Signal;
import com.epam.msvehicletracking.model.VehicleDistance;
import com.epam.msvehicletracking.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomingSignalProcessor {

    private final Map<String, Coordinate> vehicleCoordinates = new HashMap<>();
    private final VehicleDistanceProducer distanceSenderService;
    private final DistanceCalculator distanceCalculator;

    @KafkaListener(topics = "input", groupId = "signal-group")
    public void handleIncomingSignal(Signal signal) {
        if (vehicleCoordinates.containsKey(signal.vehicleId())) {
            Coordinate previousCoordinate = vehicleCoordinates.get(signal.vehicleId());
            double distance = distanceCalculator.calculateDistance(previousCoordinate, signal.coordinate());
            distanceSenderService.sendDistance(new VehicleDistance(signal.vehicleId(), distance));
        } else {
            distanceSenderService.sendDistance(new VehicleDistance(signal.vehicleId(), 0.0));
            vehicleCoordinates.put(signal.vehicleId(), signal.coordinate());
        }
    }
}
