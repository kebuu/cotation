package com.kebuu.dao;

import com.kebuu.domain.Cotation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CotationRepository extends PagingAndSortingRepository<Cotation, Long> {

}
