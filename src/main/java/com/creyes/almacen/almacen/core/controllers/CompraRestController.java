package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.Compra;
import com.creyes.almacen.almacen.core.models.services.ICompraService;
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
@Api(tags = "compras")
public class CompraRestController {
    private final ICompraService compraService;


    public CompraRestController(ICompraService compraService){
        this.compraService=compraService;
    }
    @ApiOperation(value = "Listar compras", notes = "Servicio para listar las compras")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de compras")})
    @GetMapping("/compras")
    public List<Compra> index(){
        return this.compraService.findAll();

    }
    //paginacion de productos empoint
    @ApiOperation(value = "Paginar listado de compras", notes = "Servicio para listar las compras paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "compras paginadas")})
    @GetMapping("/compras/page/{page}")
    public Page<Compra> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return compraService.finAll(pageable);
    }
    @ApiOperation(value = "Buscar compras por ID", notes = "Servicio para buscar compras por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "compra encontrada"),
            @ApiResponse(code = 404 , message = "compra no encontrada")})
    @GetMapping("/compras/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        Compra compraEncontrado =this.compraService.findById(id);
        if(compraEncontrado==null){
            response.put("mensaje","compra no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Compra>(compraEncontrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear compra", notes = "Servicio para crea una compra")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear producto"),
            @ApiResponse(code = 201, message = "compra creada exitosamente!!!!")})
    @PostMapping("/compras")
    public ResponseEntity<?> create(@Valid @RequestBody Compra elemento, BindingResult result){

        Compra nuevo=null;
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
                nuevo=this.compraService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","El producto ha sido creado com exito !!!");
        response.put("compras",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody Compra compra,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        Compra update=this.compraService.findById(id);
        Compra compraUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar la compra ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setNumeroDocumento(compra.getNumeroDocumento());
            update.setCompraProveedor(compra.getCompraProveedor());
            update.setFecha(compra.getFecha());
            update.setTotal(compra.getTotal());
            compraUpdate= this.compraService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","el producto");
        response.put("compra",compraUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("compras/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.compraService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar el producto de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","el producto fue eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }

}
