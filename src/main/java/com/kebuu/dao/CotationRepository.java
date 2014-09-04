package com.kebuu.dao;

import com.kebuu.domain.Cotation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.Set;

public interface CotationRepository extends PagingAndSortingRepository<Cotation, Long> {

    Set<Cotation> findByCode(String code);

    Set<Cotation> findByCodeAndDateGreaterThan(String code, Date date);
}
