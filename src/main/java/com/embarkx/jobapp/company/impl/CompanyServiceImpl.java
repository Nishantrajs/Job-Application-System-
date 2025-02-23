package com.embarkx.jobapp.company.impl;

import com.embarkx.jobapp.company.Company;
import com.embarkx.jobapp.company.CompanyRepository;
import com.embarkx.jobapp.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService
{
    CompanyRepository companyRepository;
    private Long companyId = 1L;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Long id, Company company) {

        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()) {

             Company companyExisting = companyOptional.get();

            if (companyExisting.getId().equals(id)) {

                companyExisting.setName(company.getName());
                companyExisting.setDescription(company.getDescription());
                companyExisting.setJobs(company.getJobs());

                companyRepository.save(companyExisting);

                return true;
            }
        }

        return false;
    }

    @Override
    public void createCompany(Company company) {
        company.setId(companyId++);
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {

        try
        {
            companyRepository.deleteById(id);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    @Override
    public Company getCompanyById(Long id) {

        return companyRepository.
                findAll().
                stream().
                filter(company -> company.getId().equals(id)).
                findFirst().
                orElse(null);
    }
}
