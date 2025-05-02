package com.credit.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.EventCredit;
import com.credit.application.dto.response.GetCredit;
import com.credit.application.mapper.CreditMapper;
import com.credit.application.service.CreditService;
import com.credit.domain.persistence.Credit;
import com.credit.domain.repository.CreditRepository;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<GetCredit> findAll(Pageable pageable) {
        return creditRepository.findAll(pageable)
                .map(CreditMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetCredit findById(Long id) {
        return CreditMapper.toGetDto(findByIdEntity(id));
    }

    @Override
    public EventCredit registerCredit(SaveCredit saveCredit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerCredit'");
    }

    @Override
    public void delete(Long id) {
        Credit deleteCredit = findByIdEntity(id);
        deleteCredit.setDeletedAt(LocalDateTime.now());
        deleteCredit.setActive(false);
    }

    /**
     * Método que busca un crédito por su ID.
     *
     * @param id ID del crédito a buscar.
     * @return El crédito encontrado.
     * @throws RuntimeException si no se encuentra el crédito (cambiar esta exepcion
     *                          al futuro).
     */
    private Credit findByIdEntity(Long id) {
        return creditRepository.findActiveById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el crédito con id: " + id));

    }

}
