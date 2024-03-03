package com.epam.msvehicletracking;

import com.epam.msvehicletracking.model.Coordinate;
import com.epam.msvehicletracking.model.Signal;
import com.epam.msvehicletracking.service.IncomingSignalProcessor;
import com.epam.msvehicletracking.service.VehicleSignalProducer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaIntegrationTest {

    @SpyBean
    private IncomingSignalProcessor incomingSignalProcessor;

    @Autowired
    private VehicleSignalProducer vehicleSignalProducer;

    @Captor
    private ArgumentCaptor<Signal> signalArgumentCaptor;

    @Test
    public void embeddedKafka_whenSendingToSimpleProducer_thenMessageReceived() {
        // Given
        Signal expectedSignal = new Signal("1", new Coordinate(0.5, 0.5));

        // When
        vehicleSignalProducer.sendSignal(expectedSignal);

        // Then
        verify(incomingSignalProcessor, timeout(5000).times(1))
                .handleIncomingSignal(signalArgumentCaptor.capture());

        Signal actualSignal = signalArgumentCaptor.getValue();
        assertEquals(expectedSignal, actualSignal);
    }


    @Test
    public void embeddedKafka_whenSendingMultipleSignals_thenAllReceived() {
        // Given
        Signal signal1 = new Signal("1", new Coordinate(0.5, 0.5));
        Signal signal2 = new Signal("2", new Coordinate(1.0, 1.0));

        // When
        vehicleSignalProducer.sendSignal(signal1);
        vehicleSignalProducer.sendSignal(signal2);

        // Then
        verify(incomingSignalProcessor, timeout(5000).times(2))
                .handleIncomingSignal(signalArgumentCaptor.capture());

        List<Signal> capturedSignals = signalArgumentCaptor.getAllValues();
        assertTrue(capturedSignals.contains(signal1));
        assertTrue(capturedSignals.contains(signal2));
    }


    @Test
    public void embeddedKafka_whenNoSignalSent_thenNoProcessing() {
        verify(incomingSignalProcessor, never()).handleIncomingSignal(any());
    }
}
