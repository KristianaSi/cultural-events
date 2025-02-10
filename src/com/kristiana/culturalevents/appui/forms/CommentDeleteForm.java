package com.kristiana.culturalevents.appui.forms;

import static com.kristiana.culturalevents.appui.PrintColor.printRed;

import com.kristiana.culturalevents.appui.Renderable;
import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.impl.ServiceFactory;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.JsonRepositoryFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommentDeleteForm implements Renderable {

    private final ServiceFactory serviceFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CommentDeleteForm(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
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

        System.out.println("Доступні коментарі для видалення:");
        for (int i = 0; i < comments.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, comments.get(i).getBody());
        }
        System.out.print("Виберіть номер коментаря для видалення: ");
        int itemChoice = Integer.parseInt(reader.readLine());
        Comment comment = comments.get(itemChoice - 1);

        System.out.print("Ви впевнені, що хочете видалити цей коментар? (+/-): ");
        String confirmation = reader.readLine();
        if (confirmation.equalsIgnoreCase("+")) {
            commentService.delete(comment.getId());
            JsonRepositoryFactory.getInstance().commit();
            System.out.println("Коментар успішно видалено!");
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    @Override
    public void render() throws IOException {
        printRed("\n~~~ Видалення коментаря ~~~");
        process();
    }
}
