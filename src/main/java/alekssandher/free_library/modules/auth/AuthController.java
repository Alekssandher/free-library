package alekssandher.free_library.modules.auth;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.OkResponse;
import alekssandher.free_library.dto.user.UserRequestDto;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.interfaces.user.IUserQueryService;
import alekssandher.free_library.interfaces.user.IUserService;
import alekssandher.free_library.mappers.UserMapper;
import alekssandher.free_library.modules.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final IUserQueryService queryService;
    private final IUserService service;
    private final UserMapper mapper;
    private final JwtService jwtService;
 

    public AuthController(IUserService service, UserMapper mapper, JwtService jwtService, IUserQueryService queryService)
    {
        this.queryService = queryService;
        this.service = service;
        this.mapper = mapper;
        this.jwtService = jwtService;

    }

    @PostMapping(value = "/register")
    public ResponseEntity<CreatedResponse> register(@Valid @RequestBody final UserRequestDto dto, HttpServletRequest request) throws ConflictException
    {
        var model = mapper.toModel(dto);

        service.create(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    @GetMapping(value = "/login/{email}/{password}")
    public ResponseEntity<OkResponse<String>> login( @PathVariable String email, @PathVariable String password, HttpServletRequest request ) throws BadRequestException
    {
        var model = queryService.validateCredentials(email, password);
        String token = jwtService.generateToken(model);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<String>(request, "Request Successful", "Loged with success", token));
    }

}