package br.com.cliente.cliente.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.cliente.dtos.ClienteDto;
import br.com.cliente.cliente.dtos.UsuarioRequestCadastrarDto;
import br.com.cliente.cliente.exeptions.ClienteNaoExisteException;
import br.com.cliente.cliente.exeptions.OutroClienteDadosJaExistenteException;
import br.com.cliente.cliente.models.Cliente;
import br.com.cliente.cliente.repositories.ClienteRepository;
import br.com.cliente.cliente.repositories.MilhasRepository;
import br.com.cliente.cliente.utils.RedisClienteCache;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RedisClienteCache redisClienteCache;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MilhasRepository milhasRepository;

    @Autowired
    private MilhasService milhasService;

    public UsuarioRequestCadastrarDto cadastrar(ClienteDto clienteDto) throws OutroClienteDadosJaExistenteException {
        Optional<List<Cliente>> existClienteBD = clienteRepository.findByCpfOrEmail(clienteDto.getCpf(), clienteDto.getEmail());
        if (existClienteBD.isPresent() && !existClienteBD.get().isEmpty()) {
            boolean cpfExists = false;
            boolean emailExists = false;
            for (Cliente cliente : existClienteBD.get()) {
                if (cliente.getCpf().equals(clienteDto.getCpf())) {
                    cpfExists = true;
                }
                if (cliente.getEmail().equals(clienteDto.getEmail())) {
                    emailExists = true;
                } 
            }
            if (cpfExists && emailExists) {
                throw new OutroClienteDadosJaExistenteException("Outro cliente com cpf e email ja existente!");
            } else if (cpfExists) {
                throw new OutroClienteDadosJaExistenteException("Outro cliente com cpf ja existente!");
            } else if (emailExists) {
                throw new OutroClienteDadosJaExistenteException("Outro cliente com email ja existente!");
            }
        }

        Cliente cliente = mapper.map(clienteDto, Cliente.class);
        cliente.setIdCliente(0L);
        Cliente clienteCriadoBD = clienteRepository.save(cliente);
        UsuarioRequestCadastrarDto usuarioRequestCadastrarDto = mapper.map(clienteCriadoBD, UsuarioRequestCadastrarDto.class);
        return usuarioRequestCadastrarDto;
    }

    public ClienteDto remover(String email) throws ClienteNaoExisteException {
        Optional<Cliente> clienteBD = clienteRepository.findByEmail(email);
        if (!clienteBD.isPresent()) {
            throw new ClienteNaoExisteException("Cliente nao existe!");
        }

        Cliente cliente = clienteBD.get();
        clienteRepository.deleteById(cliente.getIdCliente());
        ClienteDto clienteRemovidoDto = mapper.map(cliente, ClienteDto.class);
        return clienteRemovidoDto;
    }
}
