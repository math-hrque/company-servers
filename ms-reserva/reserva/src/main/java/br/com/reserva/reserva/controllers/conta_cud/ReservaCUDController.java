package br.com.reserva.reserva.controllers.conta_cud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.reserva.reserva.exeptions.MudancaEstadoReservaInvalidaException;
import br.com.reserva.reserva.exeptions.ReservaNaoExisteException;
import br.com.reserva.reserva.services.conta_cud.ReservaCUDService;

@RestController
@RequestMapping(value = "/ms-reserva")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaCUDController {

    @Autowired
    ReservaCUDService reservaCUDService;

    @PutMapping("/fazer-checkin/{codigoReserva}")
    public ResponseEntity<Object> fazerCheckinReservaCUD(@PathVariable("codigoReserva") String codigoReserva) {
        try {
            Object reservaCheckin = reservaCUDService.fazerCheckinReservaCUD(codigoReserva);
            return ResponseEntity.status(HttpStatus.OK).body(reservaCheckin);
        } catch (ReservaNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MudancaEstadoReservaInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PutMapping("/confirmar-embarque")
    public ResponseEntity<Object> confirmarEmbarque(@RequestParam("codigoVoo") String codigoVoo, @RequestParam("codigoReserva") String codigoReserva) {
        try {
            Object reservaEmbarcada = reservaCUDService.confirmarEmbarqueReservaCUD(codigoVoo, codigoReserva);
            return ResponseEntity.status(HttpStatus.OK).body(reservaEmbarcada);
        } catch (ReservaNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MudancaEstadoReservaInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
