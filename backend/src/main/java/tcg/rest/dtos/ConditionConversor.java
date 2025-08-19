package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import tcg.model.entities.Condition;

public class ConditionConversor {

    private ConditionConversor() {}

    public static ConditionDto toConditionDto(Condition condition) {
        return new ConditionDto(condition.getId(), condition.getName(), condition.getInitial());
    }

    public static List<ConditionDto> toConditionDtos(List<Condition> conditions) {
        return conditions.stream().map(ConditionConversor::toConditionDto).collect(Collectors.toList());
    }
}
