package br.com.reserva.reserva.services.conta_r;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.reserva.reserva.dtos.ReservaDto;
import br.com.reserva.reserva.dtos.ReservaManterDto;
import br.com.reserva.reserva.dtos.VooDto;
import br.com.reserva.reserva.enums.TipoEstadoReserva;
import br.com.reserva.reserva.exeptions.ListaReservaVaziaException;
import br.com.reserva.reserva.exeptions.ReservaNaoExisteException;
import br.com.reserva.reserva.models.conta_cud.ReservaCUD;
import br.com.reserva.reserva.models.conta_r.ReservaR;
import br.com.reserva.reserva.repositories.conta_r.ReservaRRepository;
import br.com.reserva.reserva.utils.conta_r.RedisReservaRCache;

@Service
public class ReservaRService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RedisReservaRCache redisReservaRCache;

    @Autowired
    private ReservaRRepository reservaRRepository;

    public ReservaManterDto reservaRCadastrar(ReservaCUD reservaCUD) {
        ReservaR reservaR = mapper.map(reservaCUD, ReservaR.class);
        reservaR.setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
        reservaR.setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
        ReservaR reservaEmbarcadaR = reservaRRepository.save(reservaR);
        ReservaManterDto reservaManterEmbarcadaR = mapper.map(reservaEmbarcadaR, ReservaManterDto.class);
        return reservaManterEmbarcadaR;
    }

    public ReservaManterDto reservaRCancelar(ReservaCUD reservaCUD) throws ReservaNaoExisteException {
        Optional<ReservaR> reservaRBD = reservaRRepository.findById(reservaCUD.getCodigoReserva());
        if (!reservaRBD.isPresent()) {
            throw new ReservaNaoExisteException("Reserva nao existe");
        }
        reservaRBD.get().setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
        reservaRBD.get().setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
        ReservaR reservaCanceladaR = reservaRRepository.save(reservaRBD.get());
        ReservaManterDto reservaManterCanceladaR = mapper.map(reservaCanceladaR, ReservaManterDto.class);
        return reservaManterCanceladaR;
    }

    public List<ReservaManterDto> reservasRVooCancelar(List<ReservaCUD> listaReservaCUD) {
        List<ReservaR> listaReservaR = new ArrayList<>();
        List<ReservaManterDto> listaReservaManterDto = new ArrayList<>();
        for (ReservaCUD reservaCUD : listaReservaCUD) {
            Optional<ReservaR> reservaR = reservaRRepository.findById(reservaCUD.getCodigoReserva());
            if (reservaR.isPresent()) {
                reservaR.get().setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
                reservaR.get().setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
                listaReservaR.add(reservaR.get());
                ReservaManterDto reservaManterRealizadaR = mapper.map(reservaR.get(), ReservaManterDto.class);
                listaReservaManterDto.add(reservaManterRealizadaR);
            }
        }
        reservaRRepository.saveAll(listaReservaR);
        return listaReservaManterDto;
    }

    public List<ReservaManterDto> reservasRVooRealizar(List<ReservaCUD> listaReservaCUD) {
        List<ReservaR> listaReservaR = new ArrayList<>();
        List<ReservaManterDto> listaReservaManterDto = new ArrayList<>();
        for (ReservaCUD reservaCUD : listaReservaCUD) {
            Optional<ReservaR> reservaR = reservaRRepository.findById(reservaCUD.getCodigoReserva());
            if (reservaR.isPresent()) {
                reservaR.get().setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
                reservaR.get().setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
                listaReservaR.add(reservaR.get());
                ReservaManterDto reservaManterRealizadaR = mapper.map(reservaR.get(), ReservaManterDto.class);
                listaReservaManterDto.add(reservaManterRealizadaR);
            }
        }
        reservaRRepository.saveAll(listaReservaR);
        return listaReservaManterDto;
    }

    public ReservaManterDto reservaRConfirmarEmbarque(ReservaCUD reservaCUD) throws ReservaNaoExisteException {
        Optional<ReservaR> reservaR = reservaRRepository.findById(reservaCUD.getCodigoReserva());
        if (!reservaR.isPresent()) {
            throw new ReservaNaoExisteException("Reserva nao existe");
        }

        reservaR.get().setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
        reservaR.get().setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
        ReservaR reservaEmbarcadaR = reservaRRepository.save(reservaR.get());
        ReservaManterDto reservaManterEmbarcadaR = mapper.map(reservaEmbarcadaR, ReservaManterDto.class);
        return reservaManterEmbarcadaR;
    }

    public ReservaManterDto reservaRCheckin(ReservaCUD reservaCUD) throws ReservaNaoExisteException {
        Optional<ReservaR> reservaR = reservaRRepository.findById(reservaCUD.getCodigoReserva());
        if (!reservaR.isPresent()) {
            throw new ReservaNaoExisteException("Reserva nao existe");
        }

        reservaR.get().setSiglaEstadoReserva(reservaCUD.getEstadoReserva().getSiglaEstadoReserva());
        reservaR.get().setTipoEstadoReserva(reservaCUD.getEstadoReserva().getTipoEstadoReserva());
        ReservaR reservaCheckinR = reservaRRepository.save(reservaR.get());
        ReservaManterDto reservaManterCheckinR = mapper.map(reservaCheckinR, ReservaManterDto.class);
        return reservaManterCheckinR;
    }

    public List<ReservaDto> listarReservasVoos48h(Long idCliente, List<VooDto> listaVooDto) throws ListaReservaVaziaException {
        List<String> listaCodigoVoo = listaVooDto.stream().map(VooDto::getCodigoVoo).collect(Collectors.toList());
        Optional<List<ReservaR>> listaReservaRBD = reservaRRepository.findByCodigoVooInAndIdClienteAndTipoEstadoReserva(listaCodigoVoo, idCliente, TipoEstadoReserva.CONFIRMADO);
    
        if (!listaReservaRBD.isPresent() || listaReservaRBD.get().isEmpty()) {
            throw new ListaReservaVaziaException("Lista de reservas vazia!");
        }
    
        Map<String, VooDto> vooDtoMap = listaVooDto.stream().collect(Collectors.toMap(VooDto::getCodigoVoo, vooDto -> vooDto));
        return listaReservaRBD.get().stream().map(reservaR -> {
            ReservaDto reservaDto = mapper.map(reservaR, ReservaDto.class);
            reservaDto.setVoo(vooDtoMap.get(reservaR.getCodigoVoo()));
            return reservaDto;
        }).collect(Collectors.toList());
    }

    public ReservaDto visualizarReservaCliente(Long idCliente, String codigoReserva) throws ReservaNaoExisteException {
      Optional<ReservaR> reservaOptional = reservaRRepository.findByCodigoReservaAndIdCliente(codigoReserva, idCliente);

      if (!reservaOptional.isPresent()) {
          throw new ReservaNaoExisteException("Reserva não encontrada para o cliente especificado.");
      }

      return mapper.map(reservaOptional.get(), ReservaDto.class);
    }
    
}
