package jogo;

/**
 * Enumeração que representa os pontos fracos do herói.
 * Cada ponto fraco possui um nome em português e uma frase exibida quando é atingido.
 */
public enum WeakPoint {
    HEAD("Cabeça", "A minha cabeça...!"),
    CHEST("Peito", "O peito... não consigo respirar..."),
    STOMACH("Barriga", "A barriga... isso dói imenso..."),
    ARM("Braço", "O braço... perdi força..."),
    LEG("Perna", "A perna... quase caí...");

    private final String ptName;
    private final String hitPhrase;

    /**
     * Cria um ponto fraco com nome e frase associada.
     *
     * @param ptName nome do ponto fraco em português
     * @param hitPhrase frase exibida quando o ponto fraco é atingido
     */
    WeakPoint(String ptName, String hitPhrase) {
        this.ptName = ptName;
        this.hitPhrase = hitPhrase;
    }

    /**
     * Devolve o nome do ponto fraco em português.
     *
     * @return nome do ponto fraco
     */
    public String getPtName() {
        return ptName;
    }

    /**
     * Devolve a frase associada ao ponto fraco quando é atingido.
     *
     * @return frase de impacto
     */
    public String getHitPhrase() {
        return hitPhrase;
    }
}