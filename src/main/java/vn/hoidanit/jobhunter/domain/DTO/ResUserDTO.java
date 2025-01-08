package vn.hoidanit.jobhunter.domain.DTO;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {

    private long id;

    private int age;

    private String name;
    private String email;
    private String address;

    private GenderEnum gender;

    private Instant createdBy;
    private Instant updatedBy;
    
}
