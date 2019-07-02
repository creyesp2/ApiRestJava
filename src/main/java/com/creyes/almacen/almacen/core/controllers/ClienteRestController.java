package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.Cliente;
import com.creyes.almacen.almacen.core.models.services.IClienteService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ClienteRestController {
    private final IClienteService clienteService;
    public ClienteRestController(IClienteService clienteService){
        this.clienteService=clienteService;
    }

    @GetMapping("/clientes")

    public List<Cliente> index(){
        return this.clienteService.findAll();

    }
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index (@PathVariable Integer page){


        Pageable pageable = PageRequest.of(page,5);
        return clienteService.finAll(pageable);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable String id){
        Map<String,Object> response =new HashMap<>();
        Cliente clienteEncontrado =this.clienteService.findByNit(id);
        if(clienteEncontrado==null){
            response.put("mensaje","cliente no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Cliente>(clienteEncontrado,HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente elemento, BindingResult result){

        Cliente nuevo=null;
        Map<String,Object>response =new HashMap<>();
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        try{
            nuevo=this.clienteService.save(elemento);


        }catch (DataAccessException e){
            response.put("mensaje","error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje","El cliente ha sido creado com exito !!!");
        response.put("clientes",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
}
