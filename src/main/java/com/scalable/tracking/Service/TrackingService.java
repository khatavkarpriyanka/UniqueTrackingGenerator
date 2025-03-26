package com.scalable.tracking.Service;

import com.scalable.tracking.Model.TrackingResponse;

import java.util.UUID;

public interface TrackingService {

     TrackingResponse generateTrackingNumber(String originCountryId, String destinationCountryId, Double weight, String createdAt, UUID customerId, String customerName, String customerSlug);

}
