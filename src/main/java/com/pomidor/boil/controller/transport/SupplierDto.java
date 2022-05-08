package com.pomidor.boil.controller.transport;

public record SupplierDto(
    Long id,
    Double supply,
    Double unitPrice
) {
}
