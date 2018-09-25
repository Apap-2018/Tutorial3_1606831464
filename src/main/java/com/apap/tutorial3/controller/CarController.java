package com.apap.tutorial3.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "brand", required = true) String brand,
					@RequestParam(value = "type", required = true) String type,
							@RequestParam(value = "price", required = true) Long price,
									@RequestParam(value = "amount", required = true) Integer amount) {
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
							}
	
	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		
		model.addAttribute("car", archive);
		return "view-car";
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	@RequestMapping("/car/view/{id}")
	public String viewById(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			String idReal = id.get();
			CarModel archive = mobilService.getCarDetail(idReal);
			if (archive == null) {
				return "error";
			}
			else {
				model.addAttribute("car", archive);
				return "view-car";
			}
		}
		else {
			return "error";
		}
	}	
	@RequestMapping("/car/update/{id}/amount/{amount}")
	public String addAmount(@PathVariable Optional<String> id, @PathVariable Optional<Integer> amount,
			Model model) {
		if (id.isPresent()) {
			String idReal = id.get();
			CarModel archive = mobilService.getCarDetail(idReal);
			if (archive == null) {
				return "error";
			}
			else {
				if(amount.isPresent()) {
					archive.setAmount(amount.get());
					return "updated-data";
				}
				else {
					return "updated-data";
				}
			}
		}
		else {
			return "error";
		}
	}
	@RequestMapping("/car/delete/{id}")
	public String deleteById(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			String idReal = id.get();
			CarModel archive = mobilService.getCarDetail(idReal);
			if (archive == null) {
				return "error";
			}
			else {
				List<CarModel> archiveList = mobilService.getCarList();
				archiveList.remove(archive);
				model.addAttribute("car", archive);
				return "deleted";
			}
		}
		else {
			return "error";
		}
	}	
	
}
