package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.Inventario;
import com.creyes.almacen.almacen.core.models.services.InventarioService;
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
@Api(tags="inventarios")
public class InventarioController {
    private final InventarioService inventarioService;


    public InventarioController(InventarioService inventarioService){
        this.inventarioService=inventarioService;
    }
    @ApiOperation(value = "Listar inventarios", notes = "Servicio para listar los inventarios")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de inventarios")})
    @GetMapping("/inventarios")
    public List<Inventario> index(){
        return this.inventarioService.findAll();

    }
    //paginacion de inventarios empoint
    @ApiOperation(value = "Paginar listado de inventarios", notes = "Servicio para listar los inventarios paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "inventarios paginados")})
    @GetMapping("/inventarios/page/{page}")
    public Page<Inventario> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return inventarioService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar inventario por ID", notes = "Servicio para buscar inventario por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "inventario encontrado"),
            @ApiResponse(code = 404 , message = "inventario no encontrado")})
    @GetMapping("/inventarios/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        Inventario inventarioEncontrado =this.inventarioService.findById(id);
        if(inventarioEncontrado==null){
            response.put("mensaje","inventario no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Inventario>(inventarioEncontrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear categoria", notes = "Servicio para crea un inventario")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear inventario"),
            @ApiResponse(code = 201, message = "inventario creado exitosamente!!!!")})
    @PostMapping("/inventarios")
    public ResponseEntity<?> create(@Valid @RequestBody Inventario elemento, BindingResult result){

        Inventario nuevo=null;
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
                nuevo=this.inventarioService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","inventario ha sido creada com exito !!!");
        response.put("inventarios",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody Inventario inventario,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        Inventario update=this.inventarioService.findById(id);
        Inventario inventarioUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar inventario ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setFecha(inventario.getFecha());
            update.setPrecio(inventario.getPrecio());
            update.setEntrada(inventario.getEntrada());
            update.setSalida(inventario.getSalida());
            update.setProducto(inventario.getProducto());
            inventarioUpdate= this.inventarioService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","el inventario");
        response.put("inventario",inventarioUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("inventarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.inventarioService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar inventario de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","inventario fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }

}
