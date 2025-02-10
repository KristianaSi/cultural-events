package com.kristiana.culturalevents.appui.pages;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.pages.MenuView.MainMenu.EXIT;

import com.kristiana.culturalevents.appui.PageFactory;
import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.User.Role;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuView implements Renderable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private final Role userRole;
    ServiceFactory serviceFactory;
    PageFactory pageFactory;

    public MenuView(Role userRole, ServiceFactory serviceFactory) {
        this.userRole = userRole;
        this.serviceFactory = serviceFactory;
        this.pageFactory = PageFactory.getInstance(serviceFactory);
    }

    private void process(MainMenu selectedItem) throws IOException {
        switch (selectedItem) {
            case MainMenu.VIEW_EVENTS -> pageFactory.createEventView().render();

            case MainMenu.ADD_COMMENT -> pageFactory.createCommentAddForm().render();

            case MainMenu.EDIT_COMMENT -> pageFactory.createCommentUpdateForm().render();

            case MainMenu.VIEW_COMMENTS -> pageFactory.createCommentView().render();

            case MainMenu.DELETE_COMMENT -> pageFactory.createCommentDeleteForm().render();

            case MainMenu.ADD_EVENT -> pageFactory.createEventAddForm().render();

            case MainMenu.EDIT_EVENT -> pageFactory.createEventUpdateForm().render();

            case MainMenu.DELETE_EVENT -> pageFactory.createDeleteEventForm().render();

            case MainMenu.ADD_LOCATION -> pageFactory.createLocationAddForm().render();

            case MainMenu.EDIT_LOCATION -> pageFactory.createLocationUpdateForm().render();

            case MainMenu.DELETE_LOCATION -> pageFactory.createDeleteLocationForm().render();

            case MainMenu.LOG_OUT -> {
                System.out.println("Вихід з акаунту...");
                serviceFactory.getAuthService().logout();
                pageFactory.createAuthView().render();
            }
            case EXIT -> System.out.println("Вихід з програми...");
            default -> System.out.println("Неправильний вибір");
        }
    }

    @Override
    public void render() throws IOException {
        while (true) {
            printRed("\n\n~~~ Головне меню ~~~");
            System.out.println("1. " + MainMenu.VIEW_EVENTS.getName());
            System.out.println("2. " + MainMenu.ADD_COMMENT.getName());
            System.out.println("3. " + MainMenu.EDIT_COMMENT.getName());
            System.out.println("4. " + MainMenu.VIEW_COMMENTS.getName());
            System.out.println("5. " + MainMenu.DELETE_COMMENT.getName());
            System.out.println("6. " + MainMenu.LOG_OUT.getName());
            if (userRole == Role.ADMIN) {
                System.out.println("7. " + MainMenu.ADD_EVENT.getName());
                System.out.println("8. " + MainMenu.EDIT_EVENT.getName());
                System.out.println("9. " + MainMenu.DELETE_EVENT.getName());
                System.out.println("10. " + MainMenu.ADD_LOCATION.getName());
                System.out.println("11. " + MainMenu.EDIT_LOCATION.getName());
                System.out.println("12. " + MainMenu.DELETE_LOCATION.getName());
            }
            System.out.println("0. " + EXIT.getName());
            System.out.print("Зробіть вибір: ");

            String choice = reader.readLine();
            MainMenu selectedItem;

            try {
                selectedItem = switch (choice) {
                    case "1" -> MainMenu.VIEW_EVENTS;
                    case "2" -> MainMenu.ADD_COMMENT;
                    case "3" -> MainMenu.EDIT_COMMENT;
                    case "4" -> MainMenu.VIEW_COMMENTS;
                    case "5" -> MainMenu.DELETE_COMMENT;
                    case "6" -> MainMenu.LOG_OUT;
                    case "7" -> MainMenu.ADD_EVENT;
                    case "8" -> MainMenu.EDIT_EVENT;
                    case "9" -> MainMenu.DELETE_EVENT;
                    case "10" -> MainMenu.ADD_LOCATION;
                    case "11" -> MainMenu.EDIT_LOCATION;
                    case "12" -> MainMenu.DELETE_LOCATION;
                    case "0" -> EXIT;
                    default -> throw new IllegalArgumentException("Неправильний вибір");
                };

                if (selectedItem == EXIT) {
                    process(EXIT);
                    System.exit(0);
                    break;
                }

                process(selectedItem);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    enum MainMenu {
        VIEW_EVENTS("Культурні події"),
        ADD_EVENT("Додати подію"),
        EDIT_EVENT("Редагувати подію"),
        DELETE_EVENT("Видалити подію"),
        VIEW_COMMENTS("Коментарі"),
        ADD_COMMENT("Додати коментар"),
        EDIT_COMMENT("Редагувати коментар"),
        DELETE_COMMENT("Видалити коментар"),
        ADD_LOCATION("Додати локацію"),
        EDIT_LOCATION("Редагувати локацію"),
        DELETE_LOCATION("Видалити локацію"),
        LOG_OUT("Вийти з акаунту"),
        EXIT("Вихід");

        private final String name;

        MainMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
