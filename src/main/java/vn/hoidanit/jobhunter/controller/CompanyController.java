package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.DTO.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /*
     * Create company
     */
    @PostMapping("/companies")
    public ResponseEntity<Company> createNewCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.ok().body(this.companyService.createCompany(company));
    }

    /*
     * Get all company
     */
    @GetMapping("/companies")
    @ApiMessage("Get all companies")
    public ResponseEntity<ResultPaginationDTO> getAllCompany(@Filter Specification<Company> spec, Pageable pageable) {

        return ResponseEntity.ok(this.companyService.getAllCompany(spec, pageable));
    }

    /*
     * Update company
     */
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.ok(this.companyService.updateCompany(company));
    }

    /*
     * Delete company
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
        this.companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }

}
