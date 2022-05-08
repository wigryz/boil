package com.pomidor.boil.transport.dtos;

public record SupplierDto(
    Long id,
    Double supply,
    Double unitPrice
) {
}
