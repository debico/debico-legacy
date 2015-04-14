### Sobre ###

Debico! Aplicação Java para gerenciar um bolão de um campeonato de futebol oficial ou um campeonato personalizado entre os amigos.

### Quickstart ###

Basta realizar o clone desse repositório e executar `mvn install` no diretório principal `debico`. O pacote para *deploy* está no módulo `debico-ui`. No diretório `target`, um arquivo `app.war` deverá estar disponível após a execução do comando Maven.

### Profiles Maven ###

Além do *profile* padrão, o pacote contém um profile para a realização dos testes de integração, no qual eu recomendo _muito_ que seja feito. Para tal execute `mvn -Pit clean install`. O *script* deverá criar um banco de dados temporário utilizando o H2 e executar os testes utilizando o Selenium.

### Servidor de Aplicação ###

Essa aplicação foi criada para ser executada no formato *standalone* e não depende de um servidor de aplicação JEE. Por isso, recomendo o Tomcat, Jetty ou qualquer servidor simples com suporte à Servlet 3.0.

Caso opte por utilizar o Tomcat, segue abaixo um arquivo de referência para a configuração das propriedades no ambiente (`context-reference.xml`).

```
#!xml

<?xml version="1.0" encoding="UTF-8"?>
<Context>

    <!-- Default set of monitored resources -->
    <WatchedResource>WEB-INF/web.xml</WatchedResource>

    <Environment name="spring.config.dao.db.url" value="jdbc:mysql://127.0.0.1:3306/bolao_campeao" type="java.lang.String" override="false"/>
    <Environment name="spring.config.dao.db.user" value="user_bolao" type="java.lang.String" override="false"/>
    <Environment name="spring.config.dao.db.password" value="user_bolao" type="java.lang.String" override="false"/>
    <Environment name="spring.config.dao.db.driver" value="com.mysql.jdbc.Driver" type="java.lang.String" override="false"/>
    <Environment name="spring.profiles.active" value="release,standalone-jpa" type="java.lang.String" override="false"/>
    <Environment name="hibernate.show_sql" value="false" type="java.lang.String" override="false"/>
    <Environment name="hibernate.dialect" value="org.hibernate.dialect.MySQL5Dialect" type="java.lang.String" override="false"/>
    <Environment name="hibernate.hbm2ddl.auto" value="validate" type="java.lang.String" override="false"/>

    <!-- http://stackoverflow.com/questions/8593303/could-not-connect-to-smtp-host-email-smtp-us-east-1-amazonaws-com-port-465-r -->
    <!-- http://docs.aws.amazon.com/ses/latest/DeveloperGuide/smtp-response-codes.html -->
    <Resource 
		name="mail/Session/Debico" 
		auth="Container" 
		type="javax.mail.Session" 
		mail.smtp.host="email-smtp.us-east-1.amazonaws.com"
		mail.smtp.user="AKIAJQXZLG2HJXQQZKFQ"
		password="AveanDpiAnKls3xNgw4KHGUKEHS0+dAYxRf9oITfIwIy"
		mail.transport.protocol="smtp"
		mail.smtp.port="465"
		mail.smtp.auth="true"
		mail.smtp.ssl.enable="true"
		/>
</Context>
```
Veja nas seções mas adiante, maiores detalhes sobre as propriedades que podem ser configuradas no ambiente.

### Banco de Dados ###

O banco de dados escolhido para suportar a aplicação é o MySQL. Você pode alterar para utilizar qualquer outro apenas alterando algumas propriedades.

Fora isso, não há muito o que fazer aqui. A própria aplicação gerencia o banco de dados pra você. Crie o banco de dados e um usuário para a aplicação no seu sistema favorito e informe nas propriedades de sistema. as views e as tabelas de suporte de terceiros (Disqus, Spring, etc.) devem ser criados manualmente por meio [deste script](https://bitbucket.org/ricardozanini/debico/src/dac4cf90c9fca497822c480758bead31052538c8/debico-core/src/main/resources/sql/db-objects.sql?at=master).

### Propriedades de Ambiente ###

Algumas propriedades da aplicação devem ser configuradas no ambiente. A não ser que você queira manter no MySQL com um usuário e senha padrão. Não, você não quer isso! Observe a tabela:

| Propriedade | Tipo | Padrão | Descrição
----------------- | ----- | --------- | ------------
| `spring.config.dao.db.url` | String | jdbc\:mysql\://127.0.0.1:3306/bolao_campeao | URL de conexão com o banco de dados
| `spring.config.dao.db.user` | String | user_bolao | Nome do usuário do banco de dados.
| `spring.config.dao.db.password` | String | user_bolao | Senha do usuário do banco de dados.
| `spring.config.dao.db.driver` | String | `com.mysql.jdbc.Driver` | Nome do driver JDBC do banco de dados. Caso altere o tipo do banco, não esquecer de adicionar o *driver* no *classpath* do servidor de aplicação.
| `spring.config.dao.db.max.conn` | Integer | 5 | Tamanho do *pool* inicial de conexão do banco de dados.
| `spring.config.dao.db.validation_query` | String | SELECT 1 | *Query* para realizar a validação da conexão com o banco de dados.
| `br.com.debico.jndi.mail.session` | String | java\:comp/env/mail/Session/Debico | Nome do JNDI da sessão de e-mail do servidor de aplicação.
| `br.com.debico.admin.email` | String | ricardozanini@gmail.com | E-mail do administrador do sistema. Aquele cara que vai receber as reclamações do formulário de contato. :)

Qualquer propriedade do hibernate pode ser carregada no ambiente. Apenas adicione-as no seu classpath e tenha certeza de que a aplicação esteja lendo antes de carregar o ambiente.

### Configuração do envio de E-mail ###

Utilizo a Amazon Web Services para realizar o envio de e-mails. Abaixo, as configurações padrão para a configuração do SMTP.

| Nome | Valor |
| ------- | ------- |
| Host   | email-smtp.us-east-1.amazonaws.com
| User   | AKIAJQXZLG2HJXQQZKFQ
| Password | AveanDpiAnKls3xNgw4KHGUKEHS0+dAYxRf9oITfIwIy
| Protocol | smtp
| Port | 465
| Auth | `true`
| SSL | `true`

### Logging ###

O serviço de log utilizado pela aplicação é o SLF4J com implementação do Logback. É esperado que as bibliotecas do SLF4J estejam no *classpath* do servidor de aplicação. Para saber como configurar o Tomcat para utilizar o SLF4J como padrão, leia [este artigo](http://adfinmunich.blogspot.com.br/2012/03/how-to-configure-tomcat-to-use-slf4j.html) e [este aqui também](http://stackoverflow.com/questions/8012595/tomcat-logging-with-slf4j-and-log4j). 

### Copyright ###

2015
Ricardo Zanini Fernandes
ricardozanini@gmail.com