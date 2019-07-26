package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.EmailCliente;
import com.creyes.almacen.almacen.core.models.services.IEmailClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags="emailClientes")
public class EmailClienteController {

    private final IEmailClienteService emailClienteService;


    public EmailClienteController(IEmailClienteService emailClienteService){
        this.emailClienteService=emailClienteService;
    }
    @ApiOperation(value = "Listar emailClientes", notes = "Servicio para listar los emailClientes")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de EmailClientes")})
    @GetMapping("/emailClientes")
    public List<EmailCliente> index(){
        return this.emailClienteService.findAll();

    }
    //paginacion de e empoint
    @ApiOperation(value = "Paginar listado de emailClientes", notes = "Servicio para listar los email paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "EMAIL paginadas")})
    @GetMapping("/emailClientes/page/{page}")
    public Page<EmailCliente> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return emailClienteService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar email por ID", notes = "Servicio para buscar email por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "email encontrada"),
            @ApiResponse(code = 404 , message = "email no encontrada")})
    @GetMapping("/emailClientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        EmailCliente emailEncontrado =this.emailClienteService.findById(id);
        if(emailEncontrado==null){
            response.put("mensaje","email no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<EmailCliente>(emailEncontrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear email", notes = "Servicio para crea un email")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear emal"),
            @ApiResponse(code = 201, message = "email creado exitosamente!!!!")})
    @PostMapping("/emailClientes")
    public ResponseEntity<?> create(@Valid @RequestBody EmailCliente elemento, BindingResult result){

        EmailCliente nuevo=null;
        Map<String,Object>response =new HashMap<>();
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(nuevo==null)
            try{
                nuevo=this.emailClienteService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","email ha sido creada com exito !!!");
        response.put("emailClientes",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody EmailCliente emailCliente,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        EmailCliente update=this.emailClienteService.findById(id);
        EmailCliente emailUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar email ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setEmail(emailCliente.getEmail());
            update.setEmailCliente(emailCliente.getEmailCliente());
            emailUpdate= this.emailClienteService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","email");
        response.put("emailcliente",emailUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("emailClientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.emailClienteService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar la categoria de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","email fue eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
