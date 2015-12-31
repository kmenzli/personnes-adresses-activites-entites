package org.academy.hibernate;

import org.academy.hibernate.entities.Activite;
import org.academy.hibernate.entities.Adresse;
import org.academy.hibernate.entities.Personne;
import org.academy.hibernate.entities.PersonneActivite;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class InitDB {

	// constantes
	private final static String TABLE_PERSONNE_ACTIVITE = "jpa07_hb_personne_activite";

	private final static String TABLE_PERSONNE = "jpa07_hb_personne";

	private final static String TABLE_ACTIVITE = "jpa07_hb_activite";

	private final static String TABLE_ADRESSE = "jpa07_hb_adresse";

	public static void main(String[] args) throws ParseException {
		// Contexte de persistance
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = null;
		// on r�cup�re un EntityManager � partir de l'EntityManagerFactory
		// pr�c�dent
		em = emf.createEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// requ�te
		Query sql1;
		// supprimer les �l�ments de la table PERSONNE_ACTIVITE
		sql1 = em.createNativeQuery("delete from " + TABLE_PERSONNE_ACTIVITE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table PERSONNE
		sql1 = em.createNativeQuery("delete from " + TABLE_PERSONNE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table ACTIVITE
		sql1 = em.createNativeQuery("delete from " + TABLE_ACTIVITE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table ADRESSE
		sql1 = em.createNativeQuery("delete from " + TABLE_ADRESSE);
		sql1.executeUpdate();
		// cr�ation activites
		Activite act1 = new Activite();
		act1.setNom("act1");
		Activite act2 = new Activite();
		act2.setNom("act2");
		Activite act3 = new Activite();
		act3.setNom("act3");
		// persistance activites
		em.persist(act1);
		em.persist(act2);
		em.persist(act3);
		// cr�ation personnes
		Personne p1 = new Personne("p1", "Paul", new SimpleDateFormat("dd/MM/yy").parse("31/01/2000"), true, 2);
		Personne p2 = new Personne("p2", "Sylvie", new SimpleDateFormat("dd/MM/yy").parse("05/07/2001"), false, 0);
		Personne p3 = new Personne("p3", "Sylvie", new SimpleDateFormat("dd/MM/yy").parse("05/07/2001"), false, 0);
		// cr�ation adresses
		Adresse adr1 = new Adresse("adr1", null, null, "49000", "Angers", null, "France");
		Adresse adr2 = new Adresse("adr2", "Les Mimosas", "15 av Foch", "49002", "Angers", "03", "France");
		Adresse adr3 = new Adresse("adr3", "x", "x", "x", "x", "x", "x");
		Adresse adr4 = new Adresse("adr4", "y", "y", "y", "y", "y", "y");
		// associations personne <--> adresse
		p1.setAdresse(adr1);
		adr1.setPersonne(p1);
		p2.setAdresse(adr2);
		adr2.setPersonne(p2);
		p3.setAdresse(adr3);
		adr3.setPersonne(p3);
		// persistance des personnes et donc des adresses associ�es
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		// persistance de l'adresse a4 non li�e � une personne
		em.persist(adr4);
		// affichage personnes
		System.out.println("[personnes]");
		for (Object p : em.createQuery("select p from Personne p order by p.nom asc").getResultList()) {
			System.out.println(p);
		}
		// affichage adresses
		System.out.println("[adresses]");
		for (Object a : em.createQuery("select a from Adresse a").getResultList()) {
			System.out.println(a);
		}
		System.out.println("[activites]");
		for (Object a : em.createQuery("select a from Activite a").getResultList()) {
			System.out.println(a);
		}
		// associations personne <-->activite
		PersonneActivite p1act1 = new PersonneActivite(p1, act1);
		PersonneActivite p1act2 = new PersonneActivite(p1, act2);
		PersonneActivite p2act1 = new PersonneActivite(p2, act1);
		PersonneActivite p2act3 = new PersonneActivite(p2, act3);
		// persistance des associations personne <--> activite
		em.persist(p1act1);
		em.persist(p1act2);
		em.persist(p2act1);
		em.persist(p2act3);
		// affichage personnes
		System.out.println("[personnes]");
		for (Object p : em.createQuery("select p from Personne p order by p.nom asc").getResultList()) {
			System.out.println(p);
		}
		// affichage adresses
		System.out.println("[adresses]");
		for (Object a : em.createQuery("select a from Adresse a").getResultList()) {
			System.out.println(a);
		}
		System.out.println("[activites]");
		for (Object a : em.createQuery("select a from Activite a").getResultList()) {
			System.out.println(a);
		}
		System.out.println("[personnes/activites]");
		for (Object pa : em.createQuery("select pa from PersonneActivite pa").getResultList()) {
			System.out.println(pa);
		}
		// fin transaction
		tx.commit();
		// fin EntityManager
		em.close();
		// fin EntityManagerFactory
		emf.close();
		// log
		System.out.println("termin�...");

	}
}
