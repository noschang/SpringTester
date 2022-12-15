package com.whitrus.spring.tester.domain.production;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.whitrus.spring.tester.domain.production.model.Production;
import com.whitrus.spring.tester.domain.production.model.dto.ProductionDTO;

@Transactional(readOnly = true)
public interface ProductionRepository extends JpaRepository<Production, Long>	 
{
	// Queries with DTOs to use on controllers

	@Query("SELECT NEW com.whitrus.spring.tester.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P WHERE P.id = :productionId")
	public Optional<ProductionDTO> findProductionByIdAsDTO(@Param("productionId") Long productionId);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.id = :productionId")
	public Optional<ProductionDTO> findProductionWithDetailsByIdAsDTO(@Param("productionId") Long productionId);
	
//	@Query("SELECT NEW com.whitrus.spring.tester.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P")
	@Query("SELECT NEW com.whitrus.spring.tester.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, D.id, D.type, D.data, D.creationDate, D.reviewDate, D.status, D.origin, R.name) FROM Production P LEFT JOIN P.reviewer R LEFT JOIN P.documents D")
	public Page<ProductionDTO> findAllProductionsAsDTO(Pageable pageable);
	
	
	
	
//
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate,P.status) FROM Production P WHERE P.lattesId = :lattesId")
//	public Optional<ProductionDTO> findProductionByLattesIdAsDTO(@Param("lattesId") String lattesId);
//
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.lattesId = :lattesId")
//	public Optional<ProductionDTO> findProductionWithDetailsByLattesIdAsDTO(@Param("lattesId") String lattesId);
//
//	@Query("SELECT NEW br.univali.sapi.domain.document.model.DocumentDTO(D.id, D.type, D.data, D.creationDate, D.reviewDate, D.status, D.origin) FROM Document D JOIN D.production P WHERE P.id = :productionId")
//	public Page<DocumentDTO> findProductionDocumentsAsDTO(@Param("productionId") Long productionId, Pageable pageable);
//	
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P WHERE P.author.id = :authorId AND P.id = :productionId")
//	public Optional<ProductionDTO> findUserProductionByIdAsDTO(@Param("authorId") Long userId, @Param("productionId") Long productionId);
//	
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.author.id = :authorId AND P.id = :productionId")
//	public Optional<ProductionDTO> findUserProductionWithDetailsByIdAsDTO(@Param("authorId") Long userId, @Param("productionId") Long productionId);

	// Search queries

//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P WHERE P.author.id = :authorId AND P.status IN :status")
//	public Page<ProductionDTO> findProductionsByAuthorAndStatusAsDTO(@Param("authorId") Long authorId, @Param("status") Set<ProductionStatus> status, Pageable pageable);
//
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.author.id = :authorId AND P.status IN :status")
//	public Page<ProductionDTO> findProductionsWithDetailsByAuthorAndStatusAsDTO(@Param("authorId") Long authorId, @Param("status") Set<ProductionStatus> status, Pageable pageable);
//
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P WHERE P.author.id = :authorId AND P.reviewer.id = :reviewerId")
//	public Page<ProductionDTO> findProductionsByAuthorAndReviewerAsDTO(@Param("authorId") Long authorId, @Param("reviewerId") Long reviewerId, Pageable pageable);
//
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.author.id = :authorId AND P.reviewer.id = :reviewerId")
//	public Page<ProductionDTO> findProductionsWithDetailsByAuthorAndReviewerAsDTO(@Param("authorId") Long authorId, @Param("reviewerId") Long reviewerId, Pageable pageable);
	

	// Queries with entities to use on services

//	@Query("SELECT DISTINCT P FROM Production P JOIN FETCH P.author A LEFT JOIN FETCH P.documents D WHERE P.id = :productionId")
//	@QueryHints(value = @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
//	public Optional<Production> findProductionWithAuthorAndDocumentsById(@Param("productionId") Long productionId);

//	@Query("SELECT DISTINCT P FROM Production P JOIN FETCH P.author A LEFT JOIN FETCH P.documents D LEFT JOIN FETCH D.reviewer R WHERE P.id = :productionId")
//	@QueryHints(value = @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
//	public Optional<Production> findProductionWithAuthorAndDocumentsAndReviewerById(@Param("productionId") Long productionId);

	// TODO: test this to see if the DISTINCT keyword is necessary
//	@Query("SELECT DISTINCT P FROM Production P JOIN FETCH P.type T JOIN FETCH P.author A LEFT JOIN FETCH P.documents D WHERE P.id = :productionId")
//	@QueryHints(value = @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
//	@Query("SELECT P FROM Production P JOIN FETCH P.type T JOIN FETCH P.author A LEFT JOIN FETCH P.documents D WHERE P.id = :productionId")
//	@EntityGraph(attributePaths = {"author", "type", "documents"})
//	@Query("SELECT P FROM Production P WHERE P.id = :productionId")
//	public Optional<Production> findProductionWithAuthorAndDocumentsAndTypeById(@Param("productionId") Long productionId);
	
//	@EntityGraph(attributePaths = {"author", "type"})
//	@Query("SELECT P FROM Production P WHERE P.id = :productionId")
//	public Optional<Production> findProductionWithAuthorAndTypeById(@Param("productionId") Long productionId);

	// Other queries
//	@Query("SELECT P FROM Production P JOIN P.documents D WHERE P.author = :user AND D.reviewer = :reviewer AND D.status != 'REPLACED' AND (P.status = 'REVIEWED' OR P.status = 'IRREGULAR') ORDER BY P.reviewDate DESC")
//	public List<Production> findReviewedProductionsByReviewerAndAuthor(@Param("user") User user, @Param("reviewer") User reviewer);

//	@Query("SELECT NEW br.univali.sapi.domain.dashboard.productions.ProductionCount(T.id, T.title, COUNT(*)) FROM Production P JOIN P.type T WHERE P.author.id = :authorId GROUP BY T.id, T.title ORDER BY T.title")
//	public List<ProductionCount> countUserProductionsByType(@Param("authorId") Long authorId);

	
//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionDetailsDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, P.lattesId, P.attributes) FROM Production P WHERE P.author.id = :userId AND P.type.id = :typeId")
//	public Page<ProductionDTO> findUserProductionsWithDetailsByTypeAsDTO(@Param("userId") Long userId, @Param("typeId") Long typeId, Pageable pageable);

//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status, D.id, D.type, D.data, D.creationDate, D.reviewDate, D.status, D.origin, R.name) FROM Production P LEFT JOIN P.documents D ON D.status != 'REPLACED' LEFT JOIN D.reviewer R WHERE P.author.id = :userId AND P.type.id = :typeId")
//	public Page<ProductionDTO> findUserProductionsWithDocumentByTypeAsDTO(@Param("userId") Long userId, @Param("typeId") Long typeId, Pageable pageable);

//	@Query("SELECT NEW br.univali.sapi.domain.production.model.dto.ProductionBasicDTO(P.id, P.year, P.title, P.category, P.type.id, P.creationDate, P.reviewDate, P.documentationDate, P.modificationDate, P.status) FROM Production P WHERE P.author.id = :userId AND P.type.id = :typeId")
//	public Page<ProductionDTO> findUserProductionsByTypeAsDTO(@Param("userId") Long userId, @Param("typeId") Long typeId, Pageable pageable);
}
