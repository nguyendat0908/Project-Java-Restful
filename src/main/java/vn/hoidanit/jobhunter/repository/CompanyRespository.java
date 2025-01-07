package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.Company;
import java.util.Optional;

@Repository
public interface CompanyRespository extends JpaRepository<Company, Long> {

    Optional<Company> findById(long id);
}
