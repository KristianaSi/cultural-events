package com.kristiana.culturalevents.persitance.entity;

import com.kristiana.culturalevents.persitance.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

    private final String password;
    private final LocalDate birthday;
    private final Role role;
    private String email;
    private String username;

    public User(UUID id, String password, String email, LocalDate birthday, String username,
        Role role) {
        super(id);
        this.password = validatedPassword(password);
        setEmail(email);
        this.birthday = validatedBirthday(birthday);
        setUsername(username);
        this.role = role;

        if (this.isValid()) {
            throw new EntityArgumentException(errors);
        }
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        final String templateName = "електронної пошти";

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        var pattern = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!pattern.matcher(email).matches()) {
            errors.add("Поле %s має бути валідною електронною поштою.".formatted(templateName));
        }

        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

        this.username = username;
    }


    private LocalDate validatedBirthday(LocalDate birthday) {
        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            errors.add("Дата народження не може бути в майбутньому або пустою.");
        }

        return birthday;
    }

    private String validatedPassword(String password) {
        final String templateName = "пароля";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (password.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 8));
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        if (!pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName));
        }

        return password;
    }

    @Override
    public String toString() {
        return "User{" +
            "password='" + password + '\'' +
            ", birthday=" + birthday +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", id=" + id +
            '}';
    }

    public enum Role {
        ADMIN("admin", Map.of(
            EntityName.COMMENT, new Permission(true, true, true, true),
            EntityName.EVENT, new Permission(true, true, true, true),
            EntityName.LOCATION, new Permission(true, true, true, true),
            EntityName.USER, new Permission(true, true, true, true))),
        GENERAL("general", Map.of(
            EntityName.COMMENT, new Permission(true, true, true, true),
            EntityName.EVENT, new Permission(true, false, true, true),
            EntityName.LOCATION, new Permission(false, false, false, true),
            EntityName.USER, new Permission(false, false, false, false)));

        private final String name;
        private final Map<EntityName, Permission> permissions;

        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        public String getName() {
            return name;
        }

        public Map<EntityName, Permission> getPermissions() {
            return permissions;
        }

        public enum EntityName {COMMENT, EVENT, LOCATION, USER}

        public record Permission(boolean canAdd, boolean canEdit, boolean canDelete,
                                 boolean canRead) {

        }
    }
}
