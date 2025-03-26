package com.scalable.tracking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class
TrackingResponse {
    private String trackingNumber;
    private String createdAt;
}
