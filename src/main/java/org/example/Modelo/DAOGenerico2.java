package org.example.Modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DAOGenerico2<T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    Class<T> clase;

    public DAOGenerico2(Class<T> clase){
        this.clase=clase;
    }

    //INSERT
    public boolean add(T objeto){
        tx.begin();
        em.persist(objeto);
        tx.commit();
        return false;
    }

    //SELECT WHERE ID
    public T getById(String id){
        return em.find(clase, id);
    }

    //SELECT *
    public List<T> getAll(){
        return em.createQuery("SELECT c from "+ clase.getName()+" c").getResultList();
    }

    //UPDATE
    public T update(T objeto){
        tx.begin();
        objeto = em.merge(objeto);
        tx.commit();
        return objeto;
    }
    //DELETE WHERE objeto.id
    public boolean deleteUsuario(T objeto){
        tx.begin();
        em.remove(objeto);
        tx.commit();
        return true;
    }

    @Override
    public String toString() {
        return "DAOGenerico{" +
                "clase=" + clase.getSimpleName() +
                "clase=" + clase.getName() +
                '}';
    }
}
