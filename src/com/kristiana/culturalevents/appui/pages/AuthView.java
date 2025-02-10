package com.kristiana.culturalevents.appui.pages;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.pages.AuthView.AuthMenu.EXIT;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.SignUpService;
import com.kristiana.culturalevents.domain.dto.UserAddDto;
import com.kristiana.culturalevents.domain.exception.AuthException;
import com.kristiana.culturalevents.domain.exception.SignUpException;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class AuthView implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public AuthView(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    private void process(AuthMenu selectedItem) throws IOException {
        AuthService authService = serviceFactory.getAuthService();
        SignUpService signUpService = serviceFactory.getSignUpService();

        switch (selectedItem) {
            case SIGN_IN -> {
                System.out.print("Впишіть ваш логін: ");
                String username = reader.readLine();

                System.out.print("Впишіть ваш пароль: ");
                String password = reader.readLine();

                try {
                    boolean authenticate = authService.authenticate(username, password);
                    if (authenticate) {
                        User user = authService.getUser();

                        new MenuView(user.getRole(), serviceFactory).render();
                    } else {
                        System.err.println("Аутентифікація неуспішна.");
                    }
                } catch (AuthException e) {
                    System.err.println("Помилка аутентифікації: " + e.getMessage());
                }
            }
            case SIGN_UP -> {

                System.out.print("Впишіть ваш логін: ");
                String username = reader.readLine();

                System.out.print("Впишіть ваш пароль: ");
                String password = reader.readLine();

                System.out.print("Вкажіть вашу електронну пошту: ");
                String email = reader.readLine();

                LocalDate birthday = null;
                while (birthday == null) {
                    System.out.print("Вкажіть дату народження у форматі yyyy-mm-dd: ");
                    String input = reader.readLine().trim();

                    try {
                        birthday = LocalDate.parse(input);
                        if (birthday.isAfter(LocalDate.now())) {
                            System.out.println(
                                "Дата народження не може бути в майбутньому. Спробуйте ще раз.");
                            birthday = null;
                        }
                    } catch (DateTimeParseException | NullPointerException e) {
                        System.out.println(
                            "Неправильний формат дати. Введіть ще раз у форматі yyyy-mm-dd.");
                    }
                }

                try {
                    UserAddDto userAddDto = new UserAddDto(
                        UUID.randomUUID(),
                        username,
                        password,
                        email,
                        birthday
                    );

                    signUpService.signUp(userAddDto);
                    JsonRepositoryFactory.getInstance().commit();

                    System.out.println("Реєстрація успішна!");
                    boolean authenticate = authService.authenticate(username, password);
                    if (authenticate) {
                        User user = authService.getUser();

                        new MenuView(user.getRole(), serviceFactory).render();
                    } else {
                        System.out.println("Помилка.");
                    }
                } catch (SignUpException e) {
                    System.err.println("Помилка реєстрації: \n" + e.getMessage());
                }
            }
            case EXIT -> {
                System.out.println("Вихід.");
            }
            default -> {
                System.err.println("Помилка.");
            }
        }
    }

    @Override
    public void render() throws IOException {
        while (true) {
            printRed("\n\n~~~ Меню ~~~");
            System.out.println("1. " + AuthMenu.SIGN_IN.getName());
            System.out.println("2. " + AuthMenu.SIGN_UP.getName());
            System.out.println("3. " + EXIT.getName());
            System.out.print("Зробіть вибір: ");

            String choice = reader.readLine();
            AuthMenu selectedItem;

            try {
                selectedItem = switch (choice) {
                    case "1" -> AuthMenu.SIGN_IN;
                    case "2" -> AuthMenu.SIGN_UP;
                    case "3" -> EXIT;
                    default -> throw new IllegalArgumentException("Неправильний вибір");
                };

                if (selectedItem == EXIT) {
                    process(EXIT);
                    System.exit(0);
                    break;
                }

                process(selectedItem);
            } catch (IllegalArgumentException e) {
                System.err.println("Помилка:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    enum AuthMenu {
        SIGN_IN("Авторизація"),
        SIGN_UP("Створити обліковий аккаунт"),
        EXIT("Вихід");

        private final String name;

        AuthMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
