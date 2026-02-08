package inventory.controller.dto;

import inventory.domain.MovementType;

import java.time.Instant;

public record ApiErrorDto (
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}
