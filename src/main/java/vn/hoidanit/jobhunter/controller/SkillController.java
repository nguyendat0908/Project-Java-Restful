package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @ApiMessage("Create a new skill!")
    public ResponseEntity<Skill> createNewSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {

        // Check if skill name is already exist
        boolean isNameExist = this.skillService.existsByName(skill.getName());
        if (isNameExist) {
            throw new IdInvalidException(
                    "Skill name: " + skill.getName() + " đã tồn tại, vui lòng sử dụng skill khác.");
        }

        Skill newSkill = this.skillService.handleCreateSkill(skill);
        return ResponseEntity.ok(newSkill);
    }

    @PutMapping("/skills")
    @ApiMessage("Update a skill!")
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {

        Skill updatedSkill = this.skillService.findById(skill.getId());

        // Check if skill is not exist
        if (updatedSkill == null) {
            throw new IdInvalidException("Skill id: " + skill.getId() + " không tồn tại!.");

        }

        // Check if skill name is already exist
        if (skill.getName() != null && this.skillService.existsByName(skill.getName())) {
            throw new IdInvalidException(
                    "Skill name: " + skill.getName() + " đã tồn tại, vui lòng sử dụng skill khác.");
            
        }

        return ResponseEntity.ok(updatedSkill);
    }

    @DeleteMapping("/skills/{id}")
    @ApiMessage("Delete a skill!")
    public ResponseEntity<Void> deteleSkill(@PathVariable("id") long id) throws IdInvalidException {
       

        Skill currentSkill = this.skillService.findById(id);
        // Check if skill is not exist
        if (currentSkill == null) {
            throw new IdInvalidException("Skill id: " + id + " không tồn tại!.");

        }
        this.skillService.handleDeleteSkill(id);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/skills")
    @ApiMessage("Fetch all skill!")
    public ResponseEntity<ResultPaginationDTO> getAllSkill(@Filter Specification<Skill> spec, Pageable pageable) {
        return ResponseEntity.ok(this.skillService.fetchAllSkill(spec, pageable));
    }
    

}
