package com.kebuu.domain;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name="enhanced_cotation", uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class EnhancedCotation extends Cotation {

    public Integer getYear(){
        return new DateTime(getDate()).getYear();
    }

    public Integer getMonth(){
        return new DateTime(getDate()).getMonthOfYear();
    }

    public Integer getDayOfMonth(){
        return new DateTime(getDate()).getDayOfMonth();
    }

    public Integer getDayOfWeek(){
        return new DateTime(getDate()).getDayOfWeek();
    }
}
