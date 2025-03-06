package org.example.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
public class PersonDTO {
    private final String name;
    private final String email;
    private final int idade;
    private final double altura;

    public PersonDTO(@NotBlank String name, @NotBlank String email, @NotNull int idade, @NotNull double altura) {
        this.name = name;
        this.email = email;
        this.idade = idade;
        this.altura = altura;
    }

    // Nos metodos get precisa ter a anotação se nao não é utilizado @Valid

    public @NotBlank String getName() {
        return name;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public @NotNull int getIdade() {
        return idade;
    }

    public @NotNull double getAltura() {
        return altura;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return idade == personDTO.idade && Double.compare(altura, personDTO.altura) == 0 && Objects.equals(name, personDTO.name) && Objects.equals(email, personDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, idade, altura);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", altura=" + altura +
                '}';
    }
}
