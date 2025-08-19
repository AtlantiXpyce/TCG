package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.Tcg;

public class TcgConversor {
    
    private TcgConversor(){}

    public static final TcgDto toTcgDto(Tcg tcg) {
        return new TcgDto(tcg.getId(),tcg.getName());
    }

    public static final List<TcgDto> toTcgDtos(List<Tcg> tcgs){
        return tcgs.stream().map(c -> toTcgDto(c)).collect(Collectors.toList());
    }
}
