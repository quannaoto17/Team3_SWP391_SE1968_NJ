package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
    List<Case> findByFormFactor(String formFactor);

    @Query("SELECT c FROM Case c WHERE " +
           "(:formFactor IS NULL OR c.formFactor = :formFactor)")
    List<Case> findWithFilters(@Param("formFactor") String formFactor);

    Optional <Case> findByProduct_ProductId(int id);

}
