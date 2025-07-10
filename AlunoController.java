package br.ufra.edu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunos;

    @GetMapping
    public List<Aluno> listar() {
        return alunos.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> retornaUmAluno(@PathVariable Long id) {
        return alunos.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aluno adicionar(@RequestBody Aluno aluno) {
        return alunos.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        return alunos.findById(id)
            .map(aluno -> {
                aluno.setNome(alunoAtualizado.getNome());
                aluno.setDisciplina(alunoAtualizado.getDisciplina());
                aluno.setNota(alunoAtualizado.getNota());
                aluno.setIdade(alunoAtualizado.getIdade());
                Aluno atualizado = alunos.save(aluno);
                return ResponseEntity.ok(atualizado);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        Aluno aluno = alunos.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        alunos.delete(aluno);
    }
}
