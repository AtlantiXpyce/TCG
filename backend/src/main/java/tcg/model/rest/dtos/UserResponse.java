package tcg.model.rest.dtos;

import tcg.model.entities.User;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean store;

    public UserResponse() {}

    public UserResponse(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.role = u.getRole();
        this.store = u.isStore();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isStore() {
        return store;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStore(boolean store) {
        this.store = store;
    }
}
