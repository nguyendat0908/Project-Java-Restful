package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Permission;
import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role findById(long id) {
        Optional<Role> opRole = this.roleRepository.findById(id);
        if (opRole.isPresent()) {
            return opRole.get();
        }
        return null;
    }

    public boolean existsByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role handleCreateRole(Role role) throws IdInvalidException {

        // Check name
        if (this.existsByName(role.getName())) {
            throw new IdInvalidException("Role đã tồn tại!");
        }

        // Check permissions
        if (role.getPermissions() != null) {
            List<Long> reqRoles = role.getPermissions().stream().map(x -> x.getId()).collect(Collectors.toList());

            List<Permission> permissions = this.permissionRepository.findByIdIn(reqRoles);
            role.setPermissions(permissions);

        }

        return this.roleRepository.save(role);
    }

    public Role handleUpdateRole(Role role) {

        Role opRole = this.findById(role.getId());

        // Check permissions
        if (role.getPermissions() != null) {
            List<Long> reqRoles = role.getPermissions().stream().map(x -> x.getId()).collect(Collectors.toList());

            List<Permission> permissions = this.permissionRepository.findByIdIn(reqRoles);
            role.setPermissions(permissions);

        }

        opRole.setName(role.getName());
        opRole.setDescription(role.getDescription());
        opRole.setActive(role.isActive());
        opRole.setPermissions(role.getPermissions());
        opRole = this.roleRepository.save(opRole);
        return opRole;
    }

    public void handleDeleteRole(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> pRole = this.roleRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pRole.getTotalPages());
        mt.setTotal(pRole.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pRole.getContent());
        return rs;
    }
}
