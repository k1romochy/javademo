package com.example.demo.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.name = :name")
    Optional<Item> findByName(@Param("name") String name);

    @Query("SELECT i FROM Item i WHERE i.price = :price")
    Optional<Item> findByPrice(@Param("price") String price);
}
