package com.kristiana.culturalevents.persitance.repository.contract;

import com.kristiana.culturalevents.persitance.entity.Comment;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.util.Set;

public interface CommentRepository extends Repository<Comment> {

    Set<Comment> findAllByAuthor(User author);
}
