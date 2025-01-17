package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository,
            CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.companyRepository = companyRepository;
    }

    public Optional<Job> fetchJobById(long id) {
        return this.jobRepository.findById(id);
    }

    public ResCreateJobDTO handleCreateJob(Job job) {

        // Check skill
        if (job.getSkills() != null) {
            List<Long> reqSkills = job.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());

            List<Skill> skills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(skills);
        }

        // Check company exist
        if (job.getCompany() != null) {
            Optional<Company> cOptional = this.companyRepository.findById(job.getCompany().getId());
            if (cOptional.isPresent()) {
                job.setCompany(cOptional.get());
            }
        }

        // Create job
        Job newJob = this.jobRepository.save(job);
        ResCreateJobDTO res = new ResCreateJobDTO();
        res.setId(newJob.getId());
        res.setName(newJob.getName());
        res.setSalary(newJob.getSalary());
        res.setQuantity(newJob.getQuantity());
        res.setLocation(newJob.getLocation());
        res.setLevel(newJob.getLevel());
        res.setStartDate(newJob.getStartDate());
        res.setEndDate(newJob.getEndDate());
        res.setActive(newJob.isActive());
        res.setCreatedAt(newJob.getCreatedAt());
        res.setCreatedBy(newJob.getCreatedBy());

        if (newJob.getSkills() != null) {
            List<String> skills = newJob.getSkills().stream().map(x -> x.getName()).collect(Collectors.toList());
            res.setSkills(skills);
        }
        return res;
    }

    public ResUpdateJobDTO handleUpdateJob(Job job, Job jobDB) {

        // Check skill
        if (job.getSkills() != null) {
            List<Long> reqSkills = job.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());

            List<Skill> skills = this.skillRepository.findByIdIn(reqSkills);
            jobDB.setSkills(skills);
        }

        // Check company exist
        if (job.getCompany() != null) {
            Optional<Company> cOptional = this.companyRepository.findById(job.getCompany().getId());
            if (cOptional.isPresent()) {
                jobDB.setCompany(cOptional.get());
            }
        }

        // Update job in db avoid case user don't send full info make die data
        jobDB.setName(job.getName());
        jobDB.setSalary(job.getSalary());
        jobDB.setQuantity(job.getQuantity());
        jobDB.setLocation(job.getLocation());
        jobDB.setLevel(job.getLevel());
        jobDB.setStartDate(job.getStartDate());
        jobDB.setEndDate(job.getEndDate());
        jobDB.setActive(job.isActive());

        // Update job
        Job newJob = this.jobRepository.save(jobDB);

        ResUpdateJobDTO res = new ResUpdateJobDTO();
        res.setId(newJob.getId());
        res.setName(newJob.getName());
        res.setSalary(newJob.getSalary());
        res.setQuantity(newJob.getQuantity());
        res.setLocation(newJob.getLocation());
        res.setLevel(newJob.getLevel());
        res.setStartDate(newJob.getStartDate());
        res.setEndDate(newJob.getEndDate());
        res.setActive(newJob.isActive());
        res.setUpdatedAt(newJob.getUpdatedAt());
        res.setUpdatedBy(newJob.getUpdatedBy());

        if (newJob.getSkills() != null) {
            List<String> skills = newJob.getSkills()
                    .stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            res.setSkills(skills);
        }

        return res;
    }

    public void delete(long id) {
        this.jobRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAll(Specification<Job> spec, Pageable pageable) {
        Page<Job> pageUser = this.jobRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);

        rs.setResult(pageUser.getContent());

        return rs;
    }

}
