package com.Vaku.Vaku.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmployeeRequest {
    private String persEmail;
    private String persPassword;
}
