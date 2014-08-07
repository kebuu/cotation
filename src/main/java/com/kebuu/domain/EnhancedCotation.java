package com.kebuu.domain;

import com.kebuu.enums.Direction;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;

@Data
@Entity
@Table(name = "enhanced_cotation", uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class EnhancedCotation extends Cotation {

    private Double dailyEndDelta;
    private Double dailyEndDeltaPercent;
    private Double intraDayDelta;
    private Double intraDayMaxMinusMin;
    private Integer sameDirectionConsecutiveCount;

    @Column(name = "slipping_mean_20")
    private Double slippingMean20;
    @Column(name = "slipping_mean_20_delta")
    private Double slippingMean20Delta;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Integer getYear() {
        return new DateTime(getDate()).getYear();
    }

    public Integer getMonth() {
        return new DateTime(getDate()).getMonthOfYear();
    }

    public Integer getDayOfMonth() {
        return new DateTime(getDate()).getDayOfMonth();
    }

    public Integer getDayOfWeek() {
        return new DateTime(getDate()).getDayOfWeek();
    }
}
