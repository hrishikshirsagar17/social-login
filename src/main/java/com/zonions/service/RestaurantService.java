package com.zonions.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.zonions.model.Restaurant;
import com.zonions.repository.RestaurantRepository;

@Service
public class RestaurantService {
  @Autowired
  private RestaurantRepository restaurantRepository;
  String status = "open";
  LocalTime time = LocalTime.now();
  String updatedTime = time.toString();

  // Service To add restaurant object
  public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
    try {

      Restaurant _restaurant =
          restaurantRepository.save(new Restaurant(restaurant.getRestaurantName(),
              restaurant.getAddress(), restaurant.getPhoneNo(), restaurant.getOpenTime(),
              restaurant.getCloseTime(), status, updatedTime));
      return new ResponseEntity<>(_restaurant, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Service To add Image
  public String uploadImage(@RequestParam("file") MultipartFile file, @PathVariable int id) {

    Optional<Restaurant> data = restaurantRepository.findById(id);
    try {
      if (data.isPresent()) {
        Restaurant restaurant = data.get();
        restaurant.setName(file.getOriginalFilename());
        restaurant.setMimetype(file.getContentType());
        restaurant.setPic(file.getBytes());

        restaurantRepository.save(restaurant);

      }
      return "File uploaded successfully! -> filename = " + file.getOriginalFilename();
    } catch (Exception e) {
      return "FAIL! Maybe You had uploaded the file before or the file's size > 500KB";
    }

  }

  // Service to get all restaurant
  public List<Restaurant> getAllRestaurant() {
    return restaurantRepository.findAll();
  }

  // Service to get restaurant by id
  public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
    if (restaurant.isPresent()) {
      return new ResponseEntity<Restaurant>(restaurant.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Service to get restaurant
  public Restaurant getRestaurantByStatus(@PathVariable String status) {
    Restaurant data = restaurantRepository.findByStatus(status);
    return data;
  }

  public ResponseEntity<HttpStatus> deleteById(@PathVariable int id) {
    try {
      restaurantRepository.deleteById(id);
      return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id,
      @RequestBody Restaurant restaurant) {
    Optional<Restaurant> data = restaurantRepository.findById(id);
    if (data.isPresent()) {
      Restaurant rest = data.get();
      rest.setRestaurantName(restaurant.getRestaurantName());
      rest.setAddress(restaurant.getAddress());
      rest.setPhoneNo(restaurant.getPhoneNo());
      rest.setOpenTime(restaurant.getOpenTime());
      rest.setCloseTime(restaurant.getCloseTime());
      rest.setStatus(restaurant.getStatus());
      rest.setUpdatedTime(updatedTime);
      return new ResponseEntity<Restaurant>(restaurantRepository.save(rest), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public Restaurant changeStatus(@PathVariable int id, @PathVariable Restaurant restaurant) {
    System.out.println(restaurant);
    String st = "closed";
    String st1 = "open";

    Optional<Restaurant> data = restaurantRepository.findById(id);

    Restaurant rest = data.get();

    if (data.isPresent()) {

      if (restaurant.getStatus().equals("open")) {
        rest.setStatus(st);

      } else {
        rest.setStatus(st1);

      }
    }

    return restaurantRepository.save(rest);
  }

}
