package roomescape.service.dto.response;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationSlot;
import roomescape.domain.reservation.ReservationStatus;
import roomescape.domain.reservation.WaitingWithRank;

import java.util.OptionalLong;

import static roomescape.domain.reservation.ReservationStatus.RESERVED;
import static roomescape.domain.reservation.ReservationStatus.WAITING;

public record UserReservationResponse(
        long id,
        ReservationSlot reservationSlot,
        ReservationStatus status,
        OptionalLong rank
) {
    public static UserReservationResponse reserved(Reservation reservation) {
        return new UserReservationResponse(
                reservation.getId(),
                reservation.getReservationSlot(),
                RESERVED,
                OptionalLong.empty()
        );
    }

    public static UserReservationResponse from(WaitingWithRank waitingWithRank) {
        return new UserReservationResponse(
                waitingWithRank.waiting().getId(),
                waitingWithRank.waiting().getReservation().getReservationSlot(),
                WAITING,
                OptionalLong.of(waitingWithRank.rank() + 1)
        );
    }
}
