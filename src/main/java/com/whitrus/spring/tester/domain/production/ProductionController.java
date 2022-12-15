package com.whitrus.spring.tester.domain.production;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.whitrus.spring.tester.domain.production.model.dto.ProductionDTO;
import com.whitrus.spring.tester.domain.validation.EntityId;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/productions")
@RequiredArgsConstructor
public class ProductionController
{
	private final ProductionService productionService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<ProductionDTO> findAllProductions(@RequestParam(defaultValue = "basic") String view, Pageable pageable)
	{
		return productionService.findAllProductionsAsDTO(pageable);
	}
	
//	@GetMapping("/count")
//	@ResponseStatus(OK)
//	public ProductionCountDTO countProductions(@Valid ProductionFilter productionFilter, @RequestParam(defaultValue = "basic") String view)
//	{
//		if (view.equals("byType"))
//		{
//			return productionService.countProductionsByType(productionFilter);
//		}
//		
//		return productionService.countProductions(productionFilter);
//	}

	@GetMapping("/{productionId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductionDTO findProductionById(@PathVariable @EntityId Long productionId, @RequestParam(defaultValue = "basic") String view)
	{
		if (view.equals("details"))
		{
			return productionService.findProductionWithDetailsByIdAsDTO(productionId);
		}

		return productionService.findProductionByIdAsDTO(productionId);
	}

	
	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity<ProductionDTO> insertNewProduction(@RequestParam(defaultValue = "basic") String view)
	{
		Long productionId = productionService.insertNewProduction();
		ProductionDTO productionDTO = findProductionById(productionId, view);

		URI uri = linkTo(methodOn(ProductionController.class).findProductionById(productionId, view)).toUri();

		return ResponseEntity.created(uri).body(productionDTO);
	}
	
	
	@PostMapping("/withDoc")
	@ResponseStatus(CREATED)
	public ResponseEntity<ProductionDTO> insertWithDoc(@RequestParam(defaultValue = "basic") String view)
	{
		Long productionId = productionService.insertNewProductionWithDoc();
		ProductionDTO productionDTO = findProductionById(productionId, view);

		URI uri = linkTo(methodOn(ProductionController.class).findProductionById(productionId, view)).toUri();

		return ResponseEntity.created(uri).body(productionDTO);
	}
	
//	@PostMapping
//	@ResponseStatus(CREATED)
//	public ResponseEntity<ProductionDTO> insertNewProduction(@NotNull @Valid @RequestBody ProductionInsertDTO productionInsertDTO, @RequestParam(defaultValue = "basic") String view)
//	{
//		Long productionId = productionService.insertNewProduction(productionInsertDTO);
//		ProductionDTO productionDTO = findProductionById(productionId, view);
//
//		URI uri = linkTo(methodOn(ProductionController.class).findProductionById(productionId, view)).toUri();
//
//		return ResponseEntity.created(uri).body(productionDTO);
//	}

//	@PatchMapping("/{productionId}")
//	@ResponseStatus(OK)
//	public ProductionDTO updateProduction(@PathVariable @EntityId Long productionId, @NotNull @Valid @RequestBody ProductionUpdateDTO productionUpdateDTO, @RequestParam(defaultValue = "basic") String view)
//	{
//		productionService.updateProduction(productionId, productionUpdateDTO);
//
//		return findProductionById(productionId, view);
//	}
//
//	@DeleteMapping("/{productionId}")
//	@ResponseStatus(NO_CONTENT)
//	public void deleteInstitutionalProduction(@PathVariable @EntityId Long productionId)
//	{
//		productionService.deleteInstitutionalProduction(productionId);
//	}
//
//	@GetMapping("/lattes/{lattesId}")
//	@ResponseStatus(OK)
//	public ProductionDTO findProductionByLattesId(@PathVariable @ProductionLattesId String lattesId, @RequestParam(defaultValue = "basic") String view)
//	{
//		if (view.equals("details"))
//		{
//			return productionService.findProductionWithDetailsByLattesIdAsDTO(lattesId);
//		}
//
//		return productionService.findProductionByLattesIdAsDTO(lattesId);
//	}

//	@PostMapping("/search")
//	@ResponseStatus(OK)
//	public Page<ProductionDTO> searchProductions(@NotNull @Valid @RequestBody ProductionSearch search, @RequestParam(defaultValue = "basic") String view, @SortDefault.SortDefaults({ @SortDefault(value = "author.id", direction = ASC), @SortDefault(value = "type.id", direction = ASC), @SortDefault(value = "year", direction = DESC), @SortDefault(value = "title", direction = ASC) }) Pageable pageable)
//	{
//		if (view.equals("details"))
//		{
//			return productionService.searchProductionsWithDetailsAsDTO(search, pageable);
//		}
//
//		return productionService.searchProductionsAsDTO(search, pageable);
//	}
}
