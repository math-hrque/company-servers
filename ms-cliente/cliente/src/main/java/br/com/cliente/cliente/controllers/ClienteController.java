package br.com.cliente.cliente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cliente.cliente.dtos.ClienteMilhasDto;
import br.com.cliente.cliente.dtos.SaldoMilhasDto;
import br.com.cliente.cliente.exeptions.ClienteNaoExisteException;
import br.com.cliente.cliente.services.ClienteService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/ms-cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping("/comprar-milhas")
    public ResponseEntity<SaldoMilhasDto> comprarMilhas(@RequestParam("idCliente") Long idCliente,
            @RequestParam("quantidadeMilhas") int quantidadeMilhas) {
        try {
            SaldoMilhasDto novoSaldo = clienteService.comprarMilhas(quantidadeMilhas, idCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoSaldo);
        } catch (ClienteNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar-milhas/{idCliente}")
    public ResponseEntity<ClienteMilhasDto> consultarExtratoMilhas(@PathVariable("idCliente") Long idCliente) {
        try {
            ClienteMilhasDto extrato = clienteService.consultarExtratoMilhas(idCliente);
            return ResponseEntity.ok(extrato);
        } catch (ClienteNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultar-email/{email}")
    public ResponseEntity<Object> consultarEmail(@PathVariable("email") String email) {
        try {
            Object clienteConsultado = clienteService.consultarEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(clienteConsultado);
        } catch (ClienteNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/consultar-idcliente/{idCliente}")
    public ResponseEntity<Object> consultarIdCliente(@PathVariable("idCliente") Long idCliente) {
        try {
            Object clienteConsultado = clienteService.consultarIdCliente(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(clienteConsultado);
        } catch (ClienteNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
