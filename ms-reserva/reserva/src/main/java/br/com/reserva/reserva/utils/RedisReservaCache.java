package br.com.reserva.reserva.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.com.reserva.reserva.models.Reserva;

@Service
public class RedisReservaCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String RESERVA_CACHE_KEY = "reserva_backup_";

    private static final long DEFAULT_CACHE_TTL = 5;

    public void saveCache(Reserva reserva) {
        redisTemplate.opsForValue().set(RESERVA_CACHE_KEY + reserva.getCodigoReserva(), reserva, DEFAULT_CACHE_TTL, TimeUnit.MINUTES);
    }

    public Reserva getCache(String codigoReserva) {
        Object reserva = redisTemplate.opsForValue().get(RESERVA_CACHE_KEY + codigoReserva);
        if (reserva instanceof Reserva) {
            return (Reserva) reserva;
        }
        return null;
    }

    public void removeCache(String codigoReserva) {
        redisTemplate.delete(RESERVA_CACHE_KEY + codigoReserva);
    }
}
