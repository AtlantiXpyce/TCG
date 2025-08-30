package tcg.rest.dtos;

public class UserProfileDto {
    private Long id;
    private String username;
    private String firstName;
    private boolean isStore;
    private Double latitude;
    private Double longitude;
    private Double rating;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String username, String firstName, boolean isStore, Double longitude, Double latitude, Double rating) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.isStore = isStore;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = rating;
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

    public Double getLatitude(){
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    
    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
