package br.com.debico.test;

public final class TestConstants {

    /**
     * Email do usuário principal.
     */
    public static final String EMAIL_CARGA = "ricardozanini@gmail.com";
    
    /**
     * :D
     */
    public static final int MEU_ID_USUARIO = 2;

    /**
     * Lista com 3 usuários válidos. Usuários, não apostadores. Não inclui o
     * usuário principal de {@link #EMAIL_CARGA}
     */
    public static final Integer[] IDS_USUARIOS = new Integer[] { 5, 6, 7 };

    /**
     * Lista com 3 apostadores válidos. APOSTADORES. Não inclui o usuário
     * principal de {@link #EMAIL_CARGA}
     */
    public static final Integer[] IDS_APOSTADORES = new Integer[] { 7, 8, 9 };

    /**
     * Identificador do campeonato ativo da carga
     */
    public static final int CAMPEONATO_ID = 1;

    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_DB = "embedded-jpa";

    private TestConstants() {

    }

}
