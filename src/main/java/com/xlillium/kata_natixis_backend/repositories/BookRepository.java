package com.xlillium.kata_natixis_backend.repositories;

import com.xlillium.kata_natixis_backend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
