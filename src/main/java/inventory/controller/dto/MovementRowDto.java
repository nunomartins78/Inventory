package inventory.controller.dto;

import inventory.domain.MovementType;

import java.time.Instant;

public record MovementRowDto (
        Long movementId,
        Long itemId,
        MovementType type,
        int quantity,
        Instant createdAt
) {}
