package com.pomidor.boil.transport.dtos;

public record TransportCostDto(
    Long supplierId,
    Long recipientId,
    Double cost
) {
}
