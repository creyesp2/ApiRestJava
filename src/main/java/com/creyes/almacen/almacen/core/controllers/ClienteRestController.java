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
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable String id){
        Map<String,Object> response=new HashMap<>();
        Cliente update=this.clienteService.findByNit(id);
        Cliente clienteUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar el cliente id ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setDpi(cliente.getDpi());
            update.setNombre(cliente.getNombre());
            update.setDireccion(cliente.getDireccion());
            update.setEmailClientes(cliente.getEmailClientes());
            update.setTelefonoClientes(cliente.getTelefonoClientes());
            update.setFacturaClientes(cliente.getFacturaClientes());

            clienteUpdate= this.clienteService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","el cliente");
        response.put("categoria",clienteUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.clienteService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar la categoria de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","la categoria fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
