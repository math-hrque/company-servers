package br.com.reserva.reserva.consumers.r07_efetuar_reserva;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.reserva.reserva.dtos.ReservaManterDto;
import br.com.reserva.reserva.models.conta_cud.ReservaCUD;
import br.com.reserva.reserva.services.conta_cud.ReservaCUDService;
import br.com.reserva.reserva.services.conta_r.ReservaRService;

@Component
public class EfetuarReservaConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ReservaCUDService reservaCUDService;

    @Autowired
    ReservaRService reservaRService;

    private static final String EXCHANGE_NAME = "saga-exchange";

    @RabbitListener(queues = "ms-reserva-reserva-cadastrar")
    public void cadastrarReservaCUD(ReservaManterDto reservaManterDto) {
        // try {
        //     ReservaManterDto reservaManterCriadaDto = reservaCUDService.cadastrarReservaCUD(reservaManterDto);
        //     rabbitTemplate.convertAndSend(EXCHANGE_NAME, "saga-ms-reserva-reserva-cadastrada", reservaManterCriadaDto);
        // } catch (Exception e) {
        //     rabbitTemplate.convertAndSend(EXCHANGE_NAME, "saga-ms-reserva-reserva-cadastrada-erro", reservaManterDto);
        // }
    }

    @RabbitListener(queues = "ms-reserva-reserva-cadastrada-compensar")
    public void compensarReservaCUDCadastrada(ReservaManterDto reservaManterDto) {
        // try {
        //     reservaCUDService.reverterReservaCUDcadastrada(reservaManterDto);
        // } catch (Exception e) {

        // }
    }

    @RabbitListener(queues = "ms-reserva-reserva-cadastrada-contaR")
    public void cadastrarReservaR(ReservaCUD reservaCUD) {
        // try {
        //     reservaRService.cadastrarReservaR(reservaCUD);
        // } catch (Exception e) {

        // }
    }
}
