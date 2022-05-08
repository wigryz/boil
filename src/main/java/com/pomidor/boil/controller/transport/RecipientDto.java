package com.pomidor.boil.controller.transport;

public record RecipientDto (
    Long id,
    Double demand,
    Double unitPrice
) {
}
