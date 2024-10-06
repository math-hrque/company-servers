package br.com.funcionario.funcionario.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {
    String email;
    String senha;
    String tipo = "FUNCIONARIO";
}
