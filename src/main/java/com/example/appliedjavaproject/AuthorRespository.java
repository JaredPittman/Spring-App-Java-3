package com.example.appliedjavaproject;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRespository extends CrudRepository<Author, Integer> {

        Author findByAuthorID(int authorID);

}
