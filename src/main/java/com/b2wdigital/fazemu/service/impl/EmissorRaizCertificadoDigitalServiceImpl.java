package com.b2wdigital.fazemu.service.impl;

import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2wdigital.fazemu.business.repository.EmissorRaizCertificadoDigitalRepository;
import com.b2wdigital.fazemu.business.service.EmissorRaizCertificadoDigitalService;
import com.b2wdigital.fazemu.domain.EmissorRaizCertificadoDigital;
import com.b2wdigital.fazemu.domain.form.EmissorRaizCertificadoDigitalForm;
import com.b2wdigital.fazemu.utils.CertificadoUtils;
import com.b2wdigital.fazemu.utils.DateUtils;

/**
 * EmissorRaizCertificadoDigital Service.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
@Service
public class EmissorRaizCertificadoDigitalServiceImpl implements EmissorRaizCertificadoDigitalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissorRaizCertificadoDigitalServiceImpl.class);

    @Autowired
    private EmissorRaizCertificadoDigitalRepository emissorRaizCertificadoDigitalRepository;

    @Override
    public void insert(EmissorRaizCertificadoDigitalForm form) {
        emissorRaizCertificadoDigitalRepository.insert(formToObject(form));
    }

    @Override
    public void update(EmissorRaizCertificadoDigitalForm form) {
        try {
            emissorRaizCertificadoDigitalRepository.update(Long.valueOf(form.getId()), DateUtils.iso8601ToCalendar(form.getDataVigenciaFim()).getTime(), form.getUsuario());
        } catch (ParseException e) {
            LOGGER.error("Erro ao converter a data de vigencia {}", form.getDataVigenciaFim());
        }
    }

    @Override
    public List<EmissorRaizCertificadoDigitalForm> listByFiltros(Map<String, String> filtros) {
        List<EmissorRaizCertificadoDigitalForm> listaEmissorRaizForm = emissorRaizCertificadoDigitalRepository.listByFiltros(filtros);
        return listaEmissorRaizForm;
    }

    private EmissorRaizCertificadoDigital formToObject(EmissorRaizCertificadoDigitalForm form) {
        try {
            EmissorRaizCertificadoDigital emissorRaizCertificadoDigital = new EmissorRaizCertificadoDigital();
            emissorRaizCertificadoDigital.setIdEmissorRaiz(Long.valueOf(form.getIdEmissorRaiz()));
            emissorRaizCertificadoDigital.setCertificadoBytes(form.getFileInput());
            emissorRaizCertificadoDigital.setSenha(form.getSenha());
            emissorRaizCertificadoDigital.setDataVigenciaInicio(DateUtils.convertStringToDate(form.getDataVigenciaInicio()));
            emissorRaizCertificadoDigital.setDataVigenciaFim(DateUtils.convertStringToDate(CertificadoUtils.getExpirationDate(emissorRaizCertificadoDigital)));

            emissorRaizCertificadoDigital.setSenha(Base64.getEncoder().encodeToString(form.getSenha().getBytes()));
            emissorRaizCertificadoDigital.setUsuario(form.getUsuario());
            emissorRaizCertificadoDigital.setDataHora(form.getDataHora());

            return emissorRaizCertificadoDigital;

        } catch (ParseException e) {
            LOGGER.error("Erro ao converter as datas do certificado digital", e);
            return null;
        }
    }

    @Override
    public List<EmissorRaizCertificadoDigitalForm> listByDataFimVigencia() {
        return emissorRaizCertificadoDigitalRepository.listByDataFimVigencia();
    }

}
