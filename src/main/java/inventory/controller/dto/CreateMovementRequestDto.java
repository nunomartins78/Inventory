package inventory.controller.dto;

import inventory.domain.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateMovementRequestDto (
   @NotNull Long itemId,
   @NotNull MovementType type,
   @Positive int quantity
) {}
