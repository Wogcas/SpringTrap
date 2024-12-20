package services.spring_project.authentication;

public class UserAdapter {

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.NOT_AUTH); //Default
        return user;
    }

}
