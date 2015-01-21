package br.com.debico.core.dao.spring;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Determina qual {@link DAOConfig} será implementada pela factory do Spring.
 * 
 * @see <a href="https://gist.github.com/keesun/1633244">Spring 3.1's @Import & ImportSelector</a>
 * @author r_fernandes
 * 
 */
public class DAOConfigSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        final TipoDAOConfig type = (TipoDAOConfig) importingClassMetadata.getAnnotationAttributes(EnableDAO.class.getName()).get("type");
        
        switch (type) {
		case JPA_EMBEDDED_DATABASE:
			return new String[] { DAOJPAEmbeddedConfig.class.getName() };
		case JPA_STANDALONE:
			return new String[] { DAOJPAStandaloneConfig.class.getName() };
		case JPA_MOCK:
			// não importar diretamente para não forçar o Mockito no classpath.
			return new String[] { "br.com.debico.core.dao.spring.DAOJPAMockConfig" };
		default:
			throw new IllegalArgumentException();
		}
    }

}
