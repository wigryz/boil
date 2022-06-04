package com.pomidor.boil.cpm.dtos;

import java.util.List;

public record HappeningDto(
    Integer id,
    Double minHappenTime,
    Double maxHappenTime,
    Double reserveTime,
    List<Integer> nextHappeningIds
) {
}