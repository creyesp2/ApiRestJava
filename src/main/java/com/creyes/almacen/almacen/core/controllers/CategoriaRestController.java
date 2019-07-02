package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.Categoria;
import com.creyes.almacen.almacen.core.models.services.CategoriaServiceImpl;
import com.creyes.almacen.almacen.core.models.services.ICategoriaService;
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
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Api(tags="categorias")
public class CategoriaRestController {
   private final ICategoriaService categoriaService;


   public CategoriaRestController(ICategoriaService categoriaService){
       this.categoriaService=categoriaService;
   }
    @ApiOperation(value = "Listar categorias", notes = "Servicio para listar las categorias")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de categorias")})
   @GetMapping("/categorias")
   public List<Categoria> index(){
       return this.categoriaService.findAll();

   }
   //paginacion de categorias empoint
   @ApiOperation(value = "Paginar listado de categorias", notes = "Servicio para listar las categorias paginadas")
   @ApiResponses(value= {@ApiResponse(code=201, message = "Categorias paginadas")})
   @GetMapping("/categorias/page/{page}")
   public Page<Categoria>index (@PathVariable Integer page){

       Pageable pageable = PageRequest.of(page,5);
       return categoriaService.findAll(pageable);
   }
    @ApiOperation(value = "Buscar categoria por ID", notes = "Servicio para buscar categoria por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404 , message = "Categoria no encontrada")})
   @GetMapping("/categorias/{id}")
   public ResponseEntity<?> show(@PathVariable Long id){
       Map<String,Object> response =new HashMap<>();
       Categoria categoriaEncontrada =this.categoriaService.findById(id);
       if(categoriaEncontrada==null){
           response.put("mensaje","categoria no encontrada");
           return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);

       }
       return new ResponseEntity<Categoria>(categoriaEncontrada,HttpStatus.OK);
   }
    @ApiOperation(value = "Crear categoria", notes = "Servicio para crea una categoria")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear categoria"),
            @ApiResponse(code = 201, message = "Categoria creada exitosamente!!!!")})
   @PostMapping("/categorias")
   public ResponseEntity<?> create(@Valid @RequestBody Categoria elemento, BindingResult result){

       Categoria nuevo=null;
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
               nuevo=this.categoriaService.save(elemento);


           }catch (DataAccessException e){
               response.put("mensaje","error al realizar el insert en la base de datos");
               response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
               return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

           }

      response.put("mensaje","La categoria ha sido creada com exito !!!");
      response.put("categorias",nuevo);
      return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria,BindingResult result, @PathVariable Long id){
       Map<String,Object> response=new HashMap<>();
       Categoria update=this.categoriaService.findById(id);
       Categoria categoriaUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar la categoria ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
       try{
           update.setDescripcion(categoria.getDescripcion());
           categoriaUpdate= this.categoriaService.save(update);

       }catch ( DataAccessException e){
           response.put("mensaje","error al actualizar los  datos");
           response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
           return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
       }
       response.put("mensaje","la categoria");
       response.put("categoria",categoriaUpdate);
       return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    
    @DeleteMapping("categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
       Map<String,Object> response =new HashMap<>();

       try{
        this.categoriaService.delete(id);

       }catch (DataAccessException e){

           response.put("mensaje","error al eliminar la categoria de la base datos");
              response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
              return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
       }
       response.put("mensaje","la categoria fue eliminada con exito");
       return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
