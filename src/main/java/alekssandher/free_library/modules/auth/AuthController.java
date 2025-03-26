package alekssandher.free_library.modules.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.OkResponse;
import alekssandher.free_library.dto.response.ErrorResponses.BadRequest;
import alekssandher.free_library.dto.response.ErrorResponses.Conflict;
import alekssandher.free_library.dto.response.ErrorResponses.InternalErrorCustom;
import alekssandher.free_library.dto.user.UserRequestDto;
import alekssandher.free_library.mappers.UserMapper;
import alekssandher.free_library.modules.users.UserQueryService;
import alekssandher.free_library.modules.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth", description = "Endpoint to register/login.")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
})
public class AuthController {
    private final UserQueryService queryService;
    private final UserService service;
    private final UserMapper mapper;
    private final JwtService jwtService;
 

    public AuthController(UserService service, UserMapper mapper, JwtService jwtService, UserQueryService queryService)
    {
        this.queryService = queryService;
        this.service = service;
        this.mapper = mapper;
        this.jwtService = jwtService;

    }
    
    @Operation(
        summary = "Register a new user",
        description = "Register a new user in the system by providing necessary information."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User successfully registered.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class))),
        @ApiResponse(responseCode = "400", description = "Malformed request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
        @ApiResponse(responseCode = "409", description = "User already exists.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Conflict.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<CreatedResponse<Void>> register(@Valid @RequestBody final UserRequestDto dto, HttpServletRequest request) 
    {
        var model = mapper.toUserEntity(dto);

        service.create(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse<>(request, null));
    }

    @Operation(
        summary = "Log in to the system",
        description = "Provide valid user credentials to receive a token authorization for accessing protected resources."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful and token generated.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class))),
        @ApiResponse(responseCode = "400", description = "Malformed request.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<OkResponse<String>> login( @Valid @RequestBody final UserLoginDto dto, HttpServletRequest request )
    {
        var model = queryService.validateCredentials(dto.email(), dto.password());
        String token = jwtService.generateToken(model);

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<String>(token, request));
    }

}