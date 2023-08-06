package com.aa.resume.data.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FormDataDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String subject;

    private String message;
}
