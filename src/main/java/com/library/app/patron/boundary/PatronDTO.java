package com.library.app.patron.boundary;

import com.library.app.patron.repository.entity.Patron;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PatronDTO {
    private Long id;
    @NotEmpty
    private String name;
    @Email
    private String email;
    private boolean member;

    public static PatronDTO fromEntity(Patron patron) {
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setId(patron.getId());
        patronDTO.setName(patron.getName());
        patronDTO.setEmail(patron.getEmail());
        patronDTO.setMember(patron.isMember());
        return patronDTO;
    }

    public Patron toEntity() {
        Patron patron = new Patron();
        patron.setId(this.getId());
        patron.setName(this.getName());
        patron.setEmail(this.getEmail());
        patron.setMember(this.isMember());
        return patron;
    }

}

