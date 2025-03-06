package org.example.util.person;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domain.Person;

import java.util.UUID;

@AllArgsConstructor
public class PersonCreator {

    public static Person createPersonValid(){
        return Person.builder()
                .id(UUID.fromString("cf9f63a1-600b-44e5-a533-df6df8599735"))
                .name("Fulano")
                .email("fulanoemail@gmail.com")
                .idade(19)
                .altura(1.78)
                .build();
    }
    public static Person createPersonToBeSaved(){
        return Person.builder()
                .name("Fulano")
                .email("fulanoemail@gmail.com")
                .idade(19)
                .altura(1.78)
                .build();
    }
}
