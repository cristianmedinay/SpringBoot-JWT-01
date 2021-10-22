package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        return  entityManager.createQuery(query).getResultList();
        //return resultado;
    }

    @Override
    public void eliminar(Long id) {

        Usuario usuario = entityManager.find(Usuario.class,id);
         entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuario(Usuario usuario) {

        //VERIFY EMAIL NO PASSWORD
        // el password lo manejamos desde jwt
        String query = "FROM Usuario WHERE email = :email";
        //String query = "FROM Usuario WHERE email = :email AND password = :password";
        List<Usuario>  lista = entityManager.createQuery(query)
                .setParameter("email",usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }
        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        /*boolean lapassword =  argon2.verify(passwordHashed,usuario.getPassword());
        return  lapassword;*/
        if(argon2.verify(passwordHashed,usuario.getPassword())){
            return lista.get(0);
        }


        /*if (lista.isEmpty()){
            return false;
        }else{
            return true;
        }*/
        return null;
    }
}
