package com.mpsystems.productioncontrol.repositories;

import com.mpsystems.productioncontrol.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByResponsibleEmail(String responsibleEmail);

    Optional<Company> findByCnpj(String cnpj);
}