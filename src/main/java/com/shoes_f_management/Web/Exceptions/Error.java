package com.shoes_f_management.Web.Exceptions;

public record Error(
    String type,
    String message
) {}