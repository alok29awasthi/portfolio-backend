package com.aa.resume.data.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormDataDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String subject;

    private String message;
}
