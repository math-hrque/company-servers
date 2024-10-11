package br.com.funcionario.funcionario.consumers.r18_atualizar_funcionario_usuario;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.funcionario.funcionario.dtos.FuncionarioRequestDto;
import br.com.funcionario.funcionario.dtos.UsuarioIdRequestDto;
import br.com.funcionario.funcionario.exeptions.FuncionarioNaoExisteException;
import br.com.funcionario.funcionario.exeptions.OutroFuncionarioDadosJaExistente;
import br.com.funcionario.funcionario.services.FuncionarioService;

@Component
public class AtualizarFuncionarioConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    FuncionarioService funcionarioService;

    private static final String EXCHANGE_NAME = "saga-exchange";

    @RabbitListener(queues = "ms-funcionario-atualizar")
    public void atualizarFuncionario(FuncionarioRequestDto funcionarioRequestDto) {
        try {
            UsuarioIdRequestDto usuarioIdRequestDto = funcionarioService.atualizar(funcionarioRequestDto);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "ms-funcionario-atualizado", usuarioIdRequestDto);
        } catch (FuncionarioNaoExisteException e) {

        } catch (OutroFuncionarioDadosJaExistente e) {
        
        } catch (Exception e) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "ms-funcionario-atualiza-erro", funcionarioRequestDto.getIdFuncionario());
        }
    }

    @RabbitListener(queues = "ms-funcionario-atualiza-compensar-idFuncionario")
    public void compensarAtualizaFuncionario(Long idFuncionario) {
        try {
            funcionarioService.reverter(idFuncionario);
        } catch (FuncionarioNaoExisteException e) {

        } catch (Exception e) {

        }
    }
}
