package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRespository;

@Service
public class CompanyService {

    private final CompanyRespository companyRespository;

    public CompanyService(CompanyRespository companyRespository) {
        this.companyRespository = companyRespository;
    }

    public Company createCompany(Company company) {
        return this.companyRespository.save(company);
    }

    public List<Company> getAllCompany() {
        return this.companyRespository.findAll();
    }

    public Company updateCompany(Company company) {
        Optional<Company> companyOptional = this.companyRespository.findById(company.getId());

        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setName(company.getName());
            currentCompany.setAddress(company.getAddress());
            currentCompany.setLogo(company.getLogo());
            currentCompany.setDescription(company.getDescription());

            return this.companyRespository.save(currentCompany);
        }
        return null;
    }

    public void deleteCompany(long id) {
        this.companyRespository.deleteById(id);
    }
}
