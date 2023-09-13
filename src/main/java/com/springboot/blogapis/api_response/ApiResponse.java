package com.springboot.blogapis.api_response;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
    public Boolean status;
    public String message;
}
