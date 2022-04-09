package com.pomidor.boil.controller.transport.dtos;

import java.util.List;

public record TransportDto(
    List<TransactionDto> transactions
) {
}
