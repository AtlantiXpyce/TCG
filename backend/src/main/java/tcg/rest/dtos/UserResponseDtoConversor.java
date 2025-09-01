package tcg.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import tcg.model.entities.User;

public class UserResponseDtoConversor {

    private UserResponseDtoConversor() {}

    public static UserResponse toUserResponse(User user) {
        if (user == null) return null;

        return new UserResponse(user);
    }

    public static List<UserResponse> toUserResponses(List<User> users) {
        return users.stream()
                    .map(UserResponseDtoConversor::toUserResponse)
                    .collect(Collectors.toList());
    }

    public static Page<UserResponse> toUserResponses(Page<User> userPage) {
        return userPage.map(UserResponseDtoConversor::toUserResponse);
    }
}
