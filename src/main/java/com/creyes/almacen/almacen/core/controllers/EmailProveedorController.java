package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.EmailProveedor;
import com.creyes.almacen.almacen.core.models.services.IEmailProveedorService;
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
@Api(tags="emailProveedores")
public class EmailProveedorController {

    private final IEmailProveedorService emailProveedorService;


    public EmailProveedorController(IEmailProveedorService emailProveedorService){
        this.emailProveedorService=emailProveedorService;
    }
    @ApiOperation(value = "Listar emailproveedor", notes = "Servicio para listar los emailProveedores")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de emailProveedores")})
    @GetMapping("/emailProveedores")
    public List<EmailProveedor> index(){
        return this.emailProveedorService.findAll();

    }
    //paginacion de e empoint
    @ApiOperation(value = "Paginar listado de emailProveedores", notes = "Servicio para listar los emailProveedores paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "EMAIL paginadas")})
    @GetMapping("/emailProveedores/page/{page}")
    public Page<EmailProveedor> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return emailProveedorService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar emailProveedores por ID", notes = "Servicio para buscar emailProveedores por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "email encontrada"),
            @ApiResponse(code = 404 , message = "email no encontrada")})
    @GetMapping("/emailProveedores/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        EmailProveedor emailEncontrado =this.emailProveedorService.findById(id);
        if(emailEncontrado==null){
            response.put("mensaje","email no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<EmailProveedor>(emailEncontrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear email", notes = "Servicio para crea un email")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear emal"),
            @ApiResponse(code = 201, message = "email creado exitosamente!!!!")})
    @PostMapping("/emailProveedores")
    public ResponseEntity<?> create(@Valid @RequestBody EmailProveedor elemento, BindingResult result){

        EmailProveedor nuevo=null;
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
                nuevo=this.emailProveedorService.save(elemento);


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
    public ResponseEntity<?> update(@Valid @RequestBody EmailProveedor emailCliente,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        EmailProveedor update=this.emailProveedorService.findById(id);
        EmailProveedor emailUpdate=null;
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
            update.setProveedor(emailCliente.getProveedor());
            emailUpdate= this.emailProveedorService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","email");
        response.put("emailProveedores",emailUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("emailProveedores/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.emailProveedorService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar la categoria de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","email fue eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
