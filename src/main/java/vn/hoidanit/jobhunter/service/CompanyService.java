package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.DTO.Meta;
import vn.hoidanit.jobhunter.domain.DTO.ResultPaginationDTO;
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

    public ResultPaginationDTO getAllCompany(Specification<Company> spec,Pageable pageable) {

        Page<Company> pageCompany = this.companyRespository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageCompany.getNumber() + 1);
        meta.setPageSize(pageCompany.getSize());
        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(pageCompany.getContent());

        return resultPaginationDTO;
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
