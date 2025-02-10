package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.CommentRepository;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<Comment> findAllByEvent(CustomEvent event) {
        return entities.stream()
            .filter(item -> item.getCustomEvent() != null && item.getCustomEvent().equals(event))
            .collect(Collectors.toList());
    }

    @Override
    public Comment add(Comment comment) {
        super.add(comment);

        JsonRepositoryFactory.getInstance().commit();
        return comment;
    }

    @Override
    public void update(Comment comment) {
        Optional<Comment> existingCollection = entities.stream()
            .filter(c -> c.getId().equals(comment.getId()))
            .findFirst();

        if (existingCollection.isPresent()) {
            super.update(comment);
        } else {
            throw new EntityNotFoundException("Коментар не існує.");
        }
        JsonRepositoryFactory.getInstance().commit();
    }

    @Override
    public boolean remove(Comment comment) {
        boolean removed = entities.remove(comment);
        if (removed) {
            JsonRepositoryFactory.getInstance().commit();
        }
        return removed;
    }
}