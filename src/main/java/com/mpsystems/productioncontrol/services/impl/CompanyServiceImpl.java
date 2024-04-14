package com.mpsystems.productioncontrol.services.impl;

import com.mpsystems.productioncontrol.dto.CompanyDTO;
import com.mpsystems.productioncontrol.exceptions.CnpjAlreadyRegisteredException;
import com.mpsystems.productioncontrol.exceptions.CompanyNotFoundException;
import com.mpsystems.productioncontrol.exceptions.EmailAlreadyRegisteredException;
import com.mpsystems.productioncontrol.models.Company;
import com.mpsystems.productioncontrol.repositories.CompanyRepository;
import com.mpsystems.productioncontrol.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(@Valid CompanyDTO companyDTO) {
        validateCnpj(companyDTO.getCnpj());
        validateEmail(companyDTO.getResponsibleEmail());
        isUniqueEmail(companyDTO);
        isUniqueCnpj(companyDTO);

        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setCnpj(companyDTO.getCnpj());
        company.setAddress(companyDTO.getAddress());
        company.setPhoneNumber(companyDTO.getPhoneNumber());
        company.setResponsibleEmail(companyDTO.getResponsibleEmail());
        company.setPassword(companyDTO.getPassword());

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada"));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Long id, @Valid CompanyDTO companyDTO) {
        validateCnpj(companyDTO.getCnpj());
        validateEmail(companyDTO.getResponsibleEmail());

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada"));

        company.setName(companyDTO.getName());
        company.setCnpj(companyDTO.getCnpj());
        company.setAddress(companyDTO.getAddress());
        company.setPhoneNumber(companyDTO.getPhoneNumber());
        company.setResponsibleEmail(companyDTO.getResponsibleEmail());
        company.setPassword(companyDTO.getPassword());

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa não encontrada"));
        companyRepository.deleteById(id);
    }

    private void validateCnpj(String cnpj) {
        if (!isValidCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido!");
        }
    }

    private void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email inválido!");
        }
    }

    private boolean isValidCnpj(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return false;
        }

        String regex = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cnpj);

        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private void isUniqueEmail(CompanyDTO companyDTO) {
        Optional<Company> responsibleEmail = companyRepository.findByResponsibleEmail(companyDTO.getResponsibleEmail());
        if (responsibleEmail.isPresent()) {
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado no sistema!");
        }
    }

    private void isUniqueCnpj(CompanyDTO companyDTO) {
        Optional<Company> cnpj = companyRepository.findByCnpj(companyDTO.getCnpj());
        if (cnpj.isPresent()) {
            throw new CnpjAlreadyRegisteredException("CNPJ já cadastrado no sistema!");
        }
    }
}