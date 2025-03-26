package com.scalable.tracking.ServiceImpl;

import com.scalable.tracking.Model.TrackingResponse;
import com.scalable.tracking.Service.TrackingService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrackingServiceImpl implements TrackingService{
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE_LENGTH = 6;
    private static final int NODE_ID_LENGTH = 2;
    private static final int LOCAL_SEQ_LENGTH = 6;
    private static final int SEQUENCE_LENGTH = NODE_ID_LENGTH + LOCAL_SEQ_LENGTH;
    private static final int RANDOM_LENGTH = 2;
    private static final int TOTAL_LENGTH = BASE_LENGTH + SEQUENCE_LENGTH + RANDOM_LENGTH;

    private final AtomicLong localSeq = new AtomicLong(0);
    private final String nodeIdStr;

    public TrackingServiceImpl() {
        this.nodeIdStr = getNodeId();
    }

    public String generateTrackingId(String yearStr, String originCountryId, String destinationCountryId) {
        String base = yearStr + originCountryId.toUpperCase() + destinationCountryId.toUpperCase();

        long seqValue = localSeq.getAndIncrement();
        String localSeqStr = Long.toString(seqValue, 36).toUpperCase();
        if (localSeqStr.length() > LOCAL_SEQ_LENGTH) {
            throw new IllegalStateException("Local sequence exceeded maximum value for 6 base36 characters.");
        }
        String paddedLocalSeq = padLeft(localSeqStr, LOCAL_SEQ_LENGTH, '0');
        String sequencePart = nodeIdStr + paddedLocalSeq;

        String randomPart = generateRandomPart(RANDOM_LENGTH);

        String trackingNumber = base + sequencePart + randomPart;

        if (trackingNumber.length() != TOTAL_LENGTH) {
            throw new IllegalStateException("Tracking number length must be " + TOTAL_LENGTH + ".");
        }
        return trackingNumber;
    }

    private String generateRandomPart(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = ThreadLocalRandom.current().nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(idx));
        }
        return sb.toString();
    }

    private String padLeft(String s, int length, char padChar) {
        while (s.length() < length) {
            s = padChar + s;
        }
        return s;
    }

    public TrackingResponse generateTrackingNumber(String originCountryId, String destinationCountryId, Double weight,
                                                   String createdAt, UUID customerId, String customerName, String customerSlug) {
        Instant instant = createdAt != null ? Instant.parse(createdAt) : Instant.now();
        LocalDate date = LocalDate.ofInstant(instant, ZoneOffset.UTC);
        String yearStr = String.format("%02d", date.getYear() % 100);
        String trackingId = generateTrackingId(yearStr, originCountryId, destinationCountryId);
        TrackingResponse trackingResponse = new TrackingResponse();
        trackingResponse.setTrackingNumber(trackingId);
        trackingResponse.setCreatedAt(createdAt != null ? createdAt: DateTimeFormatter.ISO_INSTANT.format(instant));
        return trackingResponse;
    }

    public String getNodeId() {
        String podName = System.getenv("HOSTNAME");
        if (podName == null || !podName.matches(".*-\\d+")) {
            podName = "app-01";
        }
        String indexStr = podName.substring(podName.lastIndexOf('-') + 1);
        int index = Integer.parseInt(indexStr);
        return toBase36(index, NODE_ID_LENGTH);
    }

    public String toBase36(int num, int length) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        String result = Long.toString(num, 36).toUpperCase();
        if (result.length() > length) {
            throw new IllegalArgumentException("Index too large for " + length + " base36 digits");
        }
        return padLeft(result, length, '0');
    }
}