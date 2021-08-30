package br.com.springboot.curso_fr_springboot.controllers;

import java.util.List;

import javax.validation.constraints.Null;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_fr_springboot.model.Usuario;
import br.com.springboot.curso_fr_springboot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController   /*intercepta todas as requisições */   
public class GreetingsController {
	
	@Autowired /*IC/cd ou cdi  = injeção de dependencia */
	private UsuarioRepository usuarioRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)/* primeiro mapeamento  */
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso springboot API: " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}",method = RequestMethod.GET)/*a variavel nome vem do navegador*/
    @ResponseStatus(HttpStatus.OK )
    public String metodoRetorno(@PathVariable String nome) {
		
    	Usuario usuario  = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);/*gravar no banco de dados*/
    	
    	return "ola mundo"+nome;
	}
    
     
    @GetMapping(value = "listatodos")/*nosso premeiro  metodo em API que é o listar todos*/
    @ResponseBody /*retorna osdados para ocorpo da resposta */
     public ResponseEntity<List<Usuario>>listaUsuario(){
     List<Usuario> usuarios = usuarioRepository.findAll();/*exucuta a consulta no banco de dados*/
     
     return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/*retorna a lista em json*/
     }
    /*
     * metodo para salvar no banco de dados 
     */
    
    @PostMapping(value = "salvar") /*mapeia a Url */
    @ResponseBody   /*Descrição da resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){/*Recebe  os Dados para Salvar*/
    	
     Usuario user =	usuarioRepository.save(usuario);
     	
      return new ResponseEntity<Usuario>(user,HttpStatus.CREATED);	
    }
    
    /*
     * metodo para atualizar no banco de dados
     */
    @PutMapping(value = "atualizar") /*mapeia a Url */
    @ResponseBody   /*Descrição da resposta*/
    public ResponseEntity<?>atualizar(@RequestBody Usuario usuario){/*Recebe  os Dados para Atualizar*/
      
    /*  	
     if(usuario.getId()== null) { 
    	 
    	 return new ResponseEntity<String>("Id não foi informado",HttpStatus.OK);	
    	
     }*/
    	
      Usuario user = usuarioRepository.saveAndFlush(usuario);
     	
      return new ResponseEntity<Usuario>(user,HttpStatus.OK);	
    }
    
    /*
     * metodo para deletar no banco de dados
     */
    @DeleteMapping(value = "delete") /*mapeia a Url */
    @ResponseBody   /*Descrição da resposta*/
    public ResponseEntity<String> delete(@RequestParam Long iduser){/*Recebe  os Dados para Deletar*/
    	
     usuarioRepository.deleteById(iduser);  /*passa o Id Para deletar*/
     	
      return new ResponseEntity<String>("Usuario deletedo com sucesso",HttpStatus.OK);	
    }
    
    
    
    /*
     metodo para fazer consultas no banco de dados
     */
    @GetMapping(value = "buscaruserid") /*mapeia a Url */
    @ResponseBody   /*Descrição da resposta*/
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){/*Recebe  os Dados para Deletar*/
    	
     Usuario usuario = usuarioRepository.findById(iduser).get();  /*passa o Id Para Consultar*/
     	
      return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);	
    }
    
    /*
     * Metodo para buscar por parte do nome
     */
    
    @GetMapping(value = "buscarPorNome") /*mapeia a Url */
    @ResponseBody   /*Descrição da resposta*/
    public ResponseEntity<List<Usuario>> buscarPorNome( @RequestParam(name = "name") String name){/*Recebe  os Dados para Deletar*/
    	
     List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());  /*passa o Id Para Consultar*/
     	
      return new ResponseEntity<List<Usuario>> (usuario,HttpStatus.OK);	
    }
}
