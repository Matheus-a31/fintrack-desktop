package fintrack.repository;

import java.util.List;

public interface RepositorioGenerico<T> {
    void adicionar(T entidade);
    void atualizar(T entidade);
    void remover(int id);
    List<T> listarTodos();
    // Exemplo de uso de curinga ? extendendo T
    void adicionarTodos(List<? extends T> entidades);
}
