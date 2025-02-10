package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.dto.CommentAddDto;
import com.kristiana.culturalevents.domain.dto.CommentUpdateDto;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.exception.EntityArgumentException;
import com.kristiana.culturalevents.persitance.repository.contract.CommentRepository;
import com.kristiana.culturalevents.persitance.repository.contract.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;

final class CommentServiceImpl extends GenericService<Comment> implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<Comment> getAllByAuthor(String username) {
        User author = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Такого автора коментаря не існує"));
        return new TreeSet<>(commentRepository.findAllByAuthor(author));
    }

    @Override
    public List<Comment> getAllByEvent(CustomEvent event) {
        return commentRepository.findAllByEvent(event);
    }

    @Override
    public Comment add(CommentAddDto commentAddDto) {
        try {
            Comment comment = new Comment(
                commentAddDto.getId(),
                commentAddDto.createdAt(),
                commentAddDto.body(),
                commentAddDto.customEvent(),
                commentAddDto.author(),
                commentAddDto.updatedAt()
            );

            commentRepository.add(comment);
            return comment;
        } catch (EntityArgumentException e) {
            throw new IllegalArgumentException(String.join(", ", e.getErrors()));
        }
    }

    @Override
    public void update(CommentUpdateDto commentUpdateDto) {
        Comment existingComment = get(commentUpdateDto.getId());

        existingComment.setBody(commentUpdateDto.body());
        existingComment.setUpdatedAt(commentUpdateDto.updatedAt());

        commentRepository.update(existingComment);
    }

    @Override
    public void delete(UUID commentId) {
        Comment commentToRemove = get(commentId);
        commentRepository.remove(commentToRemove);
    }

    @Override
    public Set<Comment> getAll() {
        return getAll(c -> true);
    }

    @Override
    public Set<Comment> getAll(Predicate<Comment> filter) {
        return new TreeSet<>(commentRepository.findAll(filter));
    }
}
