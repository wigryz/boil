package com.pomidor.boil.cpm.dtoFront;

import java.util.List;

public record CPM (
    Elements elements,
    List<Tippy> tippies,
    List<String> criticalPathIds,
    Double criticalPathLength
){}
