package com.orderpicker.user.infrastructure.response;

import com.orderpicker.user.infrastructure.dto.UserDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private List<UserDTO> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private  boolean lastOne;
}
