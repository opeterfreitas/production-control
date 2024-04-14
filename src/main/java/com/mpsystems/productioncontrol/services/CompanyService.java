package com.mpsystems.productioncontrol.services;

import com.mpsystems.productioncontrol.dto.CompanyDTO;
import com.mpsystems.productioncontrol.models.Company;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {
    Company createCompany(@Valid CompanyDTO companyDTO);

    Company getCompanyById(Long id);

    List<Company> getAllCompanies();

    Company updateCompany(Long id, @Valid CompanyDTO companyDTO);

    void deleteCompany(Long id);
}