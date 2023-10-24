package com.library.app.patron.repository;

import com.library.app.patron.repository.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
