package com.sagor.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sagor.dto.RestaurantDto;
import com.sagor.model.Address;
import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.repository.AddressRepository;
import com.sagor.repository.RestaurentRepository;
import com.sagor.repository.UserRepository;
import com.sagor.request.CreateRestaurantRequest;
import com.sagor.service.RestuarantService;

@Service
public class RestaurantServiceImplement implements RestuarantService {

	private final RestaurentRepository restaurentRepository;
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	public RestaurantServiceImplement(RestaurentRepository restaurentRepository, AddressRepository addressRepository,
			UserRepository userRepository) {
		this.restaurentRepository = restaurentRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address = addressRepository.save(req.getAddress());
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCusineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		return restaurentRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);

		if (restaurant.getCuisineType() != null) {
			restaurant.setCuisineType(updatedRestaurant.getCusineType());
		}
		if (restaurant.getDescription() != null) {
			restaurant.setDescription(updatedRestaurant.getDescription());
		}
		if (restaurant.getName() != null) {
			restaurant.setName(updatedRestaurant.getName());
		}
		return restaurentRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		restaurentRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		return restaurentRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keywords) {
		return restaurentRepository.findBySearchQuery(keywords);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		Optional<Restaurant> opt = restaurentRepository.findById(id);
		if (opt.isEmpty()) {
			throw new Exception("Restaurant not found with id" + id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant = restaurentRepository.findByOwnerId(userId);
		if (restaurant == null) {
			throw new Exception("Restaurant not found with owner id: " + userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		RestaurantDto dto = new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);

//		if (user.getFavorites().contains(dto)) {
//			user.getFavorites().remove(dto);
//		} else {
//			user.getFavorites().add(dto);
//		}

		boolean isFavorited = false;
		List<RestaurantDto> favorities = user.getFavorites();
		for (RestaurantDto favorite : favorities) {
			if (favorite.getId().equals(restaurantId)) {
				isFavorited = true;
				break;
			}
		}

		if (isFavorited) {
			favorities.removeIf(favorite -> favorite.getId().equals(restaurantId));
		} else {
			favorities.add(dto);
		}
		userRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		Restaurant restaurant = findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurentRepository.save(restaurant);
	}

}
