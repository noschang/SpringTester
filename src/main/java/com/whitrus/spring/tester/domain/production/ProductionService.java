package com.whitrus.spring.tester.domain.production;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.document.model.Document;
import com.whitrus.spring.tester.domain.document.model.DocumentOrigin;
import com.whitrus.spring.tester.domain.document.model.DocumentStatus;
import com.whitrus.spring.tester.domain.document.model.DocumentType;
import com.whitrus.spring.tester.domain.json.JsonData.AccessMode;
import com.whitrus.spring.tester.domain.production.exceptions.ProductionNotFoundException;
import com.whitrus.spring.tester.domain.production.model.Production;
import com.whitrus.spring.tester.domain.production.model.ProductionCategory;
import com.whitrus.spring.tester.domain.production.model.ProductionStatus;
import com.whitrus.spring.tester.domain.production.model.dto.ProductionDTO;
import com.whitrus.spring.tester.domain.user.UserRepository;
import com.whitrus.spring.tester.domain.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductionService
{
	@PersistenceContext
	private final EntityManager entityManager;
	
	private final UserRepository userRepository;
	private final ProductionRepository productionRepository;
//	private final ProductionTypeRepository productionTypeRepository;

	private final UserService userService;
//	private final ScoreComputationService scoreComputationService;

	@Transactional(readOnly = false)
	public Long insertNewProduction(){
		
		Production production = new Production();
		
		production.setAuthor(userRepository.findById(1L).orElseThrow());
		production.setCategory(ProductionCategory.LATTES);
		production.setCreationDate(new Date());
//		production.setData();
//		production.setDocumentationDate(new Date());
		production.setLattesId("6e3c8fbf2a33d282b0266aeb3c95bdaa");
		production.setModificationDate(new Date());
		production.setReference("Minha produção");
		production.setReferenceNormalized("MINHA PRODUCAO");
//		production.setReviewDate(new Date());
//		production.setReviewer(userRepository.findById(1L).orElseThrow());
		production.setStatus(ProductionStatus.UNREVIEWED);
		production.setTitle("Minha Produção");
		production.setYear(2021);
//		production.setDocumentationDate(new Date());
		
		production = productionRepository.save(production);
		final long prodId = production.getId();
		
//		return productionRepository.findProductionByIdAsDTO(prodId).orElseThrow();
		
		return prodId;
	}
	
	@Transactional(readOnly = false)
	public Long insertNewProductionWithDoc(){
		
		Document document = new Document();
		
		document.setCreationDate(new Date());
		document.setOrigin(DocumentOrigin.AUTHOR);
		document.setReviewDate(new Date());
		document.setReviewer(userRepository.findById(1L).orElseThrow());
		document.setStatus(DocumentStatus.ACCEPTED);
		document.setType(DocumentType.URL);
		ObjectNode data = document.getData().asObject(AccessMode.FOR_UPDATING);
		
		data.put("url", "https://www.google.com.br");
		
		Production production = new Production();
		
		production.setAuthor(userRepository.findById(1L).orElseThrow());
		production.setCategory(ProductionCategory.LATTES);
		production.setCreationDate(new Date());
//		production.setData();
		production.setDocumentationDate(new Date());
		production.setLattesId("6e3c8fbf2a33d282b0266aeb3c95bdaa");
		production.setModificationDate(new Date());
		production.setReference("Minha produção");
		production.setReferenceNormalized("MINHA PRODUCAO");
//		production.setReviewDate(new Date());
//		production.setReviewer(userRepository.findById(1L).orElseThrow());
		production.setStatus(ProductionStatus.UNDER_REVIEW);
		production.setTitle("Minha Produção");
		production.setYear(2021);
		production.addDocument(document);
		
		production = productionRepository.save(production);
		document = production.getDocuments().iterator().next();
		
		System.out.println("Document ID: " + document.getId());
		final long prodId = production.getId();
		
//		return productionRepository.findProductionByIdAsDTO(prodId).orElseThrow();
		
		return prodId;
	}

	@Transactional(readOnly = true)
	public Page<ProductionDTO> findAllProductionsAsDTO(Pageable pageable)
	{
		return productionRepository.findAllProductionsAsDTO(pageable);
	}
//
//	@Transactional(readOnly = true)
//	public Page<ProductionDTO> findAllProductionsWithDetailsAsDTO(ProductionFilter productionFilter, Pageable pageable)
//	{
//		var production = QProduction.production;
//		var p = production;
//
//		var view = new QProductionDetailsDTO(p.id, p.year, p.title, p.category, p.type.id, p.creationDate, p.reviewDate, p.documentationDate, p.modificationDate, p.status, p.lattesId, p.attributes);
//		var criteria = productionFilter.toPredicate(p);
//
//		return productionRepository.findAll(criteria, pageable, view, ProductionDTO.class);
//	}
	
//	@Transactional(readOnly = true)
//	public Page<ProductionDTO> findAllProductionsWithDocumentAsDTO(ProductionFilter productionFilter, Pageable pageable)
//	{
//		var production = QProduction.production;
//		var p = production;
//
//		var document = QDocument.document;
//		var d = document;
//
//		var reviewer = QUser.user;
//		var r = reviewer;
//
//		var documentView = new QProductionDocumentDTO(d.id, d.type, d.data, d.creationDate, d.reviewDate, d.status, d.origin, r.name);
//		var view = new QProductionDetailsDTO(p.id, p.year, p.title, p.category, p.type.id, p.creationDate, p.reviewDate, p.documentationDate, p.modificationDate, p.status, p.lattesId, p.attributes,  documentView);
//
//		var criteria = productionFilter.toPredicate(p);		
//		var factory = new JPAQueryFactory(entityManager);
//
//		var query = factory
//			.select(view)
//			.from(production)
//			.leftJoin(p.documents, d)
//				.on(d.status.ne(DocumentStatus.REPLACED))
//			.leftJoin(d.reviewer, r)				
//			.where(criteria);
//
//		var pagedQuery = productionRepository.applyPagination(query, pageable);
//
//		List<ProductionDetailsDTO> result = pagedQuery.fetch();
//		List<ProductionDTO> resultI = result.stream().map(dto -> (ProductionDTO) dto).collect(Collectors.toList());
//
//		return PageableExecutionUtils.getPage(resultI, pageable, query::fetchCount);
//	}
	
//	@Transactional(readOnly = true)
//	public ProductionCountDTO countProductions(ProductionFilter productionFilter)
//	{
//		var production = QProduction.production;
//		var p = production;
//
//		var criteria = productionFilter.toPredicate(p);
//
//		var factory = new JPAQueryFactory(entityManager);
//
//		var query = factory
//			.select(new QProductionCountBasicDTO(p.count()))
//			.from(production)
//			.where(criteria);
//		
//		return query.fetchOne();
//	}
	
//	@Transactional(readOnly = true)
//	public ProductionCountDTO countProductionsByType(ProductionFilter productionFilter)
//	{
//		var production = QProduction.production;
//		var p = production;
//
//		var productionType = QProductionType.productionType;
//		var pt = productionType;
//
//		var criteria = productionFilter.toPredicate(p);
//
//		var factory = new JPAQueryFactory(entityManager);
//
//		var query = factory
//			.select(new QProductionCountItemDTO(pt.id, pt.title, pt.count()))
//			.from(production)
//				.join(p.type, pt)
//			.where(criteria)
//			.groupBy(pt.id, pt.title)
//			.orderBy(pt.title.asc());
//
//		var result = query.fetch();
//		var count = result.stream().mapToLong(ProductionCountItemDTO::getCount).sum();
//
//		return new ProductionCountByTypeDTO(count, result);
//	}

	@Transactional(readOnly = true)
	public ProductionDTO findProductionByIdAsDTO(Long productionId)
	{
		return productionRepository.findProductionByIdAsDTO(productionId).orElseThrow(() -> new ProductionNotFoundException(productionId));
	}

	@Transactional(readOnly = true)
	public ProductionDTO findProductionWithDetailsByIdAsDTO(Long productionId)
	{
		return productionRepository.findProductionWithDetailsByIdAsDTO(productionId).orElseThrow(() -> new ProductionNotFoundException(productionId));
	}
//
//	@Transactional(readOnly = true)
//	public ProductionDTO findProductionByLattesIdAsDTO(String lattesId)
//	{
//		return productionRepository.findProductionByLattesIdAsDTO(lattesId).orElseThrow(() -> new ProductionNotFoundException(lattesId));
//	}
//
//	@Transactional(readOnly = true)
//	public ProductionDTO findProductionWithDetailsByLattesIdAsDTO(String lattesId)
//	{
//		return productionRepository.findProductionWithDetailsByLattesIdAsDTO(lattesId).orElseThrow(() -> new ProductionNotFoundException(lattesId));
//	}
//
//	@Transactional(readOnly = true)
//	public Page<ProductionDTO> searchProductionsAsDTO(ProductionSearch search, Pageable pageable)
//	{
//		ProductionSearchDependencies dependencies = new ProductionSearchDependencies(userService, productionRepository);
//
//		return search.findProductionsAsDTO(dependencies, pageable);
//	}
//
//	@Transactional(readOnly = true)
//	public Page<ProductionDTO> searchProductionsWithDetailsAsDTO(ProductionSearch search, Pageable pageable)
//	{
//		ProductionSearchDependencies dependencies = new ProductionSearchDependencies(userService, productionRepository);
//
//		return search.findProductionsWithDetailsAsDTO(dependencies, pageable);
//	}
//
//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public Long insertNewProduction(ProductionInsertDTO productionInsertDTO)
//	{
//		Production production = new Production();
//
//		productionInsertDTO.copyData(production, userRepository, productionTypeRepository);
//
//		return productionRepository.save(production).getId();
//	}

//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public void updateProduction(Long productionId, ProductionUpdateDTO productionUpdateDTO)
//	{
//		// Non institutional productions can be updated by the user, but must
//		// follow some rules. See ProductionUpdateDTO for more details
//
//		Production production = productionRepository.findProductionWithAuthorAndDocumentsAndTypeById(productionId).orElseThrow(() -> new ProductionNotFoundException(productionId));
//
//		if (productionUpdateDTO.applyUpdate(production))
//		{
//			// We allow the user to update the production even if it's already
//			// reviewed, but in this case we reset the status to UNREVIEWED and
//			// the production needs to be reviewed again. The client must
//			// display a warning to the user informing that this will happen if
//			// he tries to update a reviewed production and ask for confirmation
//			// before requesting the update
//
//			if (production.getStatus() == ProductionStatus.REVIEWED || production.getStatus() == ProductionStatus.IRREGULAR)
//			{
//				production.setStatus(ProductionStatus.UNDER_REVIEW);
//
//				Document document = findMostRecentDocument(production);
//
//				document.setStatus(DocumentStatus.UNDER_REVIEW);
//				document.setReviewDate(null);
//				document.setReviewer(null);
//			}
//
//			scoreComputationService.scheduleScoreCacheUpdate(production.getAuthor());
//		}
//	}
//
//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public void deleteInstitutionalProduction(Long productionId)
//	{
//		Production production = productionRepository.findProductionWithAuthorAndTypeById(productionId).orElseThrow(() -> new ProductionNotFoundException(productionId));
//		User author = production.getAuthor();
//
//		if (production.getType().isInstitutional())
//		{
//			productionRepository.delete(production);
//			scoreComputationService.scheduleScoreCacheUpdate(author);
//		}
//
//		throw new ProductionModificationException(String.format("Could not remove the production with id %1$d because it's not an institutional production", productionId));
//	}
//
//	// TODO: this operation repeats in more than one place. We should refactor
//	// this and create a method that could be reused in several places
//	private Document findMostRecentDocument(Production production)
//	{
//		return production.getDocuments().stream().sorted(new DocumentComparator()).findFirst().orElseThrow(() -> new IllegalStateException("This sould not happen. It's probably a bug or data inconsistency on the database"));
//	}
//
//	private static final class DocumentComparator implements Comparator<Document>
//	{
//		@Override
//		public int compare(Document documentA, Document documentB)
//		{
//			return documentB.getCreationDate().compareTo(documentA.getCreationDate());
//		}
//	}
//
//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public List<Production> save(List<Production> insertedProductions)
//	{
//		return productionRepository.saveAll(insertedProductions);
//	}
//
//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public void deleteInBatch(List<Production> excludedProductions)
//	{
//		productionRepository.deleteInBatch(excludedProductions);
//	}
}
