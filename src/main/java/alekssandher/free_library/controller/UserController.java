package alekssandher.free_library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.free_library.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.free_library.dto.response.ApiResponseDto.GetResponse;
import alekssandher.free_library.dto.user.UserRequestDto;
import alekssandher.free_library.dto.user.UserResponseDto;
import alekssandher.free_library.exception.Exceptions.ConflictException;
import alekssandher.free_library.exception.Exceptions.NotFoundException;
import alekssandher.free_library.mappers.UserMapper;
import alekssandher.free_library.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService service;
    private final UserMapper mapper;
    
    public UserController(IUserService service, UserMapper mapper)
    {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CreatedResponse> create(@Valid @RequestBody final UserRequestDto dto, HttpServletRequest request) throws ConflictException
    {
        var model = mapper.toModel(dto);

        service.create(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    @GetMapping("{name}")
    public ResponseEntity<GetResponse<List<UserResponseDto>>> find(@PathVariable final String name, HttpServletRequest request)
    {
        var result = mapper.toListResponseDto(service.findUsersByName(name));

        return ResponseEntity.status(HttpStatus.OK).body(new GetResponse<List<UserResponseDto>>(result, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) throws NotFoundException
    {
        service.deleteById(id);;

        return ResponseEntity.noContent().build();
    }
}
