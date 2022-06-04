package com.pomidor.boil.cpm.dtos;

public record ActivityDto(
        String name,
        Double duration,
        Integer previousHappeningId,
        Integer nextHappeningId
) {
}
