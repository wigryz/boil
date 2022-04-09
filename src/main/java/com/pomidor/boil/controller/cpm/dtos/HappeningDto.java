package com.pomidor.boil.controller.cpm.dtos;

public record HappeningDto(
    Integer id,
    Double minHappenTime,
    Double maxHapperTime,
    Double reserveTime
) {
}
