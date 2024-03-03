package com.epam.msvehicletracking.service;

import com.epam.msvehicletracking.model.VehicleDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDistanceProducer {

    @Value("${kafka.vehicle-tracking.topic.output}")
    private String topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendDistance(VehicleDistance distance) {
        kafkaTemplate.send(topic, distance.vehicleId(), distance);
    }
}
