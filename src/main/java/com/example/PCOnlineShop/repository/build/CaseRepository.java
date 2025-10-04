package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case,Integer> {
}
