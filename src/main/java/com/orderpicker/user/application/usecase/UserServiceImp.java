package com.orderpicker.user.application.usecase;

import com.orderpicker.exception.BadRequestException;
import com.orderpicker.exception.NotFoundException;
import com.orderpicker.rol.Role;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import com.orderpicker.user.infrastructure.response.UserResponse;
import com.orderpicker.user.infrastructure.service.EncryptService;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class UserServiceImp implements UserService {

    private final @NonNull UserRepository userRepository;

    private final @NonNull MapperUser mapperUser;

    private final @NonNull EncryptService encryptService;

    @Override
    public User save(UserDTO userDTO) {
        if(userDTO.getRole().equals(Role.ADMIN)){
            throw new BadRequestException("Cannot be %s. Allowed roles are %s and %s".formatted(Role.ADMIN, Role.EMPLOYEE, Role.USER));
        }
        this.findByDni(userDTO.getDni());
        this.findByEmail(userDTO.getEmail());
        this.encryptPassword(userDTO);

        return this.userRepository.save(this.mapperUser.mapUser(userDTO));
    }

    @Override
    public User getById(Long id) {
        Optional<User> userFound = this.userRepository.findById(id);
        if(userFound.isEmpty()){
            throw new NotFoundException("The user with id %s doesn't exist".formatted(id));
        }
        return userFound.get();
    }

    @Override
    public UserResponse findAll(int numberPage, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        Page<User> usersFound =this.userRepository.findAll(pageable);

        List<UserDTO> users = usersFound.getContent().stream().map(this.mapperUser::mapUserDTO).toList();

        return UserResponse.builder()
                .content(users)
                .pageNumber(usersFound.getNumber())
                .pageSize(usersFound.getSize())
                .totalElements(usersFound.getTotalElements())
                .totalPages(usersFound.getTotalPages())
                .lastOne(usersFound.isLast())
                .build();
    }

    @Override
    public User getByDni(String dni) {
        Optional<User> userFound = this.userRepository.findByDni(dni);
        if(userFound.isEmpty()){
            throw new NotFoundException("The user with dni %s doesn't exist".formatted(dni));
        }
        return userFound.get();
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> userFound = this.userRepository.findByEmail(email);
        if(userFound.isEmpty()){
            throw new NotFoundException("User with email %s doesn't exist".formatted(email));
        }
        return userFound.get();
    }

    @Override
    public User updateOne(Long id, UserDTO userDTO) {
        User userFound = this.getById(id);

        if(!userFound.getRole().equals(Role.ADMIN) && !userDTO.getRole().equals(userFound.getRole())){
                throw new NotFoundException("Role not found");
        }

        this.encryptPassword(userDTO);

        User userUpdated = this.mapperUser.updateUser(userFound, userDTO);

        return this.userRepository.save(userUpdated);
    }

    @Override
    public void deleteOne(Long id) {
        this.getById(id);
        this.userRepository.deleteById(id);
    }

    @Override
    public void validateUserRequestById(Long id, String userEmail) {
        User userFound = this.getByEmail(userEmail);
        if(userFound.getRole() != Role.ADMIN && !id.equals(userFound.getId())){
                throw new NotFoundException("User with id %s not found".formatted(id));
        }
    }

    @Override
    public void validateUserRequestByDni(String dni, String userEmail) {
        User userFound = this.getByEmail(userEmail);
        if(!userFound.getRole().equals(Role.ADMIN) && !dni.equals(userFound.getDni())){
                throw new NotFoundException("User with dni %s not found".formatted(dni));
        }
    }

    @Override
    public void validateUserRequestByEmail(String email, String userEmail) {
        User userFound = this.getByEmail(userEmail);
        if(!userFound.getRole().equals(Role.ADMIN) && !email.equals(userFound.getEmail())){
                throw new NotFoundException("User with email %s not found".formatted(email));
        }
    }

    @Override
    public void validateRole(String userEmail) {
        Optional<User> userFound = this.userRepository.findByEmail(userEmail);
        if(userFound.isPresent() && !userFound.get().getRole().equals(Role.ADMIN)){
            throw new NotFoundException("Request not found");
        }
    }

    protected void findByDni(String dni){
        Optional<User> userFound = this.userRepository.findByDni(dni);
        if(userFound.isPresent()){
            throw new BadRequestException("The user with dni %s already exist".formatted(dni));
        }
    }

    protected void findByEmail(String email){
        Optional<User> userFound = this.userRepository.findByEmail(email);
        if(userFound.isPresent()){
            throw new BadRequestException("The user with email %s already exist".formatted(email));
        }
    }

    protected void encryptPassword(UserDTO userDTO){
        String hashPass = this.encryptService.encryptPassword(userDTO.getPassword());
        userDTO.setPassword(hashPass);
    }
}
