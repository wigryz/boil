package com.pomidor.boil.controller.transport.dtos;

public record SupplierDto(
    Long id,
    Double supply,
    Double unitPrice
) {
}
