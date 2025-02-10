package com.kristiana.culturalevents.appui.pages;

import static com.kristiana.culturalevents.appui.PrintColor.printGreen;
import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventView implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public EventView(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        EventService eventService = serviceFactory.getEventService();
        Set<CustomEvent> eventsSet = eventService.getAll();
        List<CustomEvent> events = new ArrayList<>(eventsSet);

        if (events.isEmpty()) {
            System.out.println("Немає доступних подій.");
            return;
        }

        printRed("\nСписок подій:");
        for (int i = 0; i < events.size(); i++) {
            CustomEvent event = events.get(i);
            System.out.printf("%d. %s - %s%n", i + 1, event.getName(), event.getDate());
        }

        System.out.print("Введіть номер події для перегляду деталей (0 для виходу): ");
        try {
            int choice = Integer.parseInt(reader.readLine());
            if (choice == 0) {
                System.out.println("Вихід з перегляду подій.");
                return;
            }
            if (choice < 1 || choice > events.size()) {
                System.out.println("Некоректний вибір.");
                return;
            }
            viewSingleEvent(events.get(choice - 1));
        } catch (NumberFormatException e) {
            System.out.println("Некоректний ввід. Спробуйте ще раз.");
        }
    }

    void viewSingleEvent(CustomEvent event) {
        CommentService commentService = serviceFactory.getCommentService();
        printYellow("\nДеталі події:");
        System.out.println("Назва: " + event.getName());
        System.out.println("Дата: " + event.getDate());
        System.out.println("Опис: " + event.getDescription());
        System.out.println("Учасники: " + event.getParticipators());
        System.out.println(
            "Локація: " + event.getEventLocation().getName() + ", " + event.getEventLocation()
                .getDescription());

        printGreen("\nКоментарі до події:");
        List<Comment> comments = commentService.getAllByEvent(event);
        if (comments.isEmpty()) {
            System.out.println("Немає коментарів.");
        } else {
            for (Comment comment : comments) {
                System.out.printf("- %s: %s%n", comment.getAuthor().getUsername(),
                    comment.getBody());
            }
        }
    }

    @Override
    public void render() throws IOException {
        process();
    }
}
