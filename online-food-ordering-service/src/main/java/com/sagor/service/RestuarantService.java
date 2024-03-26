package com.sagor.service;

import java.util.List;

import com.sagor.dto.RestaurantDto;
import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.request.CreateRestaurantRequest;

public interface RestuarantService {

	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

	public void deleteRestaurant(Long restaurantId) throws Exception;

	public List<Restaurant> getAllRestaurant();

	public List<Restaurant> searchRestaurant(String keywords);

	public Restaurant findRestaurantById(Long id) throws Exception;

	public Restaurant getRestaurantByUserId(Long userId) throws Exception;

	public RestaurantDto addFavorites(Long restaurantId, User user) throws Exception;

	public Restaurant updateRestaurantStatus(Long id) throws Exception;

}
