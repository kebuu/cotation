package com.kebuu.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class Cotation {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;
    private double start;
    private double min;
    private double max;
    private double end;
    private Long volume;
}
