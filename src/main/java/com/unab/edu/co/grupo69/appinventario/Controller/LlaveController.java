package com.unab.edu.co.grupo69.appinventario.Controller;
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
import org.springframework.web.bind.annotation.RestController;

import com.unab.edu.co.grupo69.appinventario.Entity.Message;
import com.unab.edu.co.grupo69.appinventario.Entity.Llave;
import com.unab.edu.co.grupo69.appinventario.Security.Encriptar;
import com.unab.edu.co.grupo69.appinventario.Service.LlaveService;

@RestController
@RequestMapping("/api/v1/llave")
public class LlaveController {
    @Autowired
    LlaveService llaveservice;
    @PostMapping("/create")
    public ResponseEntity<Message> save(@RequestBody Llave llave){

        try {

            if(llave.getToken()==null){
                return new ResponseEntity<Message>(new Message(400,"Todos los campos son obligatorios"), HttpStatus.BAD_REQUEST);
            } else{
                llaveservice.save(llave);
                return new ResponseEntity<Message>(new Message(200,"Registro creado"), HttpStatus.CREATED);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Message>(new Message(400,"El registro no se creó"),HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteByidEntity(@PathVariable String idLlave){
        
        Message message=llaveservice.deleteById(idLlave);
        return new ResponseEntity<Message>(message,HttpStatus.resolve(message.getStatus()));

    }

    @GetMapping("/listar")

    public List<Llave> findAll(){

            return (List<Llave>) llaveservice.findAll();
        
    }
    @GetMapping("/encriptar/{dato}")
    public String encriptar(@PathVariable String dato){
        return Encriptar.sha1(dato);

    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody Llave llave){
        

        try {

            if(llave.getToken()==null){
                return new ResponseEntity<Message>(new Message(400,"El campo nombre es obligatorio"), HttpStatus.BAD_REQUEST);
            } else{
                Message message=llaveservice.update(llave);
                return new ResponseEntity<Message>(new Message(message.getStatus(),message.getMessage()), HttpStatus.valueOf(message.getStatus()));
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Message>(new Message(400,"El registro no se creó"),HttpStatus.BAD_REQUEST);
        }
    }
    
}
