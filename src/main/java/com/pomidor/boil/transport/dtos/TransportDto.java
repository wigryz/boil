package com.pomidor.boil.transport.dtos;

import java.util.List;

public record TransportDto(
    List<TransactionDto> transactions
) {
}
