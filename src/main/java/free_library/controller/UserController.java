package free_library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import free_library.dto.UserDto;
import free_library.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email)
    {
        UserDto user = service.findByEmail(email);
        System.err.println("User: " + user);
        return ResponseEntity.ok(user);
    }
}
