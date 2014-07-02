package com.kebuu.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class Cotation {

    private Date date;
    private double start;
    private double min;
    private double max;
    private double end;
    private Long volume;
}
