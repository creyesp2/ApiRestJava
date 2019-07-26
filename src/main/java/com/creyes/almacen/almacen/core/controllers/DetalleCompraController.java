package com.creyes.almacen.almacen.core.controllers;


import com.creyes.almacen.almacen.core.models.entity.DetalleCompra;
import com.creyes.almacen.almacen.core.models.services.IDetalleCompraService;
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
@Api(tags = "detalleCompras")
public class DetalleCompraController {
    private final IDetalleCompraService iDetalleCompraService;


    public DetalleCompraController(IDetalleCompraService iDetalleCompraService){
        this.iDetalleCompraService=iDetalleCompraService;
    }
    @ApiOperation(value = "Listar detallecompras", notes = "Servicio para listar las detallecompras")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de detallecompras")})
    @GetMapping("/detalleCompras")
    public List<DetalleCompra> index(){
        return this.iDetalleCompraService.findAll();

    }
    //paginacion de productos empoint
    @ApiOperation(value = "Paginar listado de detallecompras", notes = "Servicio para listar detallecompras paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "detallecompras paginadas")})
    @GetMapping("/detalleCompras/page/{page}")
    public Page<DetalleCompra> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return iDetalleCompraService.finAll(pageable);
    }
    @ApiOperation(value = "Buscar detallecompras por ID", notes = "Servicio para buscar detallecompras por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "compra encontrada"),
            @ApiResponse(code = 404 , message = "detallecompra no encontrada")})
    @GetMapping("/detalleCompras/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        DetalleCompra detallecompraEncontrada =this.iDetalleCompraService.findById(id);
        if(detallecompraEncontrada==null){
            response.put("mensaje","detallecompra no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<DetalleCompra>(detallecompraEncontrada,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear detallecompra", notes = "Servicio para crea una detallecompra")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear producto"),
            @ApiResponse(code = 201, message = "detallecompra creada exitosamente!!!!")})
    @PostMapping("/detalleCompras")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleCompra elemento, BindingResult result){

        DetalleCompra nuevo=null;
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
                nuevo=this.iDetalleCompraService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","detallecompra ha sido creada com exito !!!");
        response.put("detalleCompras",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody DetalleCompra detalleCompra,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        DetalleCompra update=this.iDetalleCompraService.findById(id);
        DetalleCompra detalleCompraUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar  detallecompra ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setDetalleCompra(detalleCompra.getDetalleCompra());
            update.setDetalleProducto(detalleCompra.getDetalleProducto());
            update.setCantidad(detalleCompra.getCantidad());
            update.setPrecio(detalleCompra.getPrecio());
            detalleCompraUpdate= this.iDetalleCompraService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","el producto");
        response.put("compra",detalleCompraUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("detalleCompras/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.iDetalleCompraService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar detalleCompra de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","detallecompra fue eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }


}
