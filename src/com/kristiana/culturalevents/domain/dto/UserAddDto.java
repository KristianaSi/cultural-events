package com.kristiana.culturalevents.domain.dto;

import com.kristiana.culturalevents.domain.exception.SignUpException;
import com.kristiana.culturalevents.persitance.entity.Entity;
import com.kristiana.culturalevents.persitance.entity.ErrorTemplates;
import com.kristiana.culturalevents.persitance.entity.User.Role;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public final class UserAddDto extends Entity {

    private final String username;
    private final String rawPassword;
    private final String email;
    private final LocalDate birthday;
    private final Role role;

    public UserAddDto(UUID id,
        String username,
        String rawPassword,
        String email,
        LocalDate birthday) {
        super(id);
        this.username = validatedUsername(username);
        this.rawPassword = validatedPassword(rawPassword);
        this.email = validatedEmail(email);
        this.birthday = birthday;
        this.role = Role.GENERAL;

        if (!this.errors.isEmpty()) {
            throw new SignUpException(String.join("\n", errors));
        }
    }

    public String rawPassword() {
        return rawPassword;
    }

    private String validatedPassword(String rawPassword) {
        final String templateName = "паролю";

        if (rawPassword.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (rawPassword.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (rawPassword.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        if (!pattern.matcher(rawPassword).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
        }

        return rawPassword;
    }

    public String username() {
        return username;
    }

    public String validatedUsername(String username) {
        final String templateName = "логіну";

        if (username.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (username.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (username.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
        }
        var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (!pattern.matcher(username).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName));
        }

        return username;
    }

    public String email() {
        return email;
    }

    public String validatedEmail(String email) {
        final String templateName = "електронної пошти";

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        var pattern = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!pattern.matcher(email).matches()) {
            errors.add("Поле %s має бути валідною електронною поштою.".formatted(templateName));
        }

        return email;
    }

    public LocalDate birthday() {
        return birthday;
    }

    public Role role() {
        return role;
    }
}
