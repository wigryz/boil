package com.pomidor.boil.transport.dtos;

public record RecipientDto (
    Long id,
    Double demand,
    Double unitPrice
) {
}
