package com.pomidor.boil.controller.cpm.dtoFront;

import com.pomidor.boil.controller.cpm.dtos.CPMDto;
import com.pomidor.boil.controller.cpm.dtos.HappeningDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CPMMapper {

    public CPM mapToCPM(CPMDto dto) {
        List<Node> nodes = dto.happenings().stream().map(happeningDto -> new Node(new NodeData(happeningDto.id().toString()))).toList();
        List<Edge> edges = new LinkedList<>();
        int firstId = 1000;
        for (HappeningDto happening : dto.happenings()) {
            List<Integer> nextHappeningIds = happening.nextHappeningIds();
            for (Integer next : nextHappeningIds) {
                edges.add(new Edge(new EdgeData(String.valueOf(firstId),
                        happening.id().toString(),
                        next.toString())));
                firstId++;
            }
        }

        Elements elements = new Elements(nodes, edges);
        List<Tippy> tippies = dto.happenings().stream().map(happeningDto -> new Tippy(happeningDto.id().toString(),
                "id: " + happeningDto.id() + "<br>" +
                        "minHappenTime: " + happeningDto.minHappenTime() + "<br>" +
                        "maxHappenTime: " + happeningDto.maxHappenTime() + "<br>" +
                        "reserveTime: " + happeningDto.reserveTime()
        )).toList();

        List<String> criticalPath = new LinkedList<>();
        criticalPath.add(dto.criticalPath().entrySet().iterator().next().getKey().toString());
        for (Map.Entry<Integer, Integer> critPath : dto.criticalPath().entrySet()) {
            criticalPath.add(critPath.getValue().toString());
        }

        return new CPM(elements, tippies, criticalPath, dto.criticalPathLength());
    }
}
