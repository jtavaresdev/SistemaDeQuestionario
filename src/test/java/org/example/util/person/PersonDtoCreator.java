package org.example.util.person;

import org.example.dto.PersonDTO;

public class PersonDtoCreator {

    public static PersonDTO createValidPersonDto(){
        return PersonDTO.builder()
                .name("Fulano")
                .email("fulanoemail@gmail.com")
                .idade(19)
                .altura(1.78)
                .build();
    }

    public static PersonDTO createInvalidPersonDto(){
        return PersonDTO.builder()
                .name("")
                .email("")
                .idade(19)
                .altura(1.78)
                .build();
    }


}
