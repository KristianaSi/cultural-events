package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;
import static com.kristiana.culturalevents.appui.PrintColor.printYellow;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.dto.CommentUpdateDto;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommentUpdateForm implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CommentUpdateForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    void process() throws IOException {
        CommentService commentService = serviceFactory.getCommentService();
        AuthService authService = serviceFactory.getAuthService();

        User currentUser = authService.getUser();
        Set<Comment> allCommentsSet = commentService.getAllByAuthor(currentUser.getUsername());
        List<Comment> comments = new ArrayList<>(allCommentsSet);

        if (comments.isEmpty()) {
            System.out.println("У вас ще немає коментарів.");
        } else {
            printYellow("Доступні події для редагування коментаря:");
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, comment.getAuthor().getUsername(),
                    comment.getBody());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println(
                "Виберіть номер події для редагування коментаря (0 для повернення): ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice > 0 && choice <= comments.size()) {
                Comment selectedComment = comments.get(choice - 1);
                processCollectionEditing(selectedComment);
            } else if (choice == 0) {
                System.out.println("Повернення до головного меню...");
            } else {
                System.out.println("Невірний номер, спробуйте ще раз.");
            }
        }
    }

    private void processCollectionEditing(Comment comment) {
        try {
            System.out.println("Введіть оновлений коментар (залиште порожнім для відміни): ");
            String newBody = reader.readLine();
            if (!newBody.isEmpty()) {
                comment.setBody(newBody);
            }

            CommentUpdateDto updateDto = new CommentUpdateDto(
                comment.getId(),
                comment.getBody(),
                LocalDateTime.now()
            );

            CommentService commentService = serviceFactory.getCommentService();
            commentService.update(updateDto);
            JsonRepositoryFactory.getInstance().commit();

            System.out.println("Коментар успішно оновлено!");
        } catch (IOException e) {
            System.err.println("Помилка при введенні даних: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Редагування коментаря ~~~");
        process();
    }
}
