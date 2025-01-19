package vn.hoidanit.jobhunter.domain.response.user;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;
    private CompanyUser company;
    private RoleUser roleUser;

    @Setter
    @Getter
    public static class CompanyUser {
        private long id;
        private String name;
    }

    @Setter
    @Getter
    public static class RoleUser {
        private long id;
        private String name;
    }
}
