package com.lord.arbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.IngredientMixDto;
import com.lord.arbam.mapper.IngredientMixMapper;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.service.IngredientMixService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/ingredient_mixes")
@RequiredArgsConstructor
public class IngredientMixController {
	
	@Autowired
	private final IngredientMixService ingredientMixService;
	
	private static final Gson gson = new Gson();
	
	
	@GetMapping("/all_by_product_id")
	ResponseEntity<List<IngredientMixDto>> findMixesByProductId(@RequestParam("productId")Long productId){
		List<IngredientMix> mixes = ingredientMixService.findByProductId(productId);
		List<IngredientMixDto> mixesDto = IngredientMixMapper.INSTANCE.toProductsMixDto(mixes);
		return new ResponseEntity<List<IngredientMixDto>>(mixesDto,HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<String> createMix(@RequestBody IngredientMixDto ingredientMixDto,@RequestParam("productId") Long productId){
		IngredientMix mix = IngredientMixMapper.INSTANCE.toProductMix(ingredientMixDto);
		IngredientMix savedMix = ingredientMixService.saveIngredientMix(mix);
		IngredientMixDto savedMixDto = IngredientMixMapper.INSTANCE.toProductMixDto(savedMix);
		return new ResponseEntity<String>(gson.toJson("Receta actualizada, se agrego: " + savedMixDto.getIngredientName()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/")
	ResponseEntity<String> deleteIngredientFromMix(@RequestParam("ingredientId")Long ingredientId){
		ingredientMixService.deleteIngredientMixById(ingredientId);
		return new ResponseEntity<String>("Se elimino de la receta",HttpStatus.OK);
	}

}
