package br.com.debico.social.services.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.notify.model.Contato;
import br.com.debico.social.services.AdministradorService;

@Named
@Transactional(readOnly = true)
class AdministradorServiceImpl implements AdministradorService {

    public AdministradorServiceImpl() {
    }

    @Override
    public List<Contato> selecionarAdminComoContato() {
	return null;
    }

}
