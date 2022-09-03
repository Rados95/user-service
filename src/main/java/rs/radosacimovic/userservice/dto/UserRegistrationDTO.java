package rs.radosacimovic.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
}
