package com.zonions.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zonions.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

  public Restaurant findByStatus(String status);

  public Optional<Restaurant> findByNameAndId(String name, int id);


}
