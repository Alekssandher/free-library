package alekssandher.free_library.modules.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.dto.user.UserResponseDto;
import alekssandher.free_library.interfaces.user.IUserService;
import alekssandher.free_library.mappers.UserMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("users")
@SecurityRequirement(name = "Authorization")
public class UserController {
    private final IUserService service;
    private final UserMapper mapper;
    
    public UserController(IUserService service, UserMapper mapper)
    {
        this.service = service;
        this.mapper = mapper;
    }
    
    @GetMapping("{name}")
    public ResponseEntity<GetResponse<List<UserResponseDto>>> find(@PathVariable final String name, HttpServletRequest request)
    {
        var result = mapper.toListResponseDto(service.findUsersByName(name));

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<UserResponseDto>>(result, request));
    }

}
