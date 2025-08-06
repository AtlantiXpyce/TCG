package tcg.rest.dtos;

public class UserProfileDto {
    private Long id;
    private String username;
    private String firstName;
    private boolean isStore;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String username, String firstName, boolean isStore) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.isStore = isStore;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setStore(boolean store) {
        isStore = store;
    }
}
