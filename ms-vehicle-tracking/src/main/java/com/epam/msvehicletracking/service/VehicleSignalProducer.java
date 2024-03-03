package com.epam.msvehicletracking.service;

import com.epam.msvehicletracking.model.Signal;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class VehicleSignalProducer {

    @Value("${kafka.vehicle-tracking.topic.input}")
    private String topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSignal(Signal vehicleSignal) {
        kafkaTemplate.send(topic, vehicleSignal.vehicleId(), vehicleSignal);
    }
}
