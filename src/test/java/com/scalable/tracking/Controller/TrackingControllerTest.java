package com.scalable.tracking.Controller;

import com.scalable.tracking.Model.TrackingResponse;
import com.scalable.tracking.Service.TrackingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackingControllerTest {

    @Mock
    private TrackingService trackingService;

    @InjectMocks
    private TrackingController trackingController;

    @Test
    void testGetNextTrackingNumber() {
        UUID customerId = UUID.randomUUID();
        TrackingResponse mockResponse = new TrackingResponse("TR123456", "2025-03-25T14:30:00Z");
        when(trackingService.generateTrackingNumber("US", "CA", 1.5, null, customerId, "John Doe", "johndoe"))
                .thenReturn(mockResponse);

        ResponseEntity<Object> response = trackingController.getNextTrackingNumber("US", "CA", "1.5", null, customerId, "John Doe", "johndoe");

        assertNotNull(response.getBody());
    }
}