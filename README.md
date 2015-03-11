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

    <Environment name="br.com.debico.db.url" value="jdbc:mysql://127.0.0.1:3306/bolao_campeao" type="java.lang.String" override="false"/>
    <Environment name="br.com.debico.db.user" value="user_bolao" type="java.lang.String" override="false"/>
    <Environment name="br.com.debico.db.password" value="user_bolao" type="java.lang.String" override="false"/>
    <Environment name="br.com.debico.db.driver" value="com.mysql.jdbc.Driver" type="java.lang.String" override="false"/>
    <Environment name="br.com.debico.hibernate.show_sql" value="false" type="java.lang.String" override="false"/>
    <Environment name="spring.profiles.active" value="release" type="java.lang.String" override="false"/>

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



As propriedades carregas por padrão você encontra [neste arquivo](https://bitbucket.org/ricardozanini/debico/src/dac4cf90c9fca497822c480758bead31052538c8/debico-core/src/main/resources/br/com/debico/core/debico-standalone.properties?at=master). Porém, sugiro sempre configurá-las no ambiente do servidor escolhido.

### Configuração do envio de E-mail ###

Utilizo a Amazon Web Services para realizar o envio de e-mails. Abaixo, as configurações padrão para a configuração do SMTP.

# Copyright #

Ricardo Zanini Fernandes
ricardozanini@gmail.com