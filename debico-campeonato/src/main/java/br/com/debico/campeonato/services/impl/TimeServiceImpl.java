package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.TimeDAO;
import br.com.debico.campeonato.services.TimeService;
import br.com.debico.model.Time;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

@Named
@Transactional(readOnly=true)
class TimeServiceImpl implements TimeService {
    
    @Inject
    private TimeDAO timeDAO;

    public TimeServiceImpl() {
        
    }

    @Override
    public List<Time> procurarTimePorNome(String nomeParcial) {
        checkNotNull(emptyToNull(nomeParcial), "O nome do time nao pode ser em branco.");
        checkArgument(nomeParcial.length() >= 3, "Informe ao menos 3 caracteres");
        
        return timeDAO.pesquisarParteNome(nomeParcial);
    }

}
