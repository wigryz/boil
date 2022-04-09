package com.pomidor.boil.controller.cpm.dtos;

public record ActivityDto(
        String name,
        Double duration,
        Integer previousHappeningId,
        Integer nextHappeningId
) {
}
