package com.epam.msvehicletracking.service;

import com.epam.msvehicletracking.model.VehicleDistance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceivedDistanceProcessor {

    @KafkaListener(topics = "output", groupId = "distance-group")
    public void handleReceivedDistance(VehicleDistance receivedDistance) {
        log.info("Received vehicle distance - Vehicle: {}, Distance: {} meters",
                receivedDistance.vehicleId(), receivedDistance.distance());
    }
}
