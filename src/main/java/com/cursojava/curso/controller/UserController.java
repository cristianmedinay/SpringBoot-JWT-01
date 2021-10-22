package com.cursojava.curso.controller;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController  {

    //compartir en memoria injecion de dependencia
    @Autowired
    private UsuarioDao usuarioDao;
    //@RequestMapping(value = "api/usuario/{id}",method = RequestMethod.GET)

    @Autowired
    private JWTUtil jwtUtil;


    /*@RequestMapping(value = "api/usuarios/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        //List<Usuario> usu = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("cristian");
        usuario.setApellido("franco");
        usuario.setEmail("cristianmedinay@gmail.com");
        usuario.setTelefono("12342");
        return usuario;
    }*/


    @RequestMapping(value = "api/usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){

        if(!validarToken(token)){
            return null;
        }
        return  usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }




    @RequestMapping(value = "api/usuarios",method = RequestMethod.POST)
    public void registrar(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }




    //public void eliminar(Long id) {
    @RequestMapping(value = "api/usuarios/{id}",method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,@PathVariable Long id){
        if(!validarToken(token)){
            return;
        }
       usuarioDao.eliminar(id);
    }











/*
    @RequestMapping(value = "usuario/1234")
    public List<Usuario> prueb(){
        List<Usuario> usu = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setNombre("cristian");
        usuario.setApellido("franco");
        usuario.setEmail("cristianmedinay@gmail.com");
        usuario.setPassword("12345");
        usu.add(usuario);
        return usu;
    }*/
}
