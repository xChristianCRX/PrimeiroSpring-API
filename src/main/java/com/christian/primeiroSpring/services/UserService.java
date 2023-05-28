package com.christian.primeiroSpring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.primeiroSpring.models.User;
import com.christian.primeiroSpring.repositories.TaskRepository;
import com.christian.primeiroSpring.repositories.UserRepository;
import com.christian.primeiroSpring.services.exceptions.DataBindingViolationException;
import com.christian.primeiroSpring.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired //Cria instâncias automaticamente para a classe abaixo
    private UserRepository userRepository;

    @Autowired //Cria instâncias automaticamente para a classe abaixo
    private TaskRepository taskRepository;

    public User findById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional //Envio de dados para o BDD
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional //Envio de dados para o BDD
    public User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Integer id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
