package com.pomidor.boil.controller.dtoFront;

import java.util.List;

public record Elements (
        List<Node> nodes,
        List<Edge> edges
) {
}
