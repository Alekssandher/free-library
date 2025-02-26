package free_library.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import free_library.dto.user.*;
import free_library.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserDto user)
    {
        UserResponseDto userCreated = service.create(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userCreated.getName())
            .toUri();

        return ResponseEntity.created(location).body(userCreated);
    }

    @GetMapping("{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email)
    {
        UserResponseDto user = service.findByEmail(email);
       
        return ResponseEntity.ok(user);
    }
}
