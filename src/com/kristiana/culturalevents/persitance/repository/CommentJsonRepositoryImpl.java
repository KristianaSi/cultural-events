package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.CommentRepository;
import java.util.Set;
import java.util.stream.Collectors;

final class CommentJsonRepositoryImpl
    extends GenericJsonRepository<Comment>
    implements CommentRepository {

    CommentJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.COMMENTS.getPath(), TypeToken
            .getParameterized(Set.class, Comment.class)
            .getType());
    }

    @Override
    public Set<Comment> findAllByAuthor(User author) {
        return entities.stream().filter(c -> c.getAuthor().equals(author))
            .collect(Collectors.toSet());
    }
}