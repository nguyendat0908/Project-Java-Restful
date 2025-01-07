package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.service.CompanyService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class CompanyController {
    
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
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
    public ResponseEntity<List<Company>> getAllCompany() {
        return ResponseEntity.ok(this.companyService.getAllCompany());
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
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id){
        this.companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }
    
    
}
