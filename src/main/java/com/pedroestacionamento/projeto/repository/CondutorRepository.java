package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {


    @Query("SELECT condutor FROM Condutor condutor WHERE condutor.ativo = true")
    public  List<Condutor> listarPorAtivo();

    @Query("SELECT movimentacao FROM Movimentacao movimentacao WHERE condutor.id = :id")
    public List<Movimentacao> buscarMovimentacaoPorCondutor(@Param("id") final Long condutorId);

    @Query("SELECT condutor FROM Condutor condutor WHERE condutor.cpf = :cpf")
    public boolean verificarCPF(@Param("cpf") String cpf);

    @Query("SELECT condutor FROM Condutor condutor WHERE condutor.telefone = :telefone")
    public boolean verificarTelefone(@Param("telefone")String telefone);

    @Transactional
    @Modifying
    @Query("UPDATE Condutor condutor SET condutor.ativo = false WHERE condutor.id = :id")
    public void desativar(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Condutor condutor SET condutor.ativo = true WHERE condutor.id = :id")
    public void ativar(@Param("id")Long id);
}
