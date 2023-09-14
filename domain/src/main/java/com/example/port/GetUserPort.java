package com.example.port;

import com.example.model.User;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface GetUserPort {
    Page<User> list(String query, int page);
    Set<User> listByName(Set<String> name);
}
