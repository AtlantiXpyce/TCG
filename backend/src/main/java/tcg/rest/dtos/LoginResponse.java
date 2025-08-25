package tcg.rest.dtos;

public class LoginResponse {
    private String token;
    private String username;
    private String role;
    private Long id;
    private Boolean store;

    public LoginResponse(String token, String username, String role, Long id, Boolean store) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.id = id;
        this.store = store;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Long getId(){
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }
}
