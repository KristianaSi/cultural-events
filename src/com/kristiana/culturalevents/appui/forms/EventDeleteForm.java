package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EventDeleteForm implements Renderable {

    private final ServiceFactory serviceFactory;

    public EventDeleteForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        EventService eventService = serviceFactory.getEventService();
        CommentService commentService = serviceFactory.getCommentService();

        Set<CustomEvent> eventsSet = eventService.getAll();
        ArrayList<CustomEvent> events = new ArrayList<>(eventsSet);

        if (events.isEmpty()) {
            System.out.println("Нема доступних подій.");
        } else {
            printYellow("Доступні події для видалення:");
            for (int i = 0; i < events.size(); i++) {
                CustomEvent event = events.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, event.getName(),
                    event.getDescription());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Виберіть номер події для видалення (0 для повернення): ");
            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Невірний формат введення.");
                return;
            }

            if (choice > 0 && choice <= events.size()) {
                CustomEvent selectedEvent = events.get(choice - 1);

                System.out.println(
                    "Увага! Видалення події призведе до видалення всіх її коментарів.");
                System.out.print("Ви впевнені, що хочете видалити цю подію? (+/-): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("-")) {
                    System.out.println("Видалення скасовано.");
                    return;
                }

                List<Comment> commentsToDelete = commentService.getAllByEvent(selectedEvent);
                for (Comment comment : commentsToDelete) {
                    commentService.delete(comment.getId());
                }

                eventService.delete(selectedEvent.getId());
                JsonRepositoryFactory.getInstance().commit();

                System.out.println("Подію та всі її коментарі успішно видалено!");
            } else if (choice == 0) {
                System.out.println("Повернення до головного меню...");
            } else {
                System.out.println("Невірний номер, спробуйте ще раз.");
            }
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Видалення події ~~~");
        process();
    }
}
