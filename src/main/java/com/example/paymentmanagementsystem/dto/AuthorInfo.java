package com.example.paymentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AuthorInfo {
    private final String fullName;
    private final String group;
    private final String university;
    private final String email;
    private final String experience;
    private final String projectStartDate;
    private final String projectEndDate;
}
