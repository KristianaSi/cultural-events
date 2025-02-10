package com.kristiana.culturalevents.domain.contract;

import com.kristiana.culturalevents.domain.Service;
import com.kristiana.culturalevents.domain.dto.CommentAddDto;
import com.kristiana.culturalevents.domain.dto.CommentUpdateDto;
import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CommentService extends Service<Comment> {

    Set<Comment> getAllByAuthor(String author);

    List<Comment> getAllByEvent(CustomEvent event);

    Comment add(CommentAddDto commentAddDto);

    void update(CommentUpdateDto commentUpdateDto);

    void delete(UUID id);
}
