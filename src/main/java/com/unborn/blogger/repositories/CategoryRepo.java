package com.unborn.blogger.repositories;

import com.unborn.blogger.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
