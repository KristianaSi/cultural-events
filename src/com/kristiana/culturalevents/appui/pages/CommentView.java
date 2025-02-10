package com.kristiana.culturalevents.appui.pages;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.PageFactory;
import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommentView implements Renderable {

    private final ServiceFactory serviceFactory;
    PageFactory pageFactory;
    Scanner scanner = new Scanner(System.in);

    public CommentView(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.pageFactory = PageFactory.getInstance(serviceFactory);
    }

    void process() throws IOException {
        CommentService commentService = serviceFactory.getCommentService();
        AuthService authService = serviceFactory.getAuthService();

        User currentUser = authService.getUser();

        Set<Comment> allCommentsSet = commentService.getAllByAuthor(currentUser.getUsername());
        List<Comment> comments = new ArrayList<>(allCommentsSet);

        if (comments.isEmpty()) {
            System.out.println("У вас ще нема коментарів.");
            return;
        }
        while (true) {
            printRed("\n~~~ Ваші коментарі ~~~");
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, comment.getCustomEvent().getName(),
                    comment.getBody());
            }

            System.out.println("Виберіть номер коментаря для перегляду (0 для повернення): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректний номер.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Повернення до головного меню...");
                break;
            }
            if (choice > 0 && choice <= comments.size()) {
                Comment selectedComment = comments.get(choice - 1);
                viewComment(selectedComment);
            } else {
                System.out.println("Невірний номер, спробуйте ще раз.");
            }
        }
    }

    private void viewComment(Comment comment) {
        printYellow("\n~~~ Деталі коментаря ~~~");
        System.out.println("Автор: " + comment.getAuthor().getUsername());
        System.out.println("Коментар:\n" + comment.getBody());
        System.out.println("Створено о: " + comment.getCreatedAt().toString());
        System.out.println("Оновлено о: " + comment.getUpdatedAt().toString());
    }

    @Override
    public void render() throws IOException {
        process();
    }
}
