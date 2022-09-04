package rs.radosacimovic.userservice;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rs.radosacimovic.userservice.model.UserEntity;
import rs.radosacimovic.userservice.service.UserService;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void initializeData() {
        userService.save(UserEntity.builder()
                .email("rados.acimovic@ftnmaste.com")
                .username("rados")
                .firstName("Rados")
                .lastName("Acimovic")
                .role("ADMIN")
                .password("rados")
                .build());
        userService.save(UserEntity.builder()
                .email("marija.nikolic@ftnmaster.com")
                .username("marija")
                .firstName("Marija")
                .lastName("Nikolic")
                .role("SELLER")
                .password("qwerty123")
                .build());
        userService.save(UserEntity.builder()
                .email("milos.karadzic@ftnmaster.com")
                .username("milos")
                .firstName("Milos")
                .lastName("Karadzic")
                .role("SELLER")
                .password("ghjkl345")
                .build());
        userService.save(UserEntity.builder()
                .email("natasa.jovanovic@ftnmaster.com")
                .username("natasa")
                .firstName("Natasa")
                .lastName("Jovanovic")
                .role("SELLER")
                .password("edcvfg473")
                .build());
    }

}
