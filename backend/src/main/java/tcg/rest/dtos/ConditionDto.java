package tcg.rest.dtos;

public class ConditionDto {
    private Long id;
    private String name;
    private String initial;

    public ConditionDto() {}

    public ConditionDto(Long id, String name, String initial) {
        this.id = id;
        this.name = name;
        this.initial = initial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
}
