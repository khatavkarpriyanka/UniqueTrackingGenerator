package com.scalable.tracking.Controller;

import com.scalable.tracking.Model.TrackingResponse;
import com.scalable.tracking.Service.TrackingService;
import com.scalable.tracking.Validations.InputValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api")
public class TrackingController {

    private final TrackingService trackingService;

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }
    @GetMapping("/next-tracking-number")
    public ResponseEntity<Object> getNextTrackingNumber(
            @RequestParam String originCountryId,
            @RequestParam String destinationCountryId,
            @RequestParam String weightStr,
            @RequestParam (required = false) String createdAt,
            @RequestParam UUID customerId,
            @RequestParam String customerName,
            @RequestParam String customerSlug
    ) {
        List<String> errors = InputValidations.inputValidation(originCountryId,destinationCountryId,weightStr,customerName,customerSlug);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        if (createdAt != null) {
            try {
                OffsetDateTime.parse(createdAt);
            } catch (DateTimeParseException e) {
                errors.add("Invalid createdAt format. Must be in RFC 3339 format (e.g., '2025-03-25T14:30:00Z').");
                return ResponseEntity.badRequest().body(errors);
            }
        }

        Double weight = Double.parseDouble(weightStr);
        TrackingResponse trackingResponse = trackingService.generateTrackingNumber(
                originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug
        );
        return ResponseEntity.ok(trackingResponse);
    }
}