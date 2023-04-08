package com.orderpicker.user.infrastructure.controller;


import com.orderpicker.user.application.exception.UserBadRequestException;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import com.orderpicker.user.infrastructure.response.UserResponse;
import com.orderpicker.user.infrastructure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.orderpicker.user.infrastructure.constants.UserEndpointsConstants.*;
import static com.orderpicker.user.infrastructure.constants.UserPaginationRequest.*;

@RestController
@RequestMapping(ENDPOINT_USERS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final MapperUser mapperUser;

    @Operation(summary = "Create a User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User Created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid values entry",
                    content = @Content
            )
    })
    @PostMapping()
    ResponseEntity<UserDTO> save(
            @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new UserBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.save(userDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Found",
                    content = {
                            @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_USER_ID)
    ResponseEntity<UserDTO> getById(
            @Parameter(description = "User ID to search")
            @PathVariable("id") Long id
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateUserRequestById(id, userEmail);
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.getById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get All Users",
                    content = {
                            @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            )
        }
    )
    @GetMapping
    ResponseEntity<UserResponse> findAll(
            @Parameter(description = "Choose a page number in the search")
            @RequestParam(value = "pageNumber", defaultValue = USER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @Parameter(description = "Choose a page size in the search")
            @RequestParam(value = "pageSize", defaultValue = USER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort the answer by a field")
            @RequestParam(value = "sortBy", defaultValue = USER_DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort the answer by a direction")
            @RequestParam(value = "sortDir", defaultValue = USER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateRole(userEmail);
        return new ResponseEntity<>(this.userService.findAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Get a User by dni")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_USER_DNI)
    ResponseEntity<UserDTO> getByDni(
            @Parameter(description = "User DNI to search")
            @PathVariable("dni") String dni
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateUserRequestByDni(dni, userEmail);
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.getByDni(dni)), HttpStatus.OK);
    }

    @Operation(summary = "Get a User by email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_USER_EMAIL)
    ResponseEntity<UserDTO> getByEmail(
            @Parameter(description = "User email to search")
            @PathVariable("email") String email
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateUserRequestByEmail(email, userEmail);
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.getByEmail(email)), HttpStatus.OK);
    }

    @Operation(summary = "Update a User by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content
            )
    })
    @PutMapping(ENDPOINT_USER_ID)
    ResponseEntity<UserDTO> updateOne(
            @Parameter(description = "User ID to be updated")
            @PathVariable("id") Long id,
            @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateUserRequestById(id, userEmail);
        if(bindingResult.hasErrors()){
            throw new UserBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.updateOne(id, userDTO)), HttpStatus.OK);
    }

    @Operation(summary = "Delete a User by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User Deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content
            )
    })
    @DeleteMapping(ENDPOINT_USER_ID)
    ResponseEntity<String> deleteOne(
            @Parameter(description = "User ID to be deleted")
            @PathVariable("id") Long id
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.userService.validateUserRequestById(id, userEmail);
        this.userService.deleteOne(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
