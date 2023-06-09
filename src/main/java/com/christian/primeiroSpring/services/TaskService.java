package com.christian.primeiroSpring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.primeiroSpring.models.Task;
import com.christian.primeiroSpring.models.User;
import com.christian.primeiroSpring.repositories.TaskRepository;
import com.christian.primeiroSpring.services.exceptions.DataBindingViolationException;
import com.christian.primeiroSpring.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    
    @Autowired //Cria instâncias automaticamente para a classe abaixo
    private TaskRepository taskRepository;

    @Autowired //Cria instâncias automaticamente para a classe abaixo
    private UserService userService;

    public Task findById(Integer id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()));
    }

    @Transactional //Envio de dados para o BDD
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional //Envio de dados para o BDD
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    public List<Task> findAllByUserId(Integer userId){
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }
}
