package com.pomidor.boil.controller.transport.dtos;

public record RecipientDto (
    Long id,
    Double demand,
    Double unitPrice
) {
}
