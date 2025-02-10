package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printPurple;
import static com.kristiana.culturalevents.appui.PrintColor.printRed;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.dto.CommentAddDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CommentAddForm implements Renderable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final ServiceFactory serviceFactory;

    public CommentAddForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public void process() throws IOException {
        CommentService commentService = serviceFactory.getCommentService();
        EventService eventService = serviceFactory.getEventService();
        AuthService authService = serviceFactory.getAuthService();
        User currentUser = authService.getUser();

        Set<CustomEvent> allEventsSet = eventService.getAll(event -> true);
        List<CustomEvent> allEvents = new ArrayList<>(allEventsSet);

        CustomEvent event = null;

        if (allEvents.isEmpty()) {
            System.out.println("Немає культурних подій.");
            return;
        } else {
            printPurple("~~~ Культурні події ~~~");
            for (int i = 0; i < allEvents.size(); i++) {
                CustomEvent ev = allEvents.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, ev.getName(), ev.getDate());
            }

            while (true) {
                System.out.println("Виберіть номер події для коментаря (0 для повернення): ");
                try {
                    int choice = Integer.parseInt(reader.readLine());
                    if (choice == 0) {
                        System.out.println("Повернення до головного меню...");
                        return;
                    }
                    if (choice > 0 && choice <= allEvents.size()) {
                        event = allEvents.get(choice - 1);
                        break;
                    } else {
                        System.out.println("Невірний номер, спробуйте ще раз.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Введіть коректне число.");
                }
            }
        }

        String body;
        do {
            System.out.println("Впишіть ваш коментар: ");
            body = reader.readLine().trim();
            if (body.isEmpty()) {
                System.out.println("Коментар не може бути порожнім.");
            }
        } while (body.isEmpty());

        try {
            CommentAddDto commentAddDto = new CommentAddDto(
                UUID.randomUUID(),
                LocalDateTime.now(),
                body,
                event,
                currentUser,
                LocalDateTime.now()
            );

            commentService.add(commentAddDto);
            JsonRepositoryFactory.getInstance().commit();

            System.out.println("Коментар успішно створено!");
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Створення нового коментаря ~~~");
        try {
            process();
        } catch (IOException e) {
            System.err.println("Помилка при введенні даних: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
